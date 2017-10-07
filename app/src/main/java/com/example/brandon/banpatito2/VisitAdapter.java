package com.example.brandon.banpatito2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.brandon.banpatito2.Models.Visit;
import com.example.brandon.banpatito2.Utils.CustomerHelper;

/**
 * Created by Brandon on 06-Oct-17.
 */

public class VisitAdapter extends ArrayAdapter<Visit> {
    Context context;
    public VisitAdapter(Context context) {
        super(context, R.layout.customer_info, R.id.lblName);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final View oView = super.getView(position, convertView, parent);

        TextView txtViewName = (TextView) oView.findViewById(R.id.lblName);
        TextView txtViewOperations = (TextView) oView.findViewById(R.id.lblOperations);

        Visit oVisit = this.getItem(position);

        CustomerHelper oCustomerHelper = new CustomerHelper(context);
        oCustomerHelper.open();
        String customerName = oCustomerHelper.getCustomerName(oVisit.getCustomerId());
        txtViewName.setText(customerName);
        txtViewOperations.setText(oVisit.getOperations() + "");

        return oView;
    }
}
