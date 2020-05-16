package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Women.db";
    public static final String TABLE_NAME = "women_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PWD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,PWD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String email,String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,pwd);
        long result = db.update(TABLE_NAME,contentValues,"ID = 1",null);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean isLoginValid(String username, String password) {
        String sql = "Select count(*) from women_table where NAME='" + username + "' and PWD='" + password + "'";
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        statement.close();

        if (l == 1) {
            return true;

        } else {
            return false;
        }
    }
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }
}
