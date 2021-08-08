package edu.wit.mobileapp.enjoyby;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditItem extends AppCompatActivity {
    Button cancelButton;
    Button updateButton;
    static final int NAME_LIMIT = 12;

    static int ID;
    static String NAME;
    static long PURCHASE_DATE, EXPIRATION_DATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();

        // Extras
        Bundle extras = intent.getExtras();
        ID = extras.getInt("ID");
        NAME = extras.getString("NAME");
        PURCHASE_DATE = extras.getLong("PURCHASE_DATE");
        EXPIRATION_DATE = extras.getLong("EXPIRATION_DATE");

        Log.v("myApp", "Editing: " + ID + ":" + NAME + ":" + PURCHASE_DATE + ":" + EXPIRATION_DATE);

        cancelButton = (Button) findViewById(R.id.edit_cancel_button);
        updateButton = (Button) findViewById(R.id.edit_update_button);

        EditText name = (EditText) findViewById(R.id.edit_name_input);
        EditText purchaseDate = (EditText) findViewById(R.id.edit_purchase_input);
        EditText expireDate = (EditText) findViewById(R.id.edit_expiration_input);

        // Set current values
        name.setText(NAME);
        purchaseDate.setText(getDateFormat().format(PURCHASE_DATE));
        expireDate.setText(getDateFormat().format(EXPIRATION_DATE));


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent();
                cancelIntent.setClass(EditItem.this, MainActivity.class);

                startActivity(cancelIntent);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbHelper = new DatabaseHandler(getApplicationContext());

                String productName = name.getText().toString();
                Date purchased = stringToDate(purchaseDate.getText().toString());
                Date expiresAt = stringToDate(expireDate.getText().toString());


                // handle empty name
                if(productName.matches("")) {
                    Log.v("myApp", "Empty Item Name");
                    Snackbar.make(v, "You wanna give this thing a name?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                // enforce name limit
                if(productName.length() > NAME_LIMIT) {
                    Log.v("myApp", "Name Limit Exceeded");
                    Snackbar.make(v, "Item Name cannot be longer than 12 characters", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                dbHelper.updateFoodItem(ID, productName, purchased, expiresAt);
                Intent intent = new Intent();
                intent.setClass(EditItem.this, MainActivity.class);

                startActivity(intent);
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
                new DatePickerDialog(EditItem.this, purchase, YEAR, MONTH, DAY).show();
            }
        });

        expireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditItem.this, expire, YEAR, MONTH+1, DAY).show();
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
