package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.Models.Curso;
import com.example.tfgapp.Models.Pregunta;
import com.example.tfgapp.Models.User;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuizEndActivity  extends AppCompatActivity {

    User user;
    String curso;
    int points;
    IMyService iMyService;
    TextView txt_points, txt_result;
    Button btn_end;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completed);

        Intent intent = getIntent();

        user = (User) intent.getSerializableExtra("user");
        curso = intent.getStringExtra("curso");
        points = intent.getIntExtra("points", 0);

        String puntos=String.valueOf(points);

        txt_points = findViewById(R.id.txt_points);
        txt_result = findViewById(R.id.txt_result);

        txt_points.setText(puntos);

        btn_end = findViewById(R.id.btn_end);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);


        if (points < 5) {
            txt_result.setText("Suspenso");
        } else {
            txt_result.setText("Aprobado");
        }



        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<User> call = iMyService.endQuiz(user.getId(), points, curso);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(QuizEndActivity.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                        } else {
                            User usuario = response.body();
                            Intent intent = new Intent(QuizEndActivity.this, DashboardActivity.class);
                            intent.putExtra("user", usuario);
                            startActivity(intent);

                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(QuizEndActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
