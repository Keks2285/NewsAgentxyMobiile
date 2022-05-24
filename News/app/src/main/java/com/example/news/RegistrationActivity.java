package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
Button Regbtn, BackBtn;
EditText LoginTb, PasswordTb;
DataBaseHelper databaseHelper;
CheckBox admCB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        BackBtn=findViewById(R.id.BackBtn);
        databaseHelper = new DataBaseHelper(this);
        BackBtn.setOnClickListener(view->
        {
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        });
        LoginTb = findViewById(R.id.LoginEt);
        PasswordTb = findViewById(R.id.PasswrdEt);
        Regbtn=findViewById(R.id.RegBtn);
        admCB=findViewById(R.id.RoleCB);
        Regbtn.setOnClickListener(view->
        {
            if(LoginTb.getText().length()<2)
            {
                Toast.makeText(getApplicationContext(), "Логин слишком короткий", Toast.LENGTH_LONG).show(); return;
            }
            if(PasswordTb.getText().length()<2)
            {
                Toast.makeText(getApplicationContext(), "Пароль слишком короткий", Toast.LENGTH_LONG).show(); return;
            }
            boolean checkInsert;
            int Role =2;
            if(admCB.isChecked())Role=1;
            checkInsert = databaseHelper.insertUser(LoginTb.getText().toString(), PasswordTb.getText().toString(),Role);
            if (checkInsert == true) {
                Toast.makeText(getApplicationContext(), "Успешная регистрация", Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(mainIntent);
                RegistrationActivity.this.finish();
            } else if (checkInsert == false) {
                    Toast.makeText(getApplicationContext(), "Логин занят!", Toast.LENGTH_LONG).show();
            }

        });


    }
}