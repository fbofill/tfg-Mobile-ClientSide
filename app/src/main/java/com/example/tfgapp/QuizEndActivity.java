package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.Models.User;

public class QuizEndActivity  extends AppCompatActivity implements View.OnClickListener {

    User user;
    String curso;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completed);

        Intent intent = getIntent();

        user = (User) intent.getSerializableExtra("user");
        curso = intent.getStringExtra("curso");

    }


    @Override
    public void onClick(View v) {


    }
}
