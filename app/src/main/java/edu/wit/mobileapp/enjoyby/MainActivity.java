package edu.wit.mobileapp.enjoyby;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;

import edu.wit.mobileapp.enjoyby.ui.main.SectionsPagerAdapter;
import edu.wit.mobileapp.enjoyby.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("myApp", "Main Activity Created");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);


        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addItemIntent = new Intent();
                addItemIntent.setClass(MainActivity.this, AddItem.class);

                startActivity(addItemIntent);
            }
        });

        Button goShoppingButton = binding.goShoppingButton;
        goShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToShoppingIntent = new Intent();
                goToShoppingIntent.setClass(MainActivity.this, Shopping_List.class);

                startActivity(goToShoppingIntent);
            }
        });
    }

    private void InitializeData() {
        DatabaseHandler dbHelper = new DatabaseHandler(getApplicationContext());
        Calendar calPrch = Calendar.getInstance();
        Calendar calExp = Calendar.getInstance();
        calPrch.add(Calendar.DATE, -2);
        calExp.add(Calendar.DATE, -1);
        dbHelper.createFoodItem("Apple exp", calPrch.getTime(), calExp.getTime());

        calExp.add(Calendar.DATE, 10);
        dbHelper.createFoodItem("Apple new", calPrch.getTime(), calExp.getTime());

        calPrch.add(Calendar.DATE, -1);
        calExp.add(Calendar.DATE, 5);
        dbHelper.createFoodItem("milk new", calPrch.getTime(), calExp.getTime());
    }
}