package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewsListActivity extends AppCompatActivity {
Button AddNewsBtn, ExitBtn, Addbtn;
    DataBaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        Bundle arguments = getIntent().getExtras();
        int Role=Integer.parseInt(arguments.get("role").toString());
        AddNewsBtn = findViewById(R.id.btnAdd);
        ExitBtn=findViewById(R.id.btnExit);
        databaseHelper = new DataBaseHelper(this);
        if(arguments.get("role").toString().equals("2"))
            AddNewsBtn.setVisibility(View.GONE);
        Addbtn= findViewById(R.id.btnAdd);
        Cursor res = databaseHelper.getAllNews();

        while (res.moveToNext()){
            Button myButton = new Button(this);
            myButton.setText(res.getString(1)+"\n"+res.getString(2));
            LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
            myButton.setOnClickListener(view -> {
                String[] arrSplit = myButton.getText().toString().split("\n");
                Cursor NewsData = databaseHelper.getNew(arrSplit[0],arrSplit[1]);
                String id="";
                String title="";
                String date = "";
                String description = "";
                while(NewsData.moveToNext()){
                    id = NewsData.getString(0);
                    title = NewsData.getString(1);
                    date = NewsData.getString(2);
                    description = NewsData.getString(3);
                }
                Intent mainIntent = new Intent(NewsListActivity.this, NewsInfoActivity.class);
                mainIntent.putExtra("id", id);
                mainIntent.putExtra("title", title);
                mainIntent.putExtra("date", date);
                mainIntent.putExtra("description", description);
                if(arguments.get("role").toString().equals("2"))
                {
                    mainIntent.putExtra("add", "0");
                    mainIntent.putExtra("role", arguments.get("role").toString());
                }
                else{
                    mainIntent.putExtra("add", "1");
                    mainIntent.putExtra("role", arguments.get("role").toString());
                }
                NewsListActivity.this.startActivity(mainIntent);
                NewsListActivity.this.finish();
            });
        }
        Addbtn.setOnClickListener(view->{
            Intent mainintent = new Intent(NewsListActivity.this, NewsInfoActivity.class);
            mainintent.putExtra("add", "2");
            mainintent.putExtra("role", arguments.get("role").toString());
            NewsListActivity.this.startActivity(mainintent);
            NewsListActivity.this.finish();
        });
        ExitBtn.setOnClickListener(view->{
            Intent mainIntent= new Intent(NewsListActivity.this, MainActivity.class);
            NewsListActivity.this.startActivity(mainIntent);
            NewsListActivity.this.finish();
        });
    }
}