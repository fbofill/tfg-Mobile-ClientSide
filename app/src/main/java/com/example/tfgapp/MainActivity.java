package com.example.tfgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    MaterialEditText edt_login_email,edt_login_password;
    Button btn_login;

    CompositeDisposable compositeDisposable= new CompositeDisposable();
    IMyService iMyService;
    private SharedPreferences mSharedPreferences;



    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Service
        Retrofit retrofitClient =RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);

        //Init View
        edt_login_email =findViewById(R.id.edt_email);
        edt_login_password =findViewById(R.id.edt_password);
        btn_login =  findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(edt_login_email.getText().toString(),
                        edt_login_password.getText().toString());
            }
        });
        btn_login = findViewById(R.id.btn_create_account);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View register_layout = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.register_layout, null);

                new MaterialStyledDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.ic_user)
                        .setTitle("REGISTRATION")
                        .setDescription("Rellene todos los campos")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                MaterialEditText edt_register_email =  register_layout.findViewById(R.id.edt_email);
                                MaterialEditText edt_register_name =  register_layout.findViewById(R.id.edt_name);
                                MaterialEditText edt_register_password =  register_layout.findViewById(R.id.edt_password);
                                MaterialEditText edt_register_password2 =  register_layout.findViewById(R.id.edt_password2);


                                if(TextUtils.isEmpty(edt_register_email.getText().toString())){
                                    Toast.makeText(MainActivity.this, "Introduce un email", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(TextUtils.isEmpty(edt_register_name.getText().toString())){
                                    Toast.makeText(MainActivity.this, "Introduce un nombre", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(TextUtils.isEmpty(edt_register_password.getText().toString())){
                                    Toast.makeText(MainActivity.this, "Introduce una contraseña", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(TextUtils.isEmpty(edt_register_password2.getText().toString())){
                                    Toast.makeText(MainActivity.this, "Repite la contraseña", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(!TextUtils.equals(edt_register_password.getText().toString(),edt_register_password2.getText().toString())){
                                    Toast.makeText(MainActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                registerUser(edt_register_name.getText().toString(),
                                        edt_register_email.getText().toString(),
                                        edt_register_password.getText().toString(),
                                        edt_register_password2.getText().toString());


                            }
                        }).show();
            }
        });
    }
    private void loginUser(String email, String password){
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Introduce un email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Introduce una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        compositeDisposable.add(iMyService.loginUser(email,password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String response) throws Exception {
                Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
            //   if(response.equals(ok)){
                  //  handleResponse();
               // }
            }
        }));


    }
    private void handleResponse(){
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);

    }

    private void registerUser(String name,String email, String password,String password2){
        compositeDisposable.add(iMyService.registerUser(name,email,password,password2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }));

    }
}
