package edu.wit.mobileapp.enjoyby;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fresh_Fragment extends Fragment {

    public Fresh_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fresh, container, false);

        DatabaseHandler dbHelper = new DatabaseHandler(getContext());
        List<ListItem> items = dbHelper.getExpiredFoodItems();

        ListView listView = (ListView)view.findViewById(R.id.list_fresh);

        FoodListAdapter adapter = new FoodListAdapter(getContext(), 0, items);
        listView.setAdapter(adapter);

        return view;
    }
}