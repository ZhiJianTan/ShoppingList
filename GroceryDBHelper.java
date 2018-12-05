package com.example.zaphk.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import contract java class to simplify code
import com.example.zaphk.shoppinglist.GroceryContract.*;

public class GroceryDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "shoppinglist.db";
    public static final int DATABSE_VERSION = 1;

    public GroceryDBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GROCERYLIST_TABLE="CREATE TABLE " +
                GroceryEntry.TABLE_NAME + " ( "+
                GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                GroceryEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                GroceryEntry.COLUMN_AMOUNT+" INTEGER NOT NULL, "+
                GroceryEntry.COLUMN_TIMESTAMP+ " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
