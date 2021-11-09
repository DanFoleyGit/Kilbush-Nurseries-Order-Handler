package com.example.kilbushnurseriesorderhandler;

/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description : This class creates two tables for storing orders. It creates two sql Strings for
 * creating the two tables that is executed by the onCreate function
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper
{
    // Table For Tesco orders
    public static final String KEY_TESCO_ROWID = "_id";
    public static final String KEY_TESCO_JULIAN_DATE = "julian_tesco_date";
    public static final String KEY_TESCO_DATE = "tesco_date"; // dd/mm/yyyy
    public static final String KEY_SIXPK30= "six_pk_30";
    public static final String KEY_SIXPK10 = "six_pk_10";
    public static final String KEY_SUNSTREAM = "sunstream";
    public static final String KEY_EVERYDAY = "everyday";

    // LIDL table rows
    public static final String KEY_LIDL_ROWID = "_id";
    public static final String KEY_LIDL_JULIAN_DATE = "julian_lidl_date";
    public static final String KEY_LIDL_DATE = "lidl_date"; // dd/mm/yyyy
    public static final String KEY_SUNSTREAMLIDL = "sunstream_lidl";
    public static final String KEY_LARGEVINELIDL = "largevine_lidl";

    // Database and tables
    public static final String DATABASE_ORDERS_NAME = "kilbush_nurseries_orders";
    public static final String TABLE_TESCO = "tesco";
    public static final String TABLE_LIDL = "lidl";
    public static final int DATABASE_VERSION = 5;// can change this delete and rewrite the database

    // String to create tables
    private static final String DATABASE_TESCO_CREATE =
            "create table " + TABLE_TESCO +
            " ( _id integer primary key autoincrement, " +
            " julian_tesco_date int not null, " +
            " tesco_date varchar not null, " +
            " six_pk_30 varchar not null, " +
            " six_pk_10 varchar not null, " +
            " sunstream varchar not null, " +
            " everyday varchar not null);";

    private static final String DATABASE_LIDL_CREATE =
            "create table " + TABLE_LIDL +
            " ( _id integer primary key autoincrement, " +
            " julian_lidl_date int not null, " +
            " lidl_date varchar not null, " +
            " sunstream_lidl varchar not null, " +
            " largevine_lidl varchar not null);";

    private static final String DATABASE_DELETE_TESCO = "DROP TABLE if exists tesco";
    private static final String DATABASE_DELETE_LIDL = "DROP TABLE if exists lidl";
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_ORDERS_NAME, null, DATABASE_VERSION);
        //SQLLiteOpenHelper is the super
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_TESCO_CREATE);
        db.execSQL(DATABASE_LIDL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DATABASE_DELETE_TESCO);
        db.execSQL(DATABASE_DELETE_LIDL);
        onCreate(db);
    }
}
