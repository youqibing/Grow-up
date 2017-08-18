package com.example.dell.raisingpets.NetWork.Result;

import com.example.dell.raisingpets.Type.UserInformation;
import com.example.dell.raisingpets.Whole.NetWork.BaseResult;

/**
 * Created by dell on 2016/8/15.
 */

public class RegisterResult extends BaseResult{

    String token;
    UserInformation user;

    public UserInformation getUser() {
        return user;
    }

    public void setUser(UserInformation user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
