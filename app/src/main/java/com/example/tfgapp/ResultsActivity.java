package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.Models.Completados;
import com.example.tfgapp.Models.User;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResultsActivity extends AppCompatActivity {
    User user;
    Button btn_dashboard;
    IMyService iMyService;
    ListView listViewResult;
    TextView txtUsername,txtPoints;
    TextView textViewResult;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");


        btn_dashboard =  findViewById(R.id.btn_dashboard);


        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard();
            }
        });
        listViewResult=(ListView)findViewById(R.id.lv_cursos);

        txtUsername=findViewById(R.id.txt_name);
        txtUsername.setText(user.getName());

        txtPoints=findViewById(R.id.txt_points);
        txtPoints.setText(String.valueOf(user.getPoints()));

        Call<List<Completados>> call= iMyService.getCursoCompletado(user.getName());

        ArrayList<String> nombreCurso= new ArrayList<String>();
        ArrayList<String> scoreCurso= new ArrayList<String>();


        call.enqueue(new Callback<List<Completados>>() {
            @Override
            public void onResponse(Call<List<Completados>> call, Response<List<Completados>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("CODE: "+ response.code());
                }else{
                    List<Completados> completado=response.body();

                    for(Completados completados:completado){
                        nombreCurso.add(completados.getCurso());
                        scoreCurso.add(String.valueOf(completados.getScore()));

                    }
                    MyListAdapter adapter=new MyListAdapter(ResultsActivity.this, nombreCurso,scoreCurso);
                    listViewResult.setAdapter(adapter);

                }
            }
            @Override
            public void onFailure(Call<List<Completados>> call, Throwable t) {
                textViewResult.setText((t.getMessage()));

            }
        });

    }

    public void dashboard(){
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

}
