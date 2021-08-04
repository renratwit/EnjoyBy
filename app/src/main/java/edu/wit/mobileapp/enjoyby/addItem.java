package edu.wit.mobileapp.enjoyby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class addItem extends AppCompatActivity {

    Button cancelButton;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        cancelButton = (Button)findViewById(R.id.cancel_item_button);
        addButton = (Button)findViewById(R.id.add_item_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent();
                cancelIntent.setClass(addItem.this, MainActivity.class);

                startActivity(cancelIntent);
            }
        });

    }
}