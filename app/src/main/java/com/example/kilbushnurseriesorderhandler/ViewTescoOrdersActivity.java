package com.example.kilbushnurseriesorderhandler;

/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description : This class takes takes user input from the user by using the calendar widget.
 * It then passes the string date to the to be converted to a julian integer. The integer is then
 * used to query the table in the database and return the relevent row to the user.
 */

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class ViewTescoOrdersActivity extends ListActivity {

    // Calander view
    private CalendarView myCalenderView;

    // Universal variables for class
    private String TAG;
    private String date;
    private int julianDate;

    // class objects for the class
    private DatabaseManager db; //create database object
    private OrdersServices ordersServices;

    // Variables for displaying Tesco Order
    private String[] tescoColumns;
    private int [] tescoTextIDsRow;
    private Cursor tescoData;
    private SimpleCursorAdapter tescoAdapter; // testing out list adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tesco_orders);

        myCalenderView = findViewById(R.id.tescoCalendarView);

        TAG = "ViewOrdersActivity";
        date = "";
        julianDate = 0;

        db = new DatabaseManager(this ); // assign db object the class
        ordersServices = new OrdersServices(); //assign order services object

        tescoColumns = new String[] {"six_pk_30", "six_pk_10", "sunstream", "everyday"};
        tescoTextIDsRow = new int [] {R.id.rowSixPk30, R.id.rowSixPk10, R.id.rowSunstream, R.id.rowEveryday};
        // tescoData assigned after db is opened
        // tescoAdapter assigned in on click method


        // calender listener
        myCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                date = dayOfMonth + "/" + (month + 1) + "/" + year; // yyyy/mm/dd. months start at 0.
                Log.d(TAG,"onSelectDayChange: dd/MM/yyyy " + date);



                try {
                    julianDate = ordersServices.convertStringToJulianInt(date);

                    db.open();

                    // begin getting and siplaying tesco order

                    tescoData = db.getOrderByDateTesco(julianDate);

                    if (tescoData.moveToFirst())
                    {
                        System.out.println(date);
                        System.out.println("Cursor: 1 : " + tescoData.moveToFirst());


                        // if movetofirst

                        String sixPk30 = tescoData.getString(tescoData.getColumnIndex("six_pk_30"));
                        String sixPk10 = tescoData.getString(tescoData.getColumnIndex("six_pk_10"));
                        String sunstream = tescoData.getString(tescoData.getColumnIndex("sunstream"));
                        String everyday = tescoData.getString(tescoData.getColumnIndex("everyday"));


                        String toastTable = (sixPk30 + " - " + sixPk10 + " - " + sunstream + " - " + everyday);
                        Log.d(TAG, toastTable);

                        //Toast.makeText(ViewOrdersActivity.this, toastTable, Toast.LENGTH_SHORT).show();

                        // set the adpater with the content fo data, into the rows using the column names
                        tescoAdapter = new SimpleCursorAdapter( ViewTescoOrdersActivity.this, R.layout.tesco_row, tescoData, tescoColumns, tescoTextIDsRow);
                        setListAdapter(tescoAdapter);

                    }
                    else
                    {
                        Toast.makeText(ViewTescoOrdersActivity.this, "No Orders for this day", Toast.LENGTH_SHORT).show();

                        //update the List to be null if there's  no result
                        tescoAdapter = new SimpleCursorAdapter( ViewTescoOrdersActivity.this, R.layout.tesco_row, null, tescoColumns,tescoTextIDsRow);
                        setListAdapter(tescoAdapter);
                    }

                    db.close();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}

