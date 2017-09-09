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

/**
 * Created by Brandon on 08-Sep-17.
 */

public class CustomerAdapter extends ArrayAdapter<CustomerInfo> {

    public CustomerAdapter (Context context) {
        super(context, R.layout.customer_info, R.id.lblPosition);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final View oView = super.getView(position, convertView, parent);

        TextView txtViewPosition = (TextView) oView.findViewById(R.id.lblPosition);
        TextView txtViewName = (TextView) oView.findViewById(R.id.lblName);
        TextView txtViewOperations = (TextView) oView.findViewById(R.id.lblOperations);

        CustomerInfo oCustomer = this.getItem(position);

        txtViewPosition.setText(oCustomer.getPosition() + "");
        txtViewName.setText(oCustomer.getName());
        txtViewOperations.setText(oCustomer.getOperations() + "");

        return oView;
    }
}
