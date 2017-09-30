package com.example.brandon.banpatito2.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brandon on 22-Sep-17.
 */

public class DBUtils extends SQLiteOpenHelper{

    public static final String DB_NAME="BanPatito.db";
    public static final int DB_VERSION=1;

    public static final String CUSTOMER_TABLE_NAME = "CustomerInfo";
    public static final String CUSTOMER_ID = "Id";
    public static final String CUSTOMER_NAME = "Name";
    public static final String CUSTOMER_OPERATIONS = "Operations";
    public static final String CUSTOMER_POSITION = "Position";

    private static final String DB_CREATE = "CREATE TABLE "+ CUSTOMER_TABLE_NAME + " (" +
            CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            CUSTOMER_NAME + " TEXT NOT NULL, " +
            CUSTOMER_OPERATIONS + " INTEGER NOT NULL, " +
            CUSTOMER_POSITION + " INTEGER NOT NULL)";

    public DBUtils(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CustomerInfo");
        onCreate(sqLiteDatabase);
    }
}
