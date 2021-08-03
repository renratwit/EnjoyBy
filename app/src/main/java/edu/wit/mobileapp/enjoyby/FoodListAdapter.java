package edu.wit.mobileapp.enjoyby;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodListAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;

    public FoodListAdapter(Context context, int rid, List<ListItem> list) {
        super(context, rid, list);
        mInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem item = (ListItem)getItem(position);
        View view = mInflater.inflate(R.layout.list_item, null);

        TextView name;
        name = (TextView)view.findViewById(R.id.name);
        name.setText(item.name);

        TextView purchaseDate;
        purchaseDate = (TextView)view.findViewById(R.id.purchase_date);
        purchaseDate.setText(item.purchaseDate.toString());

        TextView expireDate;
        expireDate = (TextView)view.findViewById(R.id.expire_date);
        expireDate.setText(item.expireDate.toString());

        return view;
    }

}
