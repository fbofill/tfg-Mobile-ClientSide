package com.example.tfgapp.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IMyService {
    @POST ("api/register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("password2") String password2);
    @POST ("api/login")
    @FormUrlEncoded
    Observable<String> loginUser(@Field("email") String email,
                                    @Field("password") String password);

    @GET("api/{email}")
    Observable<String> getProfile(@Path("name") String name);
}
