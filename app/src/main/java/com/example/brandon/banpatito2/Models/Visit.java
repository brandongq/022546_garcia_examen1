package com.example.brandon.banpatito2.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Brandon on 06-Oct-17.
 */

public class Visit implements Parcelable {

    //Properties
    private int id;
    private int operations;
    private int position;
    private String visitDate;
    private String visitTime;
    private int customerId;

    //Constructors
    public Visit(){

    }

    public Visit(int id){
        this.id = id;
    }

    public Visit(int id, int operations, int position, String visitDate, String visitTime, int customerId){
        this.id = id;
        this.operations = operations;
        this.position = position;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.customerId = customerId;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperations() {
        return operations;
    }

    public void setOperations(int operations) {
        this.operations = operations;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    // ------------------ PARCELABLE STUFF ------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(customerId);
        parcel.writeInt(operations);
        parcel.writeInt(position);
        parcel.writeString(visitDate);
        parcel.writeString(visitTime);
    }

    public static final Parcelable.Creator<Visit> CREATOR = new Parcelable.Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    protected Visit(Parcel in) {
        id = in.readInt();
        customerId = in.readInt();
        operations = in.readInt();
        position = in.readInt();
        visitDate = in.readString();
        visitTime = in.readString();
    }

    // ------------------ PARCELABLE STUFF ------------------
}
