package com.example.kilbushnurseriesorderhandler;

/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description :
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Home Screen Buttons
        Button tescoOrders = (Button) findViewById(R.id.btn_tescoOrders);
        Button tescoCalculator = findViewById(R.id.btn_tescoCalculator);
        Button viewTescoOrders = findViewById(R.id.btn_ViewTescoOrders);
        Button lidlOrders = findViewById(R.id.btn_lidlOrders);
        Button lidlCalculator = findViewById(R.id.btn_lidlCalculator);
        Button viewLidlOrders = findViewById(R.id.btn_ViewLidlOrders);

        tescoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class activityClass = TescoOrdersActivity.class;
                openActivity(activityClass);
            }
        });

       tescoCalculator.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Class activityClass = TescoCalculatorActivity.class;
               openActivity(activityClass);
           }
       });

        viewTescoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class activityClass = ViewTescoOrdersActivity.class;
                openActivity(activityClass);
            }
        });

       lidlOrders.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Class activityClass = LidlOrdersActivity.class;
               openActivity(activityClass);
           }
       });

       lidlCalculator.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Class activityClass = LidlCalculatorActivity.class;
               openActivity(activityClass);
           }
       });

       viewLidlOrders.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Class activityClass = ViewLidlOrdersActivity.class;
               openActivity(activityClass);
           }
       });
    }

    // takes in a class passed to it and opens it
    private void openActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}