package com.example.giuaky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseLogin extends SQLiteOpenHelper {

    public static final String DBNAME = "login.db";

    public DatabaseLogin(Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create table users(key_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ho TEXT, ten TEXT, gender TEXT, username TEXT unique, SDT TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop table if exists users");
    }

    public Boolean insertData(String ho, String ten, String gender, String username, String SDT, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ho", ho);
        values.put("ten", ten);
        values.put("gender", gender);
        values.put("username", username);
        values.put("SDT", SDT);
        values.put("password", password);

        long result = db.insert("users", null, values);
        if (result == -1) return false;
        else
            return true;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkuserNamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[] {username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public User getUser(String username){
        SQLiteDatabase db = getReadableDatabase();
        String sql= "SELECT * FROM users WHERE username = ?";
        String[] selectionArgs = { username };
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        User user = null;
        if(cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setHo(cursor.getString(1));
            user.setTen(cursor.getString(2));
            user.setGender(cursor.getString(3));
            user.setUsername(cursor.getString(4));
            user.setSdt(cursor.getString(5));
        }
        cursor.close();
        return user;
    }

}
