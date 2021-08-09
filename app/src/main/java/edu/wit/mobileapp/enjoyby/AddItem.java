package edu.wit.mobileapp.enjoyby;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddItem extends AppCompatActivity {

    Button cancelButton;
    Button addButton;

    final Calendar calendar = Calendar.getInstance();

    int YEAR = calendar.get(Calendar.YEAR);
    int MONTH = calendar.get(Calendar.MONTH);
    int DAY = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        cancelButton = (Button)findViewById(R.id.cancel_item_button);
        addButton = (Button)findViewById(R.id.add_item_button);

        EditText name = (EditText) findViewById(R.id.item_name);
        EditText purchaseDate = (EditText) findViewById(R.id.purchase_date);
        EditText expireDate = (EditText) findViewById(R.id.expire_date);

        purchaseDate.setText(getDateFormat().format(calendar.getTime()));
        calendar.set(Calendar.MONTH, MONTH + 1);
        expireDate.setText(getDateFormat().format(calendar.getTime()));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent();
                cancelIntent.setClass(AddItem.this, MainActivity.class);

                startActivity(cancelIntent);
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                purchaseDate.setText(sdf.format(calendar.getTime()));
            }
        };

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month+1);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                expireDate.setText(sdf.format(calendar.getTime()));
            }
        };


        purchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddItem.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        expireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddItem.this, date2, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

}