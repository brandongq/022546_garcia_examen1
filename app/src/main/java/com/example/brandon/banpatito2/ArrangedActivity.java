package com.example.brandon.banpatito2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ArrangedActivity extends AppCompatActivity {

    CustomerAdapter oCustomerAdapter;
    ListView oListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arranged);

        oListView = (ListView) findViewById(R.id.lvArranged);
        oCustomerAdapter = new CustomerAdapter(this);
        oListView.setAdapter(oCustomerAdapter);

        ArrayList<CustomerInfo> customers  = getIntent().getParcelableArrayListExtra("data");

        displayQueue(customers);
    }


    private void displayQueue(ArrayList<CustomerInfo> listCustomers)
    {
        oCustomerAdapter.clear();

        for (CustomerInfo oCustomer : listCustomers){
            oCustomerAdapter.add(oCustomer);
        }
        oCustomerAdapter.notifyDataSetChanged();
    }
}


