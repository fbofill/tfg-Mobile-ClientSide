package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_next,btn_check;
    RadioGroup radioGroup;
    RadioButton one,two,three,four;
    TextView txt_pregunta;
    User user;
    String curso;
    IMyService iMyService;
    private int pos;
    String respCorrecta;
    int numPreguntas;
    List<Pregunta> pregunta;
    int puntos;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        Intent intent=getIntent();

        user=(User)intent.getSerializableExtra("user");
        curso=intent.getStringExtra("curso");

        txt_pregunta=findViewById(R.id.txt_pregunta);

        txt_pregunta.setText(curso);

        //ViewComponents
        one=findViewById(R.id.rad_uno);
        one.setOnClickListener(this);
        two=findViewById(R.id.rad_dos);
        two.setOnClickListener(this);
        three=findViewById(R.id.rad_tres);
        three.setOnClickListener(this);
        four=findViewById(R.id.rad_cuatro);
        four.setOnClickListener(this);
        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);

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
                    pregunta=response.body();
                    pos=0;

                    Pregunta currentPreg;
                    puntos=0;
                    numPreguntas=pregunta.size();
                    Collections.shuffle(pregunta);

                    currentPreg=pregunta.get(pos);
                    respCorrecta=currentPreg.getOpcion1();
                    txt_pregunta.setText(currentPreg.getEnunciado());
                    desordenar_preguntas(currentPreg,one,two,three,four);

                }
            }

            @Override
            public void onFailure(Call<List<Pregunta>> call, Throwable t) {
                Toast.makeText(QuizActivity.this, "Error: "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    void desordenar_preguntas(Pregunta preg, RadioButton one,RadioButton two,RadioButton three,RadioButton four){
        List<Integer> orden= new ArrayList<Integer>();
        for (int i=0;i<4;i++){
            orden.add(i);
        }
        Collections.shuffle(orden);
        for (int k=0;k<4;k++){
            switch (k){
                case 0:
                    asignar_texto(preg,one,orden.get(k));
                    break;
                case 1:
                    asignar_texto(preg,two,orden.get(k));
                    break;
                case 2:
                    asignar_texto(preg,three,orden.get(k));
                    break;
                case 3:
                    asignar_texto(preg,four,orden.get(k));
                    break;
            }
        }
    }
    void asignar_texto(Pregunta preg,RadioButton but, int k){
        switch (k){
            case 0:
                but.setText(preg.getOpcion1());
                break;
            case 1:
                but.setText(preg.getOpcion2());
                break;
            case 2:
                but.setText(preg.getOpcion3());
                break;
            case 3:
                but.setText(preg.getOpcion4());
                break;

        }
    }

    void desabilita_botones(){
        one.setEnabled(false);
        two.setEnabled(false);
        three.setEnabled(false);
        four.setEnabled(false);
    }

    void habilitar_botones(){
        one.setEnabled(true);
        two.setEnabled(true);
        three.setEnabled(true);
        four.setEnabled(true);
        one.setChecked(false);
        two.setChecked(false);
        three.setChecked(false);
        four.setChecked(false);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rad_uno:
                if(one.getText()==respCorrecta){
                    desabilita_botones();
                    puntos++;
                    Toast.makeText(QuizActivity.this, "ACIERTO", Toast.LENGTH_SHORT).show();
                }else{
                    desabilita_botones();
                    Toast.makeText(QuizActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rad_dos:
                if(two.getText()==respCorrecta){
                    desabilita_botones();
                    puntos++;
                    Toast.makeText(QuizActivity.this, "ACIERTO", Toast.LENGTH_SHORT).show();
            }else{
                    desabilita_botones();
                    Toast.makeText(QuizActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rad_tres:
                if(three.getText()==respCorrecta){
                    desabilita_botones();
                    puntos++;
                    Toast.makeText(QuizActivity.this, "ACIERTO", Toast.LENGTH_SHORT).show();
                }else{
                    desabilita_botones();
                    Toast.makeText(QuizActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rad_cuatro:
                if(four.getText()==respCorrecta){
                    desabilita_botones();
                    puntos++;
                    Toast.makeText(QuizActivity.this, "ACIERTO", Toast.LENGTH_SHORT).show();
                }else{
                    desabilita_botones();
                    Toast.makeText(QuizActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_next:
                Toast.makeText(QuizActivity.this, "NEXT", Toast.LENGTH_SHORT).show();
                if (pos+1<numPreguntas && pos+1<10){
                    pos+=1;
                    siguientePregunta();
                }else{
                    gameOver();
                }
                break;
        }

    }

    void siguientePregunta(){
        Pregunta currentPreg;
        currentPreg=pregunta.get(pos);
        respCorrecta=currentPreg.getOpcion1();
        txt_pregunta.setText(currentPreg.getEnunciado());


        respCorrecta=currentPreg.getOpcion1();
        txt_pregunta.setText(currentPreg.getEnunciado());
        desordenar_preguntas(currentPreg,one,two,three,four);
        habilitar_botones();

    }
    void gameOver(){
        String aux,auxPreg;
        aux=String.valueOf(puntos);
        auxPreg=String.valueOf(numPreguntas);
        Log.i("Puntos",aux);
        Log.i("NumPreg",auxPreg);

        puntos=(puntos*10)/numPreguntas;

        aux=String.valueOf(puntos);
        Log.i("Puntos",aux);
        Intent intent = new Intent(QuizActivity.this, QuizEndActivity.class);
        intent.putExtra("curso",curso);
        intent.putExtra("user", user);
        intent.putExtra("points", puntos);
        startActivity(intent);

    }

}
