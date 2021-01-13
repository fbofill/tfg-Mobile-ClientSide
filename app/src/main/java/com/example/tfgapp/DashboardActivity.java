package com.example.tfgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tfgapp.Models.Curso;
import com.example.tfgapp.Models.User;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;

public class DashboardActivity extends AppCompatActivity {

    Button btn_logout;
    Button btn_results;
    ImageButton btn_settings;
    TextView textViewResult,txtUsername;
    IMyService iMyService;
    User user;
    ListView listViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Activity elements
        btn_logout =  findViewById(R.id.btn_logout);
        btn_results =  findViewById(R.id.btn_results);
        btn_settings =  findViewById(R.id.btn_opt);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btn_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             results();
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options();
            }
        });

        listViewResult=(ListView)findViewById(R.id.lv_cursos);
        txtUsername=findViewById(R.id.txt_name);

        //Intent content
        Intent intent= getIntent();
        user=(User)intent.getSerializableExtra("user");
        txtUsername.setText(user.getName());

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

       Call<List<Curso>> call= iMyService.getCurso(user.getId());

        ArrayList<String> titulos= new ArrayList<String>();
        ArrayList<String> descripciones= new ArrayList<String>();

        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("CODE: "+ response.code());
                }else{
                    List<Curso> cursos=response.body();

                    for(Curso curso:cursos){
                        titulos.add(curso.getName());
                        descripciones.add(curso.getDescription());

                    }
                    MyListAdapter adapter=new MyListAdapter(DashboardActivity.this, titulos,descripciones);
                    listViewResult.setAdapter(adapter);


                    listViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                            Intent intent = new Intent(DashboardActivity.this, QuizActivity.class);
                            intent.putExtra("curso", titulos.get(position));
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }
                    });
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

    public void results(){
        Intent intent = new Intent(DashboardActivity.this, ResultsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void options(){
        Intent intent = new Intent(DashboardActivity.this, OptionActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
