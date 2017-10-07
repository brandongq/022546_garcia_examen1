package com.example.brandon.banpatito2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.brandon.banpatito2.Models.Customer;
import com.example.brandon.banpatito2.Models.Visit;

import java.util.ArrayList;

public class ArrangedActivity extends AppCompatActivity {

    VisitAdapter oVisitAdapter;
    ListView oListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arranged);

        oListView = (ListView) findViewById(R.id.lvArranged);
        oVisitAdapter = new VisitAdapter(this);
        oListView.setAdapter(oVisitAdapter);

        ArrayList<Visit> visits  = getIntent().getParcelableArrayListExtra("data");

        displayQueue(visits);
    }


    private void displayQueue(ArrayList<Visit> listVisits)
    {
        oVisitAdapter.clear();

        for (Visit oVisit : listVisits){
            oVisitAdapter.add(oVisit);
        }
        oVisitAdapter.notifyDataSetChanged();
    }
}


