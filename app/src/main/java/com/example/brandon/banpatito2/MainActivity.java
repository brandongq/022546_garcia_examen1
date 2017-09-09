package com.example.brandon.banpatito2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<CustomerInfo> customers = new ArrayList<CustomerInfo>();
    CustomerAdapter oCustomerAdapter;
    ListView oListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddCustomer = (Button) findViewById(R.id.btnAdd);
        Button btnCalculateQueue = (Button) findViewById(R.id.btnCalculateQueue);

        oListView = (ListView) findViewById(R.id.lvCustomers);
        oCustomerAdapter = new CustomerAdapter(this);
        oListView.setAdapter(oCustomerAdapter);

        btnAddCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView tvName = (TextView) findViewById(R.id.tbCustomerName);
                TextView tvOperations = (TextView) findViewById(R.id.tbOperations);

                String name = tvName.getText().toString();
                String operations = tvOperations.getText().toString();

                if(customers.size() == 0)
                {
                    clearListView();
                }

                if(name.isEmpty())
                    Toast.makeText(getApplicationContext(), "Enter A Valid Name", Toast.LENGTH_LONG).show();
                else if (operations == "" || tryParseInt(operations) == false ) {
                    Toast.makeText(getApplicationContext(), "Invalid Operation Amount", Toast.LENGTH_LONG).show();
                }
                else if (Integer.parseInt(operations) <= 0)
                    Toast.makeText(getApplicationContext(), "Operations Must Be 1 Or Greater", Toast.LENGTH_LONG).show();
                else{
                    int NoOper = Integer.parseInt(operations);
                    if (NoOper == 1)
                        customers.add(new CustomerInfo(0, name,Integer.parseInt(operations)));
                    else
                        customers.add(new CustomerInfo(1, name, Integer.parseInt(operations)));

                    Toast.makeText(getApplicationContext(), name + " Added", Toast.LENGTH_SHORT).show();

                    tvName.setText("");
                    tvOperations.setText("");
                    tvName.requestFocus();
                }

            }
        });

        btnCalculateQueue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(customers.size() == 0)
                    Toast.makeText(getApplicationContext(), "No Customers", Toast.LENGTH_SHORT).show();
                else {
                    arrangeQueue();
                    displayQueue(customers);
                    customers.clear();
                }
            }
        });

    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void displayQueue(ArrayList<CustomerInfo> listCustomers)
    {
        oCustomerAdapter.clear();

        for (CustomerInfo oCustomer : listCustomers){
            oCustomerAdapter.add(oCustomer);
        }
        oCustomerAdapter.notifyDataSetChanged();
    }

    private void arrangeQueue()
    {
        int totalCustomers = customers.size();
        //int oneOperation = 1;
        for (int i = 0; i < totalCustomers; i++) {
            CustomerInfo customer = customers.get(i);
            if(customer.getPosition() == 0)
                customer.setPosition(i+1);
            else
            {
                customers.remove(i);
                customer.setPosition(0);
                customers.add(customer);
                i--;
            }
        }
    }

    private void clearListView()
    {
        oCustomerAdapter.clear();
        oCustomerAdapter.notifyDataSetChanged();
    }
}