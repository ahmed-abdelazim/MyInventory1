package com.example.android.myinventory1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.myinventory1.data.Contract.Entry;

// import data.Contract.Entry to be able to use class Entry directly here.

/**
 * Created by Person on 21/08/2018.
 */

public class DbHelper extends SQLiteOpenHelper {


    //to log
    public static final String LOG_TAG = DbHelper.class.getSimpleName();

    //Database name and version
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create table
        String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + Entry.TABLE_NAME + " ("
                + Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Entry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + Entry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL, "
                + Entry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL, "
                + Entry.COLUMN_PRODUCT_SUPPLIER_NAME + " INTEGER NOT NULL DEFAULT 0, "
                + Entry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER + " INTEGER );";

        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT_TABLE);

        Log.d("successfully message", "created table of db");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
