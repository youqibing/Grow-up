package com.example.dell.raisingpets.NetWork.Result;

import com.example.dell.raisingpets.Type.UserInformation;

/**
 * Created by root on 16-12-29.
 */

public class ReliveResult {

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
