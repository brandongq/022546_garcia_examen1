package com.example.brandon.banpatito2;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Brandon on 08-Sep-17.
 */

public class CustomerInfo implements Parcelable{

    private int id;
    private String name;
    private int operations;
    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public CustomerInfo(){

    }

    public CustomerInfo(int id){
        this.id = id;
    }

    public CustomerInfo(String name, int operations){
        this.name = name;
        this.operations = operations;
        this.position = 0;
        this.id = 0;

    }

    // ------------------ PARCELABLE STUFF ------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(operations);
    }

    public static final Creator<CustomerInfo> CREATOR = new Creator<CustomerInfo>() {
        @Override
        public CustomerInfo createFromParcel(Parcel in) {
            return new CustomerInfo(in);
        }

        @Override
        public CustomerInfo[] newArray(int size) {
            return new CustomerInfo[size];
        }
    };

    protected CustomerInfo(Parcel in) {
        name = in.readString();
        operations = in.readInt();
    }

    // ------------------ PARCELABLE STUFF ------------------
}
