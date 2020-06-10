package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.Models.Curso;
import com.example.tfgapp.Models.Pregunta;
import com.example.tfgapp.Models.User;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuizActivity extends AppCompatActivity {

    Button btn_logout,btn_check;
    RadioGroup radioGroup;
    RadioButton one,two,three,four;
    TextView txt_pregunta;
    User user;
    String curso;
    IMyService iMyService;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        Intent intent=getIntent();

        user=(User)intent.getSerializableExtra("user");
        curso=intent.getStringExtra("curso");

        txt_pregunta=findViewById(R.id.txt_pregunta);

        txt_pregunta.setText(curso);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        Call<List<Pregunta>> call= iMyService.getPregunta(curso);

        call.enqueue(new Callback<List<Pregunta>>() {
            @Override
            public void onResponse(Call<List<Pregunta>> call, Response<List<Pregunta>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(QuizActivity.this, "Error: "+ response.code(), Toast.LENGTH_LONG).show();
                }else {
                    List<Pregunta> pregunta=response.body();
                    //TODO:Continuar con el desarrollo del procesamiento de la lista de preguntas

                }
            }

            @Override
            public void onFailure(Call<List<Pregunta>> call, Throwable t) {
                Toast.makeText(QuizActivity.this, "Error: "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });





    }

}
