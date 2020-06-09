package com.example.tfgapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.tfgapp.Retrofit.IMyService;
import com.example.tfgapp.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText loginEmail, loginPassword;
    Button btn_login;
    IMyService iMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //Init View

        btn_login = findViewById(R.id.btn_login);
    }

    public void createAccount(View view) {
        final View register_layout = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.register_layout, null);


        new MaterialStyledDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_user)
                .setTitle("REGISTRO")
                .setDescription("Rellene todos los campos")
                .setCustomView(register_layout)
                .setNegativeText("CANCELAR")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveText("CREAR CUENTA")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EditText edt_register_email = register_layout.findViewById(R.id.edt_email);
                        EditText edt_register_name = register_layout.findViewById(R.id.edt_name);
                        EditText edt_register_password = register_layout.findViewById(R.id.edt_password);
                        EditText edt_register_password2 = register_layout.findViewById(R.id.edt_password2);


                        if (TextUtils.isEmpty(edt_register_email.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Introduce un email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(edt_register_name.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Introduce un nombre", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(edt_register_password.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Introduce una contraseña", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(edt_register_password2.getText().toString())) {
                            Toast.makeText(MainActivity.this, "Repite la contraseña", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!TextUtils.equals(edt_register_password.getText().toString(), edt_register_password2.getText().toString())) {
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

    public void loginAccount(View view) {
        loginEmail = findViewById(R.id.edt_email);
        loginPassword = findViewById(R.id.edt_password);

        loginUser(loginEmail.getText().toString(),
                loginPassword.getText().toString());
    }

    private void registerUser(String name, String email, String password, String password2) {
        compositeDisposable.add(iMyService.registerUser(name, email, password, password2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    private void loginUser(String email, String password) {
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
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer response) throws Exception {
                        if(response<0){
                            Toast.makeText(MainActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }else if (response == 1){
                            handleResponse();
                        }
                    }
                }));
    }

    public void handleResponse(){
        Toast.makeText(MainActivity.this, "BIENVENIDO", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

}


