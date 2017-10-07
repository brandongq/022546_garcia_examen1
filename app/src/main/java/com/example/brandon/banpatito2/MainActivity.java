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

import com.example.brandon.banpatito2.Models.Customer;
import com.example.brandon.banpatito2.Models.Visit;
import com.example.brandon.banpatito2.Utils.CustomerHelper;
import com.example.brandon.banpatito2.Utils.VisitHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<Visit> visits = new ArrayList<>();
    CustomerAdapter oCustomerAdapter;
    VisitAdapter oVisitAdapter;
    ListView oListView;
    public static final int RETURN_CODE=1;
    VisitHelper oVisitHelper = new VisitHelper(this);
    CustomerHelper oCustomerHelper = new CustomerHelper(this);
    Date date;
    DateFormat dateFormatTime = new SimpleDateFormat("HHmmss");
    DateFormat dateFormatDay = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAddCustomer = (Button) findViewById(R.id.btnAdd);
        Button btnCalculateQueue = (Button) findViewById(R.id.btnCalculateQueue);

        oListView = (ListView) findViewById(R.id.lvCustomers);
        //oCustomerAdapter = new CustomerAdapter(this);
        oVisitAdapter = new VisitAdapter(this);
        oListView.setAdapter(oVisitAdapter);

        oCustomerHelper.open();
        customers = oCustomerHelper.getAllCustomers();
        oCustomerHelper.close();

        oVisitHelper.open();
        date = new Date();
        visits = oVisitHelper.getVisitsOfToday(dateFormatDay.format(date));
        oVisitHelper.close();

        displayQueue(visits);

        btnAddCustomer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView tvCustomerId = (TextView) findViewById(R.id.tbCustomerID);
                TextView tvName = (TextView) findViewById(R.id.tbCustomerName);
                TextView tvOperations = (TextView) findViewById(R.id.tbOperations);

                String customerId = tvCustomerId.getText().toString();
                String name = tvName.getText().toString();
                String operations = tvOperations.getText().toString();

                if(customerId.isEmpty() || !tryParseInt(customerId))
                    Toast.makeText(getApplicationContext(), "Enter A Valid ID", Toast.LENGTH_LONG).show();
                else if(name.isEmpty())
                    Toast.makeText(getApplicationContext(), "Enter A Valid Name", Toast.LENGTH_LONG).show();
                else if (operations.isEmpty() || !tryParseInt(operations))
                    Toast.makeText(getApplicationContext(), "Invalid Operation Amount", Toast.LENGTH_LONG).show();
                else if (Integer.parseInt(customerId) <= 0)
                    Toast.makeText(getApplicationContext(), "The ID Must Be Greater Than 0", Toast.LENGTH_LONG).show();
                else if (Integer.parseInt(operations) <= 0)
                    Toast.makeText(getApplicationContext(), "Operations Must Be 1 Or Greater", Toast.LENGTH_LONG).show();
                else {
                    int customerIdInt = Integer.parseInt(customerId);
                    int operationsInt = Integer.parseInt(operations);

                    if (!customerExists(customerIdInt)) {
                        oCustomerHelper.open();
                        Customer customer = oCustomerHelper.addCustomer(customerIdInt, name);
                        oCustomerHelper.close();
                        customers.add(customer);
                        Toast.makeText(getApplicationContext(), "Customer " + name + " added", Toast.LENGTH_SHORT).show();
                    }

                    oVisitHelper.open();
                    date = new Date();
                    String dateStr = dateFormatDay.format(date);
                    String timeStr = dateFormatTime.format(date);
                    Visit visit = oVisitHelper.addVisit(operationsInt, 0, dateStr, timeStr, customerIdInt);
                    oVisitHelper.close();
                    visits.add(visit);
                    Toast.makeText(getApplicationContext(), "Visit registered", Toast.LENGTH_SHORT).show();

                    displayQueue(visits);
                    tvCustomerId.setText("");
                    tvName.setText("");
                    tvOperations.setText("");
                    tvName.requestFocus();
                }
            }
        });

        oListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Visit visit = visits.get(i);
                oVisitAdapter.remove(visit);
                oVisitAdapter.notifyDataSetChanged();
                oVisitHelper.open();
                oVisitHelper.deleteVisit(visit.getId());
                oVisitHelper.close();
                visits.remove(i);
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

    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void displayQueue(ArrayList<Visit> listVisits) {
        oVisitAdapter.clear();

        for (Visit oVisit : listVisits){
            oVisitAdapter.add(oVisit);
        }
        oVisitAdapter.notifyDataSetChanged();
    }

    private boolean customerExists (int id)
    {
        boolean result = false;
        for (int i = 0; i < customers.size(); i++){
            if (customers.get(i).getId() == id){
                result = true;
                break;
            }
        }
        return  result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RETURN_CODE && data != null){
            customers = data.getParcelableArrayListExtra("arrayListUpdt");
        }
    }

    private ArrayList<Visit> arrangeQueue()
    {
        ArrayList<Visit> arrangedCustomers = new ArrayList<>();
        int totalCustomers = visits.size();
        int counter = totalCustomers;
        int i = 0;
        int position = 1;
        while (counter > 0) {
            Visit oVisit = new Visit(
                    visits.get(i).getId(),
                    visits.get(i).getOperations(),
                    visits.get(i).getPosition(),
                    visits.get(i).getVisitDate(),
                    visits.get(i).getVisitTime(),
                    visits.get(i).getCustomerId()
            );
            if (oVisit.getOperations() > 0) {
                oVisit.setPosition(position);
                oVisit.setOperations(oVisit.getOperations() - 1);
                arrangedCustomers.add(oVisit);
                if(oVisit.getOperations() == 0)
                    counter--;
                position++;
            }
            visits.set(i, oVisit);
            if(i == totalCustomers-1)
                i = 0;
            else
                i++;
        }

        return arrangedCustomers;
    }

}