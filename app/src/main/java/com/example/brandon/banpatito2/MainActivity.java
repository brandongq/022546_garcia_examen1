package com.example.brandon.banpatito2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandon.banpatito2.Utils.CustomerHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<CustomerInfo> customers = new ArrayList<CustomerInfo>();
    CustomerAdapter oCustomerAdapter;
    ListView oListView;
    public static final int RETURN_CODE=1;
    CustomerHelper oCustomerHelper = new CustomerHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddCustomer = (Button) findViewById(R.id.btnAdd);
        Button btnCalculateQueue = (Button) findViewById(R.id.btnCalculateQueue);

        oListView = (ListView) findViewById(R.id.lvCustomers);
        oCustomerAdapter = new CustomerAdapter(this);
        oListView.setAdapter(oCustomerAdapter);

        oCustomerHelper.open();
        customers = oCustomerHelper.getAllCustomers();
        oCustomerHelper.close();
        displayQueue(customers);

        btnAddCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView tvName = (TextView) findViewById(R.id.tbCustomerName);
                TextView tvOperations = (TextView) findViewById(R.id.tbOperations);

                String name = tvName.getText().toString();
                String operations = tvOperations.getText().toString();

                if(name.isEmpty())
                    Toast.makeText(getApplicationContext(), "Enter A Valid Name", Toast.LENGTH_LONG).show();
                else if (operations.equals("") || !tryParseInt(operations)) {
                    Toast.makeText(getApplicationContext(), "Invalid Operation Amount", Toast.LENGTH_LONG).show();
                }
                else if (Integer.parseInt(operations) <= 0)
                    Toast.makeText(getApplicationContext(), "Operations Must Be 1 Or Greater", Toast.LENGTH_LONG).show();
                else{
                    oCustomerHelper.open();
                    CustomerInfo customer = oCustomerHelper.addCustomer(name,Integer.parseInt(operations),0);
                    oCustomerHelper.close();
                    customers.add(customer);
                    Toast.makeText(getApplicationContext(), name + " Added", Toast.LENGTH_SHORT).show();
                    displayQueue(customers);
                    tvName.setText("");
                    tvOperations.setText("");
                    tvName.requestFocus();
                }

            }
        });

        oListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomerInfo customer = customers.get(i);
                oCustomerAdapter.remove(customer);
                oCustomerHelper.open();
                oCustomerHelper.deleteCustomer(customer.getId());
                oCustomerHelper.close();
                customers.remove(i);
                oCustomerAdapter.notifyDataSetChanged();
            }
        });

        btnCalculateQueue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(customers.size() == 0)
                    Toast.makeText(getApplicationContext(), "No Customers", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(getApplicationContext(), ArrangedActivity.class);
                    intent.putExtra("data", arrangeQueue());
                    startActivityForResult(intent, RETURN_CODE);
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

    private void displayQueue(ArrayList<CustomerInfo> listCustomers) {
        oCustomerAdapter.clear();

        for (CustomerInfo oCustomer : listCustomers){
            oCustomerAdapter.add(oCustomer);
        }
        oCustomerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RETURN_CODE && data != null){
            customers = data.getParcelableArrayListExtra("arrayListUpdt");
        }
    }

    private ArrayList<CustomerInfo> arrangeQueue()
    {
        ArrayList<CustomerInfo> arrangedCustomers = new ArrayList<CustomerInfo>();
        int totalCustomers = customers.size();
        int counter = totalCustomers;
        int i = 0;
        int position = 1;
        while (counter > 0) {
            CustomerInfo oCustomer = new CustomerInfo(
                    customers.get(i).getName(),
                    customers.get(i).getOperations()
            );
            if (oCustomer.getOperations() > 0) {
                oCustomer.setPosition(position);
                oCustomer.setOperations(oCustomer.getOperations() - 1);
                arrangedCustomers.add(oCustomer);
                if(oCustomer.getOperations() == 0)
                    counter--;
                position++;
            }
            customers.set(i, oCustomer);
            if(i == totalCustomers-1)
                i = 0;
            else
                i++;
        }

        return arrangedCustomers;
    }

}