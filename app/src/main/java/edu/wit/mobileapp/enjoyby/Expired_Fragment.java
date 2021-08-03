package edu.wit.mobileapp.enjoyby;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Expired_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Expired_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Expired_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Expired_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Expired_Fragment newInstance(String param1, String param2) {
        Expired_Fragment fragment = new Expired_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        ArrayList<ListItem> items = new ArrayList<ListItem>();

        ListItem item1 = new ListItem();
        item1.name = "Food 1";
        item1.purchaseDate = new Date();
        item1.expireDate = new Date();
        items.add(item1);

        ListItem item2 = new ListItem();
        item2.name = "Food 2";
        item2.purchaseDate = new Date();
        item2.expireDate = new Date();
        items.add(item2);

        ListView listView = (ListView)view.findViewById(R.id.list_all);

        FoodListAdapter adapter = new FoodListAdapter(getContext(), 0, items);
        listView.setAdapter(adapter);

        return view;
    }
}