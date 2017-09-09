package com.example.brandon.banpatito2;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Brandon on 08-Sep-17.
 */

public class CustomerInfo {

    private int position;
    private String name;
    private int operations;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

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

    public CustomerInfo(int position, String name, int operations){
        this.position = position;
        this.name = name;
        this.operations = operations;

    }

}
