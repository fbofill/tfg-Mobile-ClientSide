package com.example.tfgapp.Retrofit;

import android.content.Intent;

import com.example.tfgapp.Models.Completados;
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


    @POST("api/getCursos")
    @FormUrlEncoded
    Call<List<Curso>> getCurso(@Field("id")String id);

    @POST("api/getCursosCompletado")
    @FormUrlEncoded
    Call<List<Completados>> getCursoCompletado(@Field("name")String name);




    @POST("api/getPreguntas")
    @FormUrlEncoded
    Call<List<Pregunta>> getPregunta(@Field("name")String name);


    @POST("api/endQuiz")
    @FormUrlEncoded
    Call<User> endQuiz(@Field("userid")String userid,
                       @Field("points")int points,
                       @Field("cursoname")String cursoname
                       );

    @POST ("api/deleteUser")
    @FormUrlEncoded
    Call<String> deleteUser(@Field("id") String userid);

    @POST("api/editUser")
    @FormUrlEncoded
    Call<User> editUser(@Field("id") String id,
                       @Field("name") String name,
                       @Field("email") String email,
                       @Field("password") String password,
                       @Field("password2") String password2);




}
