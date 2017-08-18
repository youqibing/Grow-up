package com.example.dell.raisingpets.Module.ModuleCharacters.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.NetWork.Result.CharactersResult;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/10/21.
 */

public class CharactersJob extends Job {

    private static final int UX = 4;

    public CharactersJob() {
        super(new Params(UX).requireNetwork());
    }
    /*
    public CharactersJob(CharacterView characterView) {
        super(new Params(UX).requireNetwork());
        this.characterView = characterView;
    }
    */

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        ApiResult<List<CharactersResult.CharacterList>> charactersResult = RetrofitApi.charactersResult();
        Log.e("testCharacters",charactersResult+"");
        Log.e("testCharactersName",charactersResult.getData()+"");

        EventBus.getDefault().post(new CharacterEvent(charactersResult.getData()));

        /*
        UserPreference.storeCharacters(Serialization(charactersResult));
        EventBus.getDefault().post(new CharacterEvent(true));
        */

        /*
        ApiResult<List<CharactersResult.CharacterList>> charactersResult_two = deSerialization(UserPreference.getCharacters());
        List<CharactersResult.CharacterList> characters = charactersResult_two.getData();

        for(int i =0;i<characters.size();i++){

            Log.e("Name",characters.get(i).getName()+"");
            Log.e("Category",characters.get(i).getCategory()+"");
            Log.e("Com_id",characters.get(i).getCom_id()+"");
            Log.e("Describe",characters.get(i).getDescribe()+"");
            Log.e("HP",characters.get(i).getHP()+"");
            Log.e("Hungry_point",characters.get(i).getHungry_point()+"");
            Log.e("Image",characters.get(i).getImage()+"");
            Log.e("Price",characters.get(i).getPrice()+"");
        }
        */

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }



    /**
     * 序列化对象,便于直接存到SharedPreferences中
     * @param charactersResult
     * @return
     * @throws IOException
     */
    /*
    private String Serialization(ApiResult<List<CharactersResult.CharacterList>> charactersResult) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(charactersResult);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();

        return serStr;
    }
    */

    /**
     * 反序列化对象,即取出对象
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */

    /*
    private ApiResult<List<CharactersResult.CharacterList>> deSerialization(String str) throws IOException,
            ClassNotFoundException {
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        ApiResult<List<CharactersResult.CharacterList>> charactersResult =
                (ApiResult<List<CharactersResult.CharacterList>>) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();

        return charactersResult;
    }

    */
}
