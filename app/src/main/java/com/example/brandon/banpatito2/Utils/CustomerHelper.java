package com.example.brandon.banpatito2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.brandon.banpatito2.Models.Customer;

import java.util.ArrayList;

/**
 * Created by Brandon on 22-Sep-17.
 */

public class CustomerHelper {

    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] CUSTOMERS_TABLE_COLUMNS = {
            DBUtils.CUSTOMER_ID,
            DBUtils.CUSTOMER_NAME
    };

    public CustomerHelper(Context context) {
        dbHelper = new DBUtils(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> listCustomers = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.CUSTOMER_TABLE_NAME, CUSTOMERS_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listCustomers.add(parseCustomer(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return listCustomers;
    }

    public String getCustomerName(int id){
        Cursor cursor = database.query(DBUtils.CUSTOMER_TABLE_NAME,
                new String[] {DBUtils.CUSTOMER_NAME},
                DBUtils.CUSTOMER_ID+ " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(DBUtils.CUSTOMER_NAME));
        cursor.close();
        return name;
    }

    private Customer parseCustomer(Cursor cursor) {
        Customer oCustomer = new Customer(cursor.getInt(cursor.getColumnIndex(DBUtils.CUSTOMER_ID)));
        oCustomer.setName(cursor.getString(cursor.getColumnIndex(DBUtils.CUSTOMER_NAME)));
        return oCustomer;
    }

    public Customer addCustomer(int id, String name) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.CUSTOMER_ID, id);
        values.put(DBUtils.CUSTOMER_NAME, name);

        database.insert(DBUtils.CUSTOMER_TABLE_NAME, null, values);

        Cursor cursor = database.query(DBUtils.CUSTOMER_TABLE_NAME,
                CUSTOMERS_TABLE_COLUMNS,
                DBUtils.CUSTOMER_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        Customer customer = parseCustomer(cursor);
        cursor.close();

        return customer;
    }

    public int deleteCustomer(int nCustomerID) {
        return database.delete(DBUtils.CUSTOMER_TABLE_NAME, DBUtils.CUSTOMER_ID + " = " + nCustomerID, null);
    }
}
