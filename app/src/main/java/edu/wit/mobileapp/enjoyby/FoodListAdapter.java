package edu.wit.mobileapp.enjoyby;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FoodListAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;

    public FoodListAdapter(Context context, int rid, List<ListItem> list) {
        super(context, rid, list);
        mInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem item = (ListItem)getItem(position);
        View view = mInflater.inflate(R.layout.list_item, null);


        TextView daysLeft;
        daysLeft = (TextView)view.findViewById(R.id.days_left);

        if (item.expireDate.before(new Date())) {
            view.setBackgroundColor(Color.parseColor("#DE8F8F"));
            daysLeft.setText("EXPIRED");
        }
        else {
            daysLeft.setText(Long.toString(getDaysUntil(item.expireDate)) + " days");
        }

        Button nameButton = (Button) view.findViewById(R.id.name_button);
        nameButton.setText(item.name);
        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                Intent intent = new Intent();
                intent.setClass(getContext(), EditItem.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                extras.putInt("ID", item.Id);
                extras.putString("NAME", item.name);
                extras.putLong("PURCHASE_DATE", item.purchaseDate.getTime());
                extras.putLong("EXPIRATION_DATE", item.expireDate.getTime());
                intent.putExtras(extras);

                getContext().startActivity(intent);
            }
        });

        Button eatButton = (Button)view.findViewById(R.id.eat_button);
        if(item.expireDate.before(new Date())) {
            eatButton.setText("Toss");
        }
        eatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("myApp", String.valueOf(item.expireDate.before(new Date())));
                //Remove by Id from Database
                DatabaseHandler dbHelper = new DatabaseHandler(getContext().getApplicationContext());
                dbHelper.deleteFoodItem(item.Id);

                //Refresh view
                Intent intent = new Intent();
                intent.setClass(getContext(), MainActivity.class);
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    private long getDaysUntil(Date date) {
        long diff = date.getTime() - new Date().getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
