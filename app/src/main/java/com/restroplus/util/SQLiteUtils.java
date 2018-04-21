package com.restroplus.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by M1037764 on 1/9/2018.
 */

public class SQLiteUtils extends SQLiteOpenHelper{

    private static String dbName = "item_db";

    private static int dbVersion = 1;

    private static String tableName = "item";


    public SQLiteUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    SQLiteUtils(Context context){
        super(context,dbName,null,dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE ITEM(itemName text , itemPrice double, itemCategory text, quantity integer, inCart boolean)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tableName);
        this.onCreate(db);
    }


}
