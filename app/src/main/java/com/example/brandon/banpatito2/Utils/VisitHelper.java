package com.example.brandon.banpatito2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.brandon.banpatito2.Models.Visit;

import java.util.ArrayList;

/**
 * Created by Brandon on 06-Oct-17.
 */

public class VisitHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] VISITS_TABLE_COLUMNS = {
            DBUtils.VISIT_ID,
            DBUtils.VISIT_CUSTOMER_ID,
            DBUtils.VISIT_OPERATIONS,
            DBUtils.VISIT_POSITION,
            DBUtils.VISIT_DATE,
            DBUtils.VISIT_TIME
    };

    public VisitHelper(Context context) {
        this.dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Visit> getAllVisits() {
        ArrayList<Visit> listVisits = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.VISIT_TABLE_NAME, VISITS_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listVisits.add(parseVisit(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return listVisits;
    }

    public ArrayList<Visit> getVisitsOfToday(String date) {
        ArrayList<Visit> listVisits = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.VISIT_TABLE_NAME,
                VISITS_TABLE_COLUMNS,
                DBUtils.VISIT_DATE + " = " + date,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listVisits.add(parseVisit(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return listVisits;
    }

    private Visit parseVisit(Cursor cursor) {
        Visit oVisit = new Visit(cursor.getInt(cursor.getColumnIndex(DBUtils.VISIT_ID)));
        oVisit.setCustomerId(cursor.getInt(cursor.getColumnIndex(DBUtils.VISIT_CUSTOMER_ID)));
        oVisit.setOperations(cursor.getInt(cursor.getColumnIndex(DBUtils.VISIT_OPERATIONS)));
        oVisit.setPosition(cursor.getInt(cursor.getColumnIndex(DBUtils.VISIT_OPERATIONS)));
        oVisit.setVisitDate(cursor.getString(cursor.getColumnIndex(DBUtils.VISIT_DATE)));
        oVisit.setVisitTime(cursor.getString(cursor.getColumnIndex(DBUtils.VISIT_TIME)));
        return oVisit;
    }

    public Visit addVisit(int operations, int position, String visitDate, String visitTime, int customerId) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.VISIT_OPERATIONS, operations);
        values.put(DBUtils.VISIT_POSITION, position);
        values.put(DBUtils.VISIT_DATE, visitDate);
        values.put(DBUtils.VISIT_TIME, visitTime);
        values.put(DBUtils.VISIT_CUSTOMER_ID, customerId);

        long visitId = database.insert(DBUtils.VISIT_TABLE_NAME, null, values);

        Cursor cursor = database.query(DBUtils.VISIT_TABLE_NAME,
                VISITS_TABLE_COLUMNS,
                DBUtils.VISIT_ID + " = " + visitId,
                null, null, null, null);
        cursor.moveToFirst();
        Visit visit = parseVisit(cursor);
        cursor.close();

        return visit;
    }

    public int deleteVisit(int nVisitID) {
        return database.delete(DBUtils.VISIT_TABLE_NAME, DBUtils.VISIT_ID + " = " + nVisitID, null);
    }

}