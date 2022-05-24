package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewsInfoActivity extends AppCompatActivity {
Button addBtn, deleteBtn, updateBtn, Backbtn;
TextView date;
EditText title, description;
DataBaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        Bundle arguments = getIntent().getExtras();
            updateBtn= findViewById(R.id.Updatebtn);
            addBtn= findViewById(R.id.Insertbtn);
            deleteBtn = findViewById(R.id.Deletebtn);
            date = findViewById(R.id.DateNews);
            Backbtn = findViewById(R.id.btnBack);
            title = findViewById(R.id.Title);
            databaseHelper= new DataBaseHelper(this);
            description = findViewById(R.id.Description);
            Backbtn.setOnClickListener(view->
            {
                Intent mainintent = new Intent(NewsInfoActivity.this, NewsListActivity.class);
                mainintent.putExtra("role", arguments.get("role").toString());
                NewsInfoActivity.this.startActivity(mainintent);
                NewsInfoActivity.this.finish();
            });
        if(arguments.get("add").toString().equals("0"))
        {
            updateBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
            addBtn.setVisibility(View.GONE);
            title.setText(arguments.get("title").toString());
            date.setText(arguments.get("date").toString());
            description.setText(arguments.get("description").toString());
        }
        if(arguments.get("add").toString().equals("1"))
        {
            addBtn.setVisibility(View.GONE);
            title.setText(arguments.get("title").toString());
            date.setText(arguments.get("date").toString());
            description.setText(arguments.get("description").toString());
        }
        if(arguments.get("add").toString().equals("2"))
        {
            updateBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
            date.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(Calendar.getInstance().getTime()));
        }
        addBtn.setOnClickListener(view->{
            if(title.getText().toString().equals("") || date.getText().toString().equals("") || description.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_LONG).show();
                return;
            }
            Boolean checkInsertData = databaseHelper.insertNews(title.getText().toString(), date.getText().toString(), description.getText().toString());
            if (checkInsertData) {
                Intent mainIntent = new Intent(NewsInfoActivity.this, NewsListActivity.class);
                mainIntent.putExtra("role", arguments.get("role").toString());
                NewsInfoActivity.this.startActivity(mainIntent);
                NewsInfoActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });

        deleteBtn.setOnClickListener(view->{
            Boolean checkDeleteData = databaseHelper.DeleteNews(arguments.get("id").toString());
            if (checkDeleteData) {
                Intent mainIntent = new Intent(NewsInfoActivity.this, NewsListActivity.class);
                mainIntent.putExtra("role",  arguments.get("role").toString());
                NewsInfoActivity.this.startActivity(mainIntent);
                NewsInfoActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });

        updateBtn.setOnClickListener(view->{
            if(title.getText().toString().equals("") || date.getText().toString().equals("") || description.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(), "Не все поля заполнены", Toast.LENGTH_LONG).show();
                return;
            }
            Boolean checkUpdateData = databaseHelper.UpdateNews(arguments.get("id").toString(),title.getText().toString(), date.getText().toString(), description.getText().toString());
            if (checkUpdateData) {
                Intent mainIntent = new Intent(NewsInfoActivity.this, NewsListActivity.class);
                mainIntent.putExtra("role",  arguments.get("role").toString());
                NewsInfoActivity.this.startActivity(mainIntent);
                NewsInfoActivity.this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }
}