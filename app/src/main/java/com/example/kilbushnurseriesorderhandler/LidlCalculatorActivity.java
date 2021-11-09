package com.example.kilbushnurseriesorderhandler;

/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description : This class takes user input to give the user the recommended and correct
 * calculation as how many pallets are required and how many boxes go on each pallet.
 *
 */
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LidlCalculatorActivity extends AppCompatActivity {

    // The oncreate waits for the user to click the button.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lidl_calculator);

        Button calculateLidl = findViewById(R.id.btn_LidlCalculatorCalculate);

        calculateLidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatorLidl();
            }
        });
    }

    // This function takes the text view id's and the edit text id's. Then takes the input from the
    // user, validates it and passes the the textViews, quantity and the box type to the calculator
    // class in calculator services.
    private void calculatorLidl() {

        TextView sunstreamLidlQuantity = findViewById(R.id.sunstreamLidl_quantity);
        TextView sunstreamLidlBulk = findViewById(R.id.sunstreamLidl_bulk);
        TextView sunstreamLidlRemainder = findViewById(R.id.sunstreamLidl_remainder);

        TextView largeVineQuantity = findViewById(R.id.largeVineLidl_quantity);
        TextView largeVineBulk = findViewById(R.id.largeVineLidl_bulk);
        TextView largeVineRemainder = findViewById(R.id.largeVineLidl_remainder);

        int stdCardboardBox = 32;
        int smallCardboardBox = 72;

        CalculatorServices calculatorServices = new CalculatorServices();

        if ( sunstreamLidlQuantity.getText().toString().length() >0 &&
                largeVineQuantity.getText().toString().length() >0 ) {

            calculatorServices.performCalculation(sunstreamLidlQuantity, sunstreamLidlBulk,
                    sunstreamLidlRemainder, smallCardboardBox);
            calculatorServices.performCalculation(largeVineQuantity, largeVineBulk,
                    largeVineRemainder, stdCardboardBox);
        }
    }
}

