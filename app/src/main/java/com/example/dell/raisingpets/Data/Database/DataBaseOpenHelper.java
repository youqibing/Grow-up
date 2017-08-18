package com.example.dell.raisingpets.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 16-11-22.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private final static String STEPS = "STEPS";
    private final static int VESION = 1;

    private static DataBaseOpenHelper instance;

    public DataBaseOpenHelper(Context context) {
        super(context, "data", null, VESION);
    }

    public static synchronized DataBaseOpenHelper getInstance(final Context context){
        if(instance == null){
            instance = new DataBaseOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + STEPS + " (date INTEGER, steps INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Query the 'steps' table. Remember to close the cursor!
     *
     * @param columns       the colums
     * @param selection     the selection
     * @param selectionArgs the selction arguments
     * @param groupBy       the group by statement
     * @param having        the having statement
     * @param orderBy       the order by statement
     * @return the cursor
     */
    public Cursor query(final String[] columns, final String selection,
                        final String[] selectionArgs, final String groupBy, final String having,
                        final String orderBy, final String limit){
        return getReadableDatabase().query(STEPS, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public void insertNewDaysteps(long date, int steps){
        getWritableDatabase().beginTransaction();
        try{
            Cursor cursor = getReadableDatabase()
                    .query(STEPS, new String[]{"date"},"date = ?",new String[]{String.valueOf(date)},null,null,null);
            if(cursor.getCount() == 0 && steps >= 0){

                // add 'steps' to yesterdays count
                addToLastEntryDaySteps(steps);

                ContentValues values = new ContentValues();
                values.put("date", date);   // add today
                values.put("steps", -steps);    // use the negative steps as offset
                getWritableDatabase().insert(STEPS,null,values);
            }
            cursor.close();
            getWritableDatabase().setTransactionSuccessful();
        } finally {
            getWritableDatabase().endTransaction();
        }
    }

    /**
     * Adds the given number of steps to the last entry in the database
     * @param steps the number of steps to add. Must be > 0
     */
    public void addToLastEntryDaySteps(int steps){
        if(steps > 0){
            getWritableDatabase().execSQL("UPDATE " + STEPS +" SET steps = steps + " + steps +
                    " WHERE date = (SELECT MAX(date) FROM " + STEPS +")");
        }
    }

    /**
     * Saves the current 'steps since boot' sensor value in the database.
     *
     * @param steps since boot
     */
    public void saveCurrentSteps(int steps){
        ContentValues values = new ContentValues();
        values.put("steps",steps);
        if(getWritableDatabase().update(STEPS,values,"date = -1",null) == 0){
            values.put("date", -1);
            getWritableDatabase().insert(STEPS,null,values);
        }
    }

    /**
     * Reads the latest saved value for the 'steps since boot' sensor value.
     *
     * @return the current number of steps saved in the database or 0 if there
     * is no entry
     */
    public int getCurrentSteps(){
        int steps = getSteps(-1);
        return steps == Integer.MIN_VALUE ? 0 : steps;
    }

    /**
     * Get the number of steps taken for a specific date.
     * <p/>
     * If date is Util.getToday(), this method returns the offset which needs to
     * be added to the value returned by getCurrentSteps() to get todays steps.
     *
     * @param date the date in millis since 1970
     * @return the steps taken on this date or Integer.MIN_VALUE if date doesn't
     * exist in the database
     */
    public int getSteps(final long date){
        Cursor cursor = getReadableDatabase()
                .query(STEPS,new String[]{"steps"},"date = ?",new String[]{String.valueOf(date)},null,null,null);
        cursor.moveToFirst();
        int steps;
        if(cursor.getCount() == 0) {
            steps = Integer.MIN_VALUE;
        }else{
            steps = cursor.getInt(0);
        }
        cursor.close();
        return steps;
    }



}
