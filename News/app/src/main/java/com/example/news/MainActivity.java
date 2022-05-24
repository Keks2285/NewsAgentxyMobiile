package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import androidx.biometric.BiometricPrompt;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
Button regBtn, signIn, signInByFinger;
EditText LoginTb, PasswordTb;
DataBaseHelper dataBaseHelper;
Executor ex;
BiometricPrompt promt;
BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regBtn = findViewById(R.id.Registrationbtn);
        signInByFinger= findViewById(R.id.logIFinger);
        ex = ContextCompat.getMainExecutor(this);
        promt = new BiometricPrompt(MainActivity.this,
                ex,new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString){
                super.onAuthenticationError(errorCode,errString);
                //Log.e("ErrAuth",errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result){
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Успешно!", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(MainActivity.this, NewsListActivity.class);
                mainIntent.putExtra("role", "2");
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }

            @Override
            public void onAuthenticationFailed(){
                super.onAuthenticationFailed();
                Log.e("Отпечаток нераспознан","Ошибка");
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация в новостное приложение")
                .setSubtitle("Отсканируйте палец")
                .setNegativeButtonText("Отмена")
                .build();

        signInByFinger.setOnClickListener(view->{
            promt.authenticate(promptInfo);
        });
        regBtn.setOnClickListener(view->
        {
            Intent mainIntent = new Intent(this, RegistrationActivity.class);
            startActivity(mainIntent);
        });
        signIn=findViewById(R.id.LogIn);
        LoginTb=findViewById(R.id.LoginEt);
        PasswordTb= findViewById(R.id.PasswordEt);
        dataBaseHelper = new DataBaseHelper(this);
        signIn.setOnClickListener(view->
        {
            int Role=0;
            Cursor user = dataBaseHelper.checkRoleUser(LoginTb.getText().toString(), PasswordTb.getText().toString());
            if(user==null)
            {
                Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_LONG).show();
                return;
            }
            while (user.moveToNext()) {
                Role = user.getInt(0);
            }
            if (Role == 1) {
                Intent mainIntent = new Intent(MainActivity.this, NewsListActivity.class);
                mainIntent.putExtra("role", "1");
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
            else if(Role == 2) {
                Intent mainIntent = new Intent(MainActivity.this, NewsListActivity.class);
                mainIntent.putExtra("role", "2");
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }

        });



    }
}