package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.DashboardActivity;
import com.example.tfgapp.MainActivity;
import com.example.tfgapp.Models.Completados;
import com.example.tfgapp.Models.User;
import com.example.tfgapp.MyListAdapter;
import com.example.tfgapp.R;
import com.example.tfgapp.ResultsActivity;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DisclaimerActivity extends AppCompatActivity {
    User user;
    IMyService iMyService;
    Button btn_yes,btn_no;
    TextView textViewResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        btn_yes =  findViewById(R.id.btn_yes);
        btn_no =  findViewById(R.id.btn_no);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> call= iMyService.deleteUser(user.getId());

                Log.i("USER_ID",user.getId());

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(!response.isSuccessful()){
                            textViewResult.setText("CODE: "+ response.code());
                        }else{
                            logout();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        textViewResult.setText((t.getMessage()));

                    }
                });
            }
        });
    }

    public void dashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void logout(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
