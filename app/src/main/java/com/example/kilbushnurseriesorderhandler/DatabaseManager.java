package com.example.kilbushnurseriesorderhandler;


/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description : This class is performs all the CRUD tasks to do with the database. It imports
 * all the keys from the databaseHelper to perform these tasks. As it deals with two tables it has
 * a insert, delete and update for each table.
 *
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

import java.util.List;

import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.TABLE_TESCO;
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_TESCO_ROWID;
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_TESCO_JULIAN_DATE; // 123456
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_TESCO_DATE; // dd/mm/yyyy
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_SIXPK30;
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_SIXPK10;
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_SUNSTREAM;
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_EVERYDAY;

import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.TABLE_LIDL;
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_LIDL_ROWID;
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_LIDL_JULIAN_DATE; // 123456
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_LIDL_DATE;// dd/mm/yyyy
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_SUNSTREAMLIDL;
import static com.example.kilbushnurseriesorderhandler.DatabaseHelper.KEY_LARGEVINELIDL;

public class DatabaseManager {

    Context context;
    private DatabaseHelper myDatabaseHelper;
    private SQLiteDatabase myDatabase;



    public DatabaseManager(Context context)
    {
        this.context = context;
    }

    public DatabaseManager open() throws SQLException
    {
        myDatabaseHelper = new DatabaseHelper(context);
        myDatabase = myDatabaseHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        myDatabaseHelper.close();
    }

    // This function inserts data into the tesco orders table.
    // It takes strings from which are added to rows
    public long insertTesco(int julianDate, String tescoDate, String sixpk30, String sixpk10, String sunstream, String everyday)
    {
        ContentValues initialValuesTesco = new ContentValues();
        initialValuesTesco.put(KEY_TESCO_JULIAN_DATE, julianDate);
        initialValuesTesco.put(KEY_TESCO_DATE, tescoDate);
        initialValuesTesco.put(KEY_SIXPK30, sixpk30);
        initialValuesTesco.put(KEY_SIXPK10, sixpk10);
        initialValuesTesco.put(KEY_SUNSTREAM, sunstream);
        initialValuesTesco.put(KEY_EVERYDAY, everyday);

        return myDatabase.insert(TABLE_TESCO, null, initialValuesTesco);
    }

    // This function inserts data into the Lidl orders table.
    // It takes strings from which are added to rows.
    public long insertLidl(int julianDate, String lidlDate, String sunstreamLidl, String largevineLidl)
    {
        ContentValues initialValuesLidl = new ContentValues();
        initialValuesLidl.put(KEY_LIDL_JULIAN_DATE, julianDate);
        initialValuesLidl.put(KEY_LIDL_DATE, lidlDate);
        initialValuesLidl.put(KEY_SUNSTREAMLIDL, sunstreamLidl);
        initialValuesLidl.put(KEY_LARGEVINELIDL, largevineLidl);

        return myDatabase.insert(TABLE_LIDL, null, initialValuesLidl);
    }

    // get order by date by taking in a julian integer and comparing it to a corresponding
    // julian int in the table and returns a cursor with the data in it.
    public Cursor getOrderByDateTesco(int date) throws SQLException
    {

        Cursor myCursor = myDatabase.query(true, TABLE_TESCO, new String[]{
                KEY_TESCO_ROWID,
                KEY_TESCO_JULIAN_DATE,
                KEY_TESCO_DATE,
                KEY_SIXPK30,
                KEY_SIXPK10,
                KEY_SUNSTREAM,
                KEY_EVERYDAY
        },
                KEY_TESCO_JULIAN_DATE + "=" + date,
                null,
                null,
                null,
                null,
                null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    // get order by date by taking in a julian integer and comparing it to a corresponding
    // julian int in the table and returns a cursor with the data in it.
    public Cursor getOrderByDateLidl(int date) throws SQLException
    {
        Cursor myCursor = myDatabase.query(true, TABLE_LIDL, new String[]{
                        KEY_LIDL_ROWID,
                        KEY_LIDL_JULIAN_DATE,
                        KEY_LIDL_DATE,
                        KEY_SUNSTREAMLIDL,
                        KEY_LARGEVINELIDL
                },
                KEY_LIDL_JULIAN_DATE + " like " + date,
                null,
                null,
                null,
                null,
                null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    // Searches for the corresponding julian and updates the database with the data passed to it.
    public boolean updateTescoTable(int julianDate, String sixpk30, String sixpk10, String sunstream, String everyday)
    {
        ContentValues args = new ContentValues(20);
        args.put(KEY_TESCO_JULIAN_DATE, julianDate);
        args.put(KEY_SIXPK30, sixpk30);
        args.put(KEY_SIXPK10, sixpk10);
        args.put(KEY_SUNSTREAM, sunstream);
        args.put(KEY_EVERYDAY, everyday);
        return myDatabase.update(TABLE_TESCO, args,
                KEY_TESCO_JULIAN_DATE + "=" + julianDate, null) > 0;
    }

    // Searches for the corresponding julian and updates the database with the data passed to it.

    public boolean updateLidlTable(int julianDate, String sunstreamLidl, String largevineLidl)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_LIDL_JULIAN_DATE, julianDate);
        args.put(KEY_SUNSTREAMLIDL, sunstreamLidl);
        args.put(KEY_LARGEVINELIDL, largevineLidl);

        return myDatabase.update(TABLE_LIDL, args,
                KEY_LIDL_JULIAN_DATE + "=" + julianDate, null) > 0;
    }

    // Delete a row from the table where julian date matches
    public boolean deleteTescoOrder(int julianDate)
    {
        return myDatabase.delete(TABLE_TESCO, KEY_TESCO_JULIAN_DATE +
                "=" + julianDate, null) > 0;
    }

    // Delete a row from the table where julian date matches
    public boolean deleteLidlOrder(int julianDate)
    {
        return myDatabase.delete(TABLE_LIDL, KEY_LIDL_JULIAN_DATE +
                "=" + julianDate, null) > 0;
    }
}
