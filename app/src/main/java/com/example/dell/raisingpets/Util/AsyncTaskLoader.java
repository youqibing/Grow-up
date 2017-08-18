package com.example.dell.raisingpets.Util;

import android.os.AsyncTask;

/**
 * Created by root on 16-11-28.
 * 该类的作用就是用于将一些琐碎的耗时动作添加到后台,比如头像剪裁图片之后读入文件夹的过程,以及游戏画面渲染时人物加载的耗时操作.
 */

public class AsyncTaskLoader extends AsyncTask <IAsyncCallback, Integer, Boolean> {

    IAsyncCallback[] _params;

    @Override
    protected Boolean doInBackground(IAsyncCallback... params) {
        this._params = params;
        int count = params.length;
        for(int i = 0; i < count; i++){
            params[i].workToDo();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        int count = this._params.length;
        for(int i = 0; i < count; i++){
            this._params[i].onComplete();
        }
    }
}
