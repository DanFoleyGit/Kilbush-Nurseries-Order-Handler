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
import android.widget.Toast;



public class TescoCalculatorActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesco_calculator);

        Button calculateTesco = findViewById(R.id.btn_tescoCalculatorCalculate);


        calculateTesco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatorTesco();
            }
        });
    }

    // This function takes the text view id's and the edit text id's. Then takes the input from the
    // user, validates it and passes the the textViews, quantity and the box type to the calculator
    // class in calculator services.
    private void calculatorTesco()
    {

        //Read in data including EditText as TextView so it can be converted to a to an int
        TextView sixPk30Quantity = findViewById(R.id.sixPk_quantity);
        TextView sixPk30Bulk = findViewById(R.id.sixPk_bulk);
        TextView sixPk30Remainder = findViewById(R.id.sixPk_remainder);

        TextView sixPk10Quantity = findViewById(R.id.sixPk10_quantity);
        TextView sixPk10Bulk = findViewById(R.id.sixPk10_bulk);
        TextView sixPk10Remainder = findViewById(R.id.sixPk10_remainder);

        TextView sunstreamQuantity = findViewById(R.id.sunstream_quantity);
        TextView sunstreamBulk = findViewById(R.id.sunstream_bulk);
        TextView sunstreamRemainder = findViewById(R.id.sunstream_remainder);

        TextView everydayQuantity = findViewById(R.id.everyday_quantity);
        TextView everydayBulk = findViewById(R.id.everyday_bulk);
        TextView everydayRemainder = findViewById(R.id.everyday_remainder);

        int stdBoxPref = 45;
        int shallowBoxPref = 70;

        CalculatorServices calculatorServices = new CalculatorServices();

        if ( sixPk10Quantity.length()>0 && sixPk30Quantity.length()>0
                && sunstreamQuantity.length()>0 && everydayQuantity.length()>0 )
        {

            calculatorServices.performCalculation(sixPk30Quantity,sixPk30Bulk,sixPk30Remainder,stdBoxPref);
            calculatorServices.performCalculation(sixPk10Quantity,sixPk10Bulk,sixPk10Remainder,shallowBoxPref);
            calculatorServices.performCalculation(sunstreamQuantity,sunstreamBulk, sunstreamRemainder, shallowBoxPref);
            calculatorServices.performCalculation(everydayQuantity, everydayBulk, everydayRemainder, stdBoxPref);

        }
        else {
            Toast toast = Toast.makeText(this, "Fill in blank data", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}