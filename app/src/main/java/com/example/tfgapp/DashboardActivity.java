package com.example.tfgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfgapp.Models.Curso;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;

public class DashboardActivity extends AppCompatActivity {

    Button btn_logout;
    private TextView textViewResult;
    IMyService iMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_logout =  findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        textViewResult=findViewById(R.id.txt_cursos);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

       Call<List<Curso>> call= iMyService.getCurso();


        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("CODE: "+ response.code());
                }
                List<Curso> cursos=response.body();

                for(Curso curso:cursos){
                    String content="";
                    content += "NAME: " + curso.getName()+"\n";
                    content += "DESCRIPTION: " + curso.getDescription()+"\n";
                    content += "LEVEL: " + curso.getLevel()+"\n\n";

                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                textViewResult.setText((t.getMessage()));


            }
        });


    }

    public void logout(){
        Intent intent = new Intent(this, MainActivity.class);
        btn_logout =  findViewById(R.id.btn_logout);
        startActivity(intent);
    }
}
