package com.example.dell.raisingpets.NetWork.Result;

import java.io.Serializable;

/**
 * Created by dell on 2016/8/15.
 */

public class ApiResult<T> implements Serializable{

    private int code;
    private String msg;
    private T data;


    public ApiResult(){
        this.code = 0;
        this.msg = "成功";
    }

    public ApiResult(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

    public T getData(){
        return data;
    }


    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
