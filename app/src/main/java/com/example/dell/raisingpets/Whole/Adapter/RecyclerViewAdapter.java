package com.example.dell.raisingpets.Whole.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/10/20.
 */

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter<T>.RecyclerHolder> {

    protected Context context;
    protected LayoutInflater layoutInflater;

    protected long clickItemTime;
    protected static final long minClickTime = 500;

    protected List<T> data = new ArrayList<>();
    protected ItemClickListener itemClickListener;

    public RecyclerViewAdapter(Context context){
        Log.e("testAdapter","3");
        this.context = context;
        Log.e("testAdapter","3.1");
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.e("testAdapter","3.2");
    }

    protected abstract class RecyclerHolder extends RecyclerView.ViewHolder{

        public RecyclerHolder(View itemView) {
            super(itemView);
            Log.e("testAdapter","3.3");
        }

        public abstract void fillView(int position);
    }


    public interface ItemClickListener{
        void onItemClick(int position);
    }

    public void setItemOnClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    protected void onClickitem(int position){

    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, final int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                itemClick(position);

            }
        });
        holder.fillView(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void itemClick(int postion){
        if(System.currentTimeMillis() - clickItemTime <= minClickTime){

        }
        if(itemClickListener != null){
            itemClickListener.onItemClick(postion);
        }
        onClickitem(postion);
        clickItemTime = System.currentTimeMillis();
    }

    public T getItem(int position){
        return data.get(position);
    }


}
