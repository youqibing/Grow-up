package com.example.dell.raisingpets.NetWork;


import android.util.Log;

import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.NetWork.Result.BackgroudResult;
import com.example.dell.raisingpets.NetWork.Result.ChangeBackgroudResult;
import com.example.dell.raisingpets.NetWork.Result.CharactersResult;
import com.example.dell.raisingpets.NetWork.Result.FoodResult;
import com.example.dell.raisingpets.NetWork.Result.PassWordResult;
import com.example.dell.raisingpets.NetWork.Result.PostStepsAndMoneyResult;
import com.example.dell.raisingpets.NetWork.Result.PropsResult;
import com.example.dell.raisingpets.NetWork.Result.FinishRestResult;
import com.example.dell.raisingpets.NetWork.Result.PurchaseFoodResult;
import com.example.dell.raisingpets.NetWork.Result.ReliveResult;
import com.example.dell.raisingpets.NetWork.Result.SendWordsReult;
import com.example.dell.raisingpets.NetWork.Result.StartRestResult;
import com.example.dell.raisingpets.NetWork.Result.LoginResult;
import com.example.dell.raisingpets.NetWork.Result.NickNameResult;
import com.example.dell.raisingpets.NetWork.Result.PostTokenResult;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.HeaderResult;
import com.example.dell.raisingpets.NetWork.Result.RegisterResult;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;


/**
 * Created by dell on 2016/8/15.
 */

public class RetrofitApi {

    public static final String BASE_URL = "https://pets.hustonline.net";
    private static RestAdapter restAdapter;
    private static RasingPetsService service;

    public interface RasingPetsService{

        @GET("/v1/commodity/commodities/1")
        ApiResult<List<FoodResult.FoodList>> food();

        @GET("/v1/commodity/commodities/2")
        ApiResult<List<CharactersResult.CharacterList>> character();

        @GET("/v1/commodity/commodities/3")
        ApiResult<List<BackgroudResult.BackgroudList>> backgroud();

        @GET("/v1/commodity/commodities/4")
        ApiResult<List<PropsResult.PropsList>> props();



        @FormUrlEncoded
        @POST("/v1/user/purchase")
        ApiResult<PurchaseFoodResult> purchaseFood(@Field("token") String token,
                                                         @Field("identifier") String identifier);

        /**FormUrlEncoded表单的方式传递键值对*/
        @FormUrlEncoded
        @POST("/v1/user/register")
        ApiResult<RegisterResult> register(@Field("phone") String phone,
                                           @Field("password") String password);

        @FormUrlEncoded
        @POST("/v1/user/login")
        ApiResult<LoginResult> login(@Field("phone") String phone,
                                           @Field("password") String password);

        @FormUrlEncoded
        @POST("/v1/user/change_password")
        ApiResult<PassWordResult> editPassWord(@Field("token") String token,
                                                     @Field("old") String oldPassWord,
                                                     @Field("new") String newPassWord);

        @FormUrlEncoded
        @POST("/v1/user/refresh_token")
        ApiResult<PostTokenResult> postToken(@Field("token") String token);

        @FormUrlEncoded
        @POST("/v1/user/add_pace")
        ApiResult<PostStepsAndMoneyResult> postSteps(@Field("token") String token,
                                            @Field("pace_num") int pace_num,
                                            @Field("money") int money);

        @FormUrlEncoded
        @POST("/v1/user/rest")
        ApiResult<StartRestResult> startRest(@Field("token") String TOKEN,
                                                @Field("option") int option);

        @FormUrlEncoded
        @POST("/v1/user/rest")
        ApiResult<FinishRestResult> finishRest(@Field("token") String TOKEN,
                                             @Field("option") int option);

        @FormUrlEncoded
        @POST("/v1/user/use_commodity/3")
        ApiResult<ChangeBackgroudResult> ChangeBackgroud(@Field("token") String TOKEN,
                                               @Field("identifier") String identifier);


        @FormUrlEncoded
        @POST("/v1/user/info")
        ApiResult<NickNameResult> nicknamechange(@Field("token") String TOKEN,
                                                 @Field("name") String name);

        @FormUrlEncoded
        @POST("/v1/user/feedback")
        ApiResult<SendWordsReult> sendWord(@Field("token") String TOKEN,
                                                     @Field("information") String information,
                                                     @Field("message") String message);

        @FormUrlEncoded
        @POST("/v1/user/restart")
        ApiResult<ReliveResult> relive(@Field("token") String TOKEN,
                                                 @Field("option") int option);

        /**Multipart单文件上传*/
        @Multipart
        @POST("/v1/user/change_head")
        ApiResult<HeaderResult> headerchange(@Part("token") String TOKEN,
                                             @Part("image") TypedFile image);
    }


    static {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(10, TimeUnit.SECONDS);

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        Response r = cause.getResponse();
                        if (r != null && r.getStatus() == 401) {

                        }
                        return cause;
                    }
                })
                .setClient(new OkClient(okHttpClient))
                .setConverter(new Converter() {

                    @Override
                    public Object fromBody(TypedInput body, Type type) throws ConversionException {
                        BufferedReader reader = null;

                        try {
                            reader = new BufferedReader(new InputStreamReader(body.in()));
                            StringBuilder builder = new StringBuilder();
                            String line;
                            while (null != (line = reader.readLine())) {
                                builder.append(line);
                            }
                            Gson gson = new GsonBuilder().create();
                            ApiResult result = gson.fromJson(builder.toString(), type);

                            Log.e("testApiResult",result.getMsg());
                            if (result.getCode() != 0 ) {
                                ToastUtil.showLongToast(result.getMsg());
                            }
                            return result;

                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException ignored) {
                                }
                            }
                        }
                        return null;
                    }

                    @Override
                    public TypedOutput toBody(Object object) {
                        return null;
                    }
                })
                .build();

        service = restAdapter.create(RasingPetsService.class);
    }


    public static ApiResult<RegisterResult> register(String phoneNum, String passWord){
        return service.register(phoneNum,passWord);
    }

    public static ApiResult<LoginResult> login(String phoneNum, String passWord){
        return service.login(phoneNum,passWord);
    }

    public static ApiResult<PassWordResult> editPassWord(String oldPassWord, String newPassWord){
        return service.editPassWord(UserPreference.getToken(),oldPassWord,newPassWord);
    }

    public static ApiResult<PostTokenResult> postToken(String token){
        return service.postToken(token);
    }

    public static ApiResult<PostStepsAndMoneyResult> postSteps(int pace_num,int money){
        return service.postSteps(UserPreference.getToken(),pace_num,money);
    }

    public static ApiResult<StartRestResult> startRest(int option){
        return service.startRest(UserPreference.getToken(),option);
    }

    public static ApiResult<FinishRestResult> finishRest(int option){
        return service.finishRest(UserPreference.getToken(),option);
    }

    public static ApiResult<ChangeBackgroudResult> ChangeBackgroud(String identifier){
        return service.ChangeBackgroud(UserPreference.getToken(),identifier);
    }

    public static ApiResult<List<FoodResult.FoodList>> foodLists(){
        return service.food();
    }

    public static ApiResult<PurchaseFoodResult> purchaseFood(String identifier){
        return service.purchaseFood(UserPreference.getToken(),identifier);
    }

    public static ApiResult<List<CharactersResult.CharacterList>> charactersResult(){
        return service.character();
    }

    public static ApiResult<List<BackgroudResult.BackgroudList>> backgroudListResult(){
        return service.backgroud();
    }

    public static ApiResult<List<PropsResult.PropsList>> propsListResult(){
        return service.props();
    }

    public static ApiResult<NickNameResult> nicknamechange(String nickName){
        return service.nicknamechange(UserPreference.getToken(),nickName);
    }

    public static ApiResult<SendWordsReult> sendWord(String information, String message){
        return service.sendWord(UserPreference.getToken(),information,message);
    }

    public static ApiResult<ReliveResult> relive(int option){
        return service.relive(UserPreference.getToken(),option);
    }

    public static ApiResult<HeaderResult> headerchange(String uri) {

        File file = new File(uri);
        TypedFile typedFile = new TypedFile("multipart/mixed",file);
        return service.headerchange(UserPreference.getToken(),typedFile);
    }



}
