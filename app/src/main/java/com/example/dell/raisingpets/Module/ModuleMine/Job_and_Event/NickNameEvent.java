package com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event;

import android.widget.TextView;

/**
 * Created by root on 16-12-4.
 */

public class NickNameEvent {

    public boolean isSucessed;
    public String nickName;
    public TextView userNickName;


    public NickNameEvent(boolean isSucessed,String nickName,TextView userNickName){
        this.isSucessed = isSucessed;
        this.nickName = nickName;
        this.userNickName = userNickName;
    }

    public boolean isSucess(){
        return isSucessed;
    }

    public String nickName(){
        return nickName;
    }

    public TextView getTextView(){
        return userNickName;
    }
}
