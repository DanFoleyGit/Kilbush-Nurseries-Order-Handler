package com.example.kilbushnurseriesorderhandler;

/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description : This class takes user input, passes it to a validation service and then inserts the
 * data into the database.
 *
 */
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;



public class TescoOrdersActivity extends AppCompatActivity {

    private Button submitBtn;
    private EditText addTescoDate;
    private EditText addSixPk30;
    private EditText addSixPk10;
    private EditText addSunstream;
    private EditText addEveryday;
    private TextView output; // for test purposes

    private boolean vDate; // boolean for validating date
    private int julianIntDate = 0;
    private Cursor tescoData;
    private DatabaseManager db;
    private OrdersServices ordersServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesco_orders);

        submitBtn = findViewById(R.id.btn_submit_tesco);

        addTescoDate = findViewById(R.id.add_tesco_date);
        addSixPk30 = findViewById(R.id.add_six_pk_30);
        addSixPk10 = findViewById(R.id.add_six_pk_10);
        addSunstream = findViewById(R.id.add_sunstream);
        addEveryday = findViewById(R.id.add_everyday);

        db = new DatabaseManager(this);
        ordersServices = new OrdersServices();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // validate the date as a String
                vDate = ordersServices.validateDate(addTescoDate.getText().toString());

                // Log cat for testing purposes
                Log.d("The date given to validateDate is", addTescoDate.getText().toString());
                Log.d("vDate is" ,String.valueOf(vDate));

                // check if valid date and that all inputs have a content in them
                if( addTescoDate.length() > 0 && addSixPk30.length() > 0 && addSixPk10.length() > 0 &&
                    addSunstream.length() > 0 && addEveryday.length() > 0 && vDate)
                {

                    // show the user the most recent input
                    String outputText = ("The Following infromtion has been saved: " + addTescoDate.getText().toString()
                            + ", " + addSixPk30.getText().toString()
                            + ", " + addSixPk10.getText().toString()
                            + ", " + addSunstream.getText().toString()
                            + ", " + addEveryday.getText().toString());


                    try {
                        // Convert the validated int to a julian date to be entered into the table
                        julianIntDate = OrdersServices.convertStringToJulianInt(addTescoDate.getText().toString());

                        // check if the date is already in the database
                        db.open();
                        tescoData = db.getOrderByDateTesco(julianIntDate);

                        // If a date is found, update it.
                        if(tescoData.moveToFirst())
                        {
                            Toast.makeText(TescoOrdersActivity.this,"The order for " + addTescoDate.getText().toString() + " has been updated." , Toast.LENGTH_SHORT).show();

                            db.updateTescoTable(julianIntDate, addSixPk30.getText().toString(),
                                    addSixPk10.getText().toString(), addSunstream.getText().toString(), addEveryday.getText().toString());
                        }
                        else
                        {
                            db.insertTesco(julianIntDate,
                                    addTescoDate.getText().toString(),
                                    addSixPk30.getText().toString(),
                                    addSixPk10.getText().toString(),
                                    addSunstream.getText().toString(),
                                    addEveryday.getText().toString());

                            // Toast the users input
                            Toast.makeText(TescoOrdersActivity.this,outputText, Toast.LENGTH_LONG).show();

                        }

                        db.close();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } // Give user feedback
                else if(!vDate)
                {
                    Toast.makeText(TescoOrdersActivity.this,"Wrong Date Format! Use dd/mm/yyyy. " , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(TescoOrdersActivity.this, "Fill in blank data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}