package edu.wit.mobileapp.enjoyby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddItem extends AppCompatActivity {

    Button cancelButton;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        cancelButton = (Button) findViewById(R.id.cancel_item_button);
        addButton = (Button) findViewById(R.id.add_item_button);

        EditText name = (EditText) findViewById(R.id.item_name);
        EditText purchaseDate = (EditText) findViewById(R.id.purchase_date);
        EditText expireDate = (EditText) findViewById(R.id.expire_date);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent();
                cancelIntent.setClass(AddItem.this, MainActivity.class);

                startActivity(cancelIntent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbHelper = new DatabaseHandler(getApplicationContext());

                String productName = name.getText().toString();
                Date purchased = stringToDate(purchaseDate.getText().toString());
                Date expiresAt = stringToDate(expireDate.getText().toString());

                Log.v("myApp", "Product Name: " + productName);
                if(productName.matches("")) {
                    Log.v("myApp", "Empty Item Name");
                    Snackbar.make(v, "You wanna give this thing a name?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                dbHelper.createFoodItem(productName, purchased, expiresAt);
                Intent createIntent = new Intent();
                createIntent.setClass(AddItem.this, MainActivity.class);

                startActivity(createIntent);
            }
        });

        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener purchase = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                purchaseDate.setText(getDateFormat().format(calendar.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener expire = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                expireDate.setText(getDateFormat().format(calendar.getTime()));
            }
        };

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DAY = calendar.get(Calendar.DAY_OF_MONTH);

        purchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddItem.this, purchase, YEAR, MONTH, DAY).show();
            }
        });

        expireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddItem.this, expire, YEAR, MONTH+1, DAY).show();
            }
        });
    }

    private SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("MM/dd/yy", Locale.US);
    }

    private Date stringToDate(String str) {
        try {
            return getDateFormat().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date();
    }
}