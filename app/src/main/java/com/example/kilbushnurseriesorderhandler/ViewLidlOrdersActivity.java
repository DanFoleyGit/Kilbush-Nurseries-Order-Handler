package com.example.kilbushnurseriesorderhandler;

/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description : This class takes takes user input from the user by using the calendar widget.
 * It then passes the string date to the to be converted to a julian integer. The integer is then
 * used to query the table in the database and return the relevent row to the user.
 */

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.text.ParseException;

public class ViewLidlOrdersActivity extends ListActivity {

    // Calander view
    private CalendarView myCalenderView;

    // Universal variables for class
    private String TAG;
    private String date;
    private int julianDate;

    // class objects for the class
    private DatabaseManager db; //create database object
    private OrdersServices ordersServices;

    // Variables for displaying Lidl Orders
    private String[] lidlColumns;
    private int [] lidlTextIDsRow;
    private Cursor lidlData;
    private SimpleCursorAdapter lidlAdapter; // testing out list adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_lidl_orders);

        myCalenderView = findViewById(R.id.lidlCalendarView);

        TAG = "ViewOrdersActivity";
        date = "";
        julianDate = 0;

        db = new DatabaseManager(this ); // assign db object the class
        ordersServices = new OrdersServices(); //assign order services object



        lidlColumns = new String[] {"sunstream_lidl", "largevine_lidl"};
        lidlTextIDsRow = new int [] {R.id.rowSunstreamLidl, R.id.rowLargeVineLidl};
        // tescoData assigned after db is opened
        // tescoAdapter assigned in on click method


        myCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month + 1) + "/" + year; // yyyy/mm/dd. months start at 0.
                Log.d(TAG, "onSelectDayChange: dd/MM/yyyy " + date);


                try {
                    julianDate = ordersServices.convertStringToJulianInt(date);

                    db.open();

                    // begin getting and displaying Lidl order

                    lidlData = db.getOrderByDateLidl(julianDate);

                    if (lidlData.moveToFirst()) {
                        System.out.println(date);
                        System.out.println("Cursor: 1 : " + lidlData.moveToFirst());


                        // if movetofirst

                        String sunstreamLidl = lidlData.getString(lidlData.getColumnIndex("sunstream_lidl"));
                        String largevineLidl = lidlData.getString(lidlData.getColumnIndex("largevine_lidl"));


                        String toastTable = (sunstreamLidl + " - " + largevineLidl);
                        Log.d(TAG, toastTable);

                        //Toast.makeText(ViewOrdersActivity.this, toastTable, Toast.LENGTH_SHORT).show();

                        // set the adpater with the content fo data, into the rows using the column names
                        lidlAdapter = new SimpleCursorAdapter(ViewLidlOrdersActivity.this, R.layout.lidl_row, lidlData, lidlColumns, lidlTextIDsRow);
                        setListAdapter(lidlAdapter);

                    } else {
                        Toast.makeText(ViewLidlOrdersActivity.this, "No Orders for this day", Toast.LENGTH_SHORT).show();

                        //update the List to be null if there's  no result
                        lidlAdapter = new SimpleCursorAdapter(ViewLidlOrdersActivity.this, R.layout.lidl_row, null, lidlColumns, lidlTextIDsRow);
                        setListAdapter(lidlAdapter);
                    }

                    db.close();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}