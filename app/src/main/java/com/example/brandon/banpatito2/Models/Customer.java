package com.example.brandon.banpatito2.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Brandon on 06-Oct-17.
 */

public class Customer implements Parcelable{

    //Properties
    private int id;
    private String name;

    //Constructors
    public Customer(){

    }

    public Customer(int id){
        this.id = id;
    }

    public Customer(int id, String name){
        this.id = id;
        this.name = name;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ------------------ PARCELABLE STUFF ------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    protected Customer(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    // ------------------ PARCELABLE STUFF ------------------
}
