package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.Models.User;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OptionActivity extends AppCompatActivity {
    User user;
    IMyService iMyService;
    Button btn_dashboard,btn_edit,btn_delete;
    TextView txtUsername;
    EditText edt_name,edt_email,edt_password,edt_password2;
    TextView textViewResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");

        btn_dashboard =  findViewById(R.id.btn_dashboard);
        btn_edit =  findViewById(R.id.btn_edit);
        btn_delete =  findViewById(R.id.btn_delete);
        txtUsername=findViewById(R.id.txt_name);
        edt_name=findViewById(R.id.edt_name);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        edt_password2=findViewById(R.id.edt_password2);



        txtUsername.setText(user.getName());
        edt_name.setText(user.getName());
        edt_email.setText(user.getEmail());

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard(user);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edt_name.getText().toString();
                String email=edt_email.getText().toString();
                String password=edt_password.getText().toString();
                String password2=edt_password2.getText().toString();

                Call<User> call= iMyService.editUser(user.getId(),name,email,password,password2);

                if(!password.equals(password2)){
                    textViewResult.setText(("Las contraseñas no coinciden"));
                }else{
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if(!response.isSuccessful()){
                                textViewResult.setText("CODE: "+ response.code());
                            }else{
                                User newuser=response.body();
                                dashboard(newuser);
                            }
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            textViewResult.setText((t.getMessage()));

                        }
                    });

                }


            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disclaimer();
            }
        });



    }

    public void dashboard(User user){
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void disclaimer(){
        Intent intent = new Intent(this, DisclaimerActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }


}

