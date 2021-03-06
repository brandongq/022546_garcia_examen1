package com.example.brandon.banpatito2;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.brandon.banpatito2.Models.Customer;

/**
 * Created by Brandon on 08-Sep-17.
 */

public class CustomerAdapter extends ArrayAdapter<Customer> {

    public CustomerAdapter (Context context) {
        super(context, R.layout.customer_info, R.id.lblName);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final View oView = super.getView(position, convertView, parent);

        TextView txtViewName = (TextView) oView.findViewById(R.id.lblName);
        //TextView txtViewPosition = (TextView) oView.findViewById(R.id.lblPosition);
        TextView txtViewOperations = (TextView) oView.findViewById(R.id.lblOperations);

        Customer oCustomer = this.getItem(position);

        txtViewName.setText(oCustomer.getName());
        /*if (oCustomer.getPosition() == 0)
            txtViewPosition.setText("N/A");
        else
            txtViewPosition.setText(oCustomer.getPosition() + "");
        */
        //txtViewOperations.setText(oCustomer.getOperations() + "");
        txtViewOperations.setText("Still working!");

        return oView;
    }
}
