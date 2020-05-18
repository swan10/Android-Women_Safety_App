package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class FavContactDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FavContact.db";
    public static final String TABLE_NAME = "favContact_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "P1";
    public static final String COL_3 = "PC1";
    public static final String COL_4 = "P2";
    public static final String COL_5 = "PC2";
    public static final String COL_6 = "P3";
    public static final String COL_7 = "PC3";

    public FavContactDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,P1 TEXT,PC1 INTEGER,P2 TEXT,PC2 INTEGER," +
                "P3 TEXT,PC3 INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    //insert or update
    public boolean insertData(String p1,String pc1,String p2,String pc2,String p3,String pc3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,p1);
        contentValues.put(COL_3,pc1);
        contentValues.put(COL_4,p2);
        contentValues.put(COL_5,pc2);
        contentValues.put(COL_6,p3);
        contentValues.put(COL_7,pc3);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    //retrieve all
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ID = (SELECT MAX(ID) FROM "+TABLE_NAME+")",null);
        return cursor;
    }

    //fetch exact data
    //used in menu UI for call emergency
    public String getData(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ID = (SELECT MAX(ID) FROM "+TABLE_NAME+")",null);
        StringBuffer buffer= new StringBuffer();
        while (res.moveToNext())
        {

            String name =res.getString(res.getColumnIndex(s));

            buffer.append(name);
        }
        return buffer.toString();

    }

}
