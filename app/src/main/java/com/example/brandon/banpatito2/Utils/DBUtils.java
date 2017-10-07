package com.example.brandon.banpatito2.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brandon on 22-Sep-17.
 */

public class DBUtils extends SQLiteOpenHelper {

    public static final String DB_NAME = "BanPatito.db";
    public static final int DB_VERSION = 11;

    public static final String CUSTOMER_TABLE_NAME = "Customer";
    public static final String CUSTOMER_ID = "Id";
    public static final String CUSTOMER_NAME = "Name";

    public static final String VISIT_TABLE_NAME = "Visit";
    public static final String VISIT_ID = "Id";
    public static final String VISIT_CUSTOMER_ID = "CustomerId";
    public static final String VISIT_OPERATIONS = "Operations";
    public static final String VISIT_POSITION = "Position";
    public static final String VISIT_DATE = "VisitDate";
    public static final String VISIT_TIME = "VisitTime";

    private static final String DB_CREATE_TABLE_CUSTOMER = "CREATE TABLE " + CUSTOMER_TABLE_NAME + " (" +
            CUSTOMER_ID + " INTEGER PRIMARY KEY, " +
            CUSTOMER_NAME + " TEXT NOT NULL);";
    private static final String DB_CREATE_TABLE_VISIT = "CREATE TABLE " + VISIT_TABLE_NAME + " (" +
            VISIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            VISIT_CUSTOMER_ID + " INTEGER NOT NULL, " +
            VISIT_OPERATIONS + " INTEGER NOT NULL, " +
            VISIT_POSITION + " INTEGER, " +
            VISIT_DATE + " TEXT NOT NULL," +
            VISIT_TIME + " TEXT NOT NULL," +
            "FOREIGN KEY(" + VISIT_CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE_NAME + "(" + CUSTOMER_ID + "));";

    public DBUtils(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE_TABLE_CUSTOMER);
        sqLiteDatabase.execSQL(DB_CREATE_TABLE_VISIT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CustomerInfo");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Customer");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Visit");
        onCreate(sqLiteDatabase);
    }
}
