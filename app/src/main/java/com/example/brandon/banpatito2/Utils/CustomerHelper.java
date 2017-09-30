package com.example.brandon.banpatito2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.brandon.banpatito2.CustomerInfo;

import java.util.ArrayList;

/**
 * Created by Brandon on 22-Sep-17.
 */

public class CustomerHelper {

    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] CUSTOMERS_TABLE_COLUMNS = {
        DBUtils.CUSTOMER_ID,
        DBUtils.CUSTOMER_NAME,
        DBUtils.CUSTOMER_OPERATIONS,
        DBUtils.CUSTOMER_POSITION
    };

    public CustomerHelper(Context context){
        dbHelper = new DBUtils(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<CustomerInfo> getAllCustomers () {
        ArrayList<CustomerInfo> listCustomers = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.CUSTOMER_TABLE_NAME,CUSTOMERS_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            listCustomers.add(parseCustomer(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return listCustomers;
    }

    private CustomerInfo parseCustomer(Cursor cursor){
        CustomerInfo oCustomer = new CustomerInfo(cursor.getInt(cursor.getColumnIndex(DBUtils.CUSTOMER_ID)));
        oCustomer.setName(cursor.getString(cursor.getColumnIndex(DBUtils.CUSTOMER_NAME)));
        oCustomer.setOperations(cursor.getInt(cursor.getColumnIndex(DBUtils.CUSTOMER_OPERATIONS)));
        oCustomer.setPosition(cursor.getInt(cursor.getColumnIndex(DBUtils.CUSTOMER_POSITION)));
        return  oCustomer;
    }

    public CustomerInfo addCustomer(String name, int operations, int position){
        ContentValues values = new ContentValues();
        values.put(DBUtils.CUSTOMER_NAME,name);
        values.put(DBUtils.CUSTOMER_OPERATIONS,operations);
        values.put(DBUtils.CUSTOMER_POSITION,position);

        long customerId = database.insert(DBUtils.CUSTOMER_TABLE_NAME, null, values);

        Cursor cursor=database.query(DBUtils.CUSTOMER_TABLE_NAME,
                CUSTOMERS_TABLE_COLUMNS,
                DBUtils.CUSTOMER_ID + " = " + customerId,
                null, null, null, null);
        cursor.moveToFirst();
        CustomerInfo customer = parseCustomer(cursor);
        cursor.close();

        return customer;
    }

    public int deleteCustomer(int nCustomerID){
        return database.delete(DBUtils.CUSTOMER_TABLE_NAME, DBUtils.CUSTOMER_ID + " = " + nCustomerID, null);
    }
}
