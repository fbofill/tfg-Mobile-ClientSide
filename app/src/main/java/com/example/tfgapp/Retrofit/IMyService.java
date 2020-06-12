package com.example.tfgapp.Retrofit;

import com.example.tfgapp.Models.Curso;
import com.example.tfgapp.Models.Pregunta;
import com.example.tfgapp.Models.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.Call;

public interface IMyService {
    @POST ("api/register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("password2") String password2,
                                    @Field("points") Integer points);
    @POST ("api/login")
    @FormUrlEncoded
    Call<User> loginUser(@Field("email") String email,
                               @Field("password") String password);

    @GET("api/{email}")
    Observable<String> getProfile(@Path("name") String name);


    @GET("api/getCursos")
    Call<List<Curso>> getCurso();

    @POST("api/getPreguntas")
    @FormUrlEncoded
    Call<List<Pregunta>> getPregunta(@Field("name")String name);
}
