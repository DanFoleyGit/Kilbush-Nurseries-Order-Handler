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
import java.text.SimpleDateFormat;

public class LidlOrdersActivity extends AppCompatActivity
{

    private Button submitBtn;
    private EditText addLidlDate;
    private EditText addSunstreamLidl;
    private EditText addLargevineLidl;
    private TextView output; // for test purposes

    private boolean vDate; // boolean for validating the date
    private int julianIntDate = 0;
    private Cursor lidlData;
    private DatabaseManager db;
    private OrdersServices ordersServices;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lidl_orders);

        submitBtn = findViewById(R.id.btn_submit_lidl);
        addLidlDate = findViewById(R.id.add_Lidl_date);
        addSunstreamLidl = findViewById(R.id.add_sunstreamLidl);
        addLargevineLidl = findViewById(R.id.add_largevineLidl);

        db = new DatabaseManager(this);
        ordersServices = new OrdersServices();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Validate date
                vDate = ordersServices.validateDate(addLidlDate.getText().toString());

                // Log cat for testing purposes
                Log.d("The date given to validateDate is", addLidlDate.getText().toString());
                Log.d("vDate is" ,String.valueOf(vDate));

                //check the input s valid and has data entered
                if (addLidlDate.length() > 0 && addSunstreamLidl.length() > 0 &&
                    addLargevineLidl.length() > 0 && vDate)
                {

                    // set output for textview
                    String outputText = ("The Following infromtion has been saved: " + addLidlDate.getText().toString()
                            + ", " + addSunstreamLidl.getText().toString()
                            + ", " + addLargevineLidl.getText().toString());


                    // perform inserts here
                    try
                    {
                        //convert the date to a julian integer
                        julianIntDate = OrdersServices.convertStringToJulianInt(addLidlDate.getText().toString());

                        db.open();
                        lidlData = db.getOrderByDateLidl(julianIntDate);

                        // Check if it already exists, if it does update it
                        if(lidlData.moveToFirst())
                        {

                            Toast.makeText(LidlOrdersActivity.this,"The order for " + addLidlDate.getText().toString() + " has been updated." , Toast.LENGTH_SHORT).show();
                            // Call
                            db.updateLidlTable(julianIntDate, addSunstreamLidl.getText().toString(), addLargevineLidl.getText().toString());
                        }
                        else
                        {
                            // insert the data and toast the inputs added
                            db.insertLidl(julianIntDate,
                                    addLidlDate.getText().toString(),
                                    addSunstreamLidl.getText().toString(),
                                    addLargevineLidl.getText().toString());

                            // Toast to give user feedback
                            Toast.makeText(LidlOrdersActivity.this,outputText , Toast.LENGTH_LONG).show();

                        }

                        db.close();

                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                }
                else if(!vDate) // perform feedback to the user
                {
                    Toast.makeText(LidlOrdersActivity.this,"Wrong Date Format! Use dd/mm/yyyy. " , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LidlOrdersActivity.this, "Fill in blank data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}