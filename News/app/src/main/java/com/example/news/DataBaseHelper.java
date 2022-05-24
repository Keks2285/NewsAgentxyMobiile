package com.example.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public  DataBaseHelper(Context context){
        super(context,"NewsData.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table Role(ID_Role INTEGER PRIMARY KEY AUTOINCREMENT, NameRole TEXT);");
        sqLiteDatabase.execSQL("create Table Users(ID_User INTEGER PRIMARY KEY AUTOINCREMENT not null,  Login TEXT not null UNIQUE, Password TEXT not null, Role_ID INTEGER NOT NULL, FOREIGN KEY (Role_ID) REFERENCES Role(ID_Role));");
        sqLiteDatabase.execSQL("create Table News(ID_News INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, DateNews TEXT, Description TEXT);");

        sqLiteDatabase.execSQL("INSERT INTO Role values(1,'Администратор');");
        sqLiteDatabase.execSQL("INSERT INTO Role values(2,'Пользователь');");
        sqLiteDatabase.execSQL("INSERT INTO Users values(1,'novikov','pas123',1);");
        sqLiteDatabase.execSQL("INSERT INTO News values(1,'Первая новость', '2022.01.11','новостьновостьновостьновостьновостьновостьновость');");
        sqLiteDatabase.execSQL("INSERT INTO News values(2,'Вторая новость', '2023.01.20','новость2новость2новость2новость2новость2новость2новость2новость2');");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists Role");
        sqLiteDatabase.execSQL("drop Table if exists Users");
        sqLiteDatabase.execSQL("drop Table if exists News");
    }

    public Boolean insertUser( String Login, String Password,int Role_ID)
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Users where Login=?",new String[]{Login});
        if(cursor.getCount()>0) return false;
        else {
            DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Login", Login);
            contentValues.put("Password", Password);
            contentValues.put("Role_ID", Role_ID);
            long result = DB.insert("Users", null, contentValues);
            return result != -1;
        }
    }

    public Cursor getAllNews()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from News",null);
    }

    public Cursor getNew(String Title,String DateNews)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from News where Title=? and DateNews=?",new String[]{Title,DateNews});
    }

    public Boolean UpdateNews(String id, String Title, String DateNews, String Description)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", Title);
        contentValues.put("DateNews", DateNews);
        contentValues.put("Description", Description);
        long result = DB.update("News",contentValues,"ID_News = ?",new String[] {id});
        return result != -1;
    }
    public Boolean insertNews(String Title, String DateNews, String Description)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", Title);
        contentValues.put("DateNews", DateNews);
        contentValues.put("Description", Description);
        long result = DB.insert("News", null, contentValues);
        return result != -1;
    }
    public Boolean DeleteNews(String id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("News","ID_News = ?", new String[] {id});
        return result != -1;
    }
    public Cursor checkRoleUser(String Login, String Password){
        SQLiteDatabase DB = this.getReadableDatabase();
        DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Users where Login=? and Password=?",new String[]{Login,Password});
        if(cursor.getCount()>0) return DB.rawQuery("Select Role_ID from Users where Login=? and Password=?",new String[]{Login,Password});
        else return null;
    }
}
