package com.example.kilbushnurseriesorderhandler;

/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description : This class contains methods to convert a data as a String into
 * a Juilian date as an integer. It also contains a function to validate dates
 * passed by the order classes.
 */
import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrdersServices {

     // This program takes in a String as a date in the form of dd/MM/yyyy.
     // It then converts that date to a java Date object, then to a julian date
     // string and finally to a julian date int.
     //
     // This allows all dates to be converted into a julian date so that it can be
     // compared as an integer int he data base

    public static int convertStringToJulianInt(String stringDate) throws ParseException {
        int julianInt = 0;
        String julianString = "";

        Date date = JulianMethods.parseDate(stringDate);

        julianString = JulianMethods.getJulianDate(date);

        julianInt = JulianMethods.parseInteger(julianString);

        Log.d("convertStringToJulianInt date recieved: ", stringDate);
        Log.d("convertStringToJulianInt julian date: ", String.valueOf(julianInt));
        return julianInt;
    }



    // This class holds all the functions required to convert a String date to a Julian Integer.
    public static class JulianMethods
    {
         // The function getJulianDate has been referenced from
         // https://gist.github.com/trplll/552d5b66eb71518c4c8004358caa35af .
         // * It converts a date object into a Julian number as a String.
         // *
         // * JavaDateToJdeDate: translate from a Java Date to the JDEdwards Julian
         // * date format.
         // *
         // * {talendTypes} int | Integer {Category} User Defined {param}
         // * Date("1/10/2008") input: The Java Date to be converted. {example}
         // * JavaDateToJdeDate(1/10/2008) # 108010.
        public static String getJulianDate(Date date) {
            StringBuilder sb = new StringBuilder();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return sb.append("1").append(Integer.toString(cal.get(Calendar.YEAR)).substring(2, 4))
                    .append(String.format("%03d", cal.get(Calendar.DAY_OF_YEAR))).toString();
        } //end getJulianDate
        // end reference

         // This function takes a String date and parses it into
         // a java date object
        public static Date parseDate(String stringDate) throws ParseException {

            Date date= new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);

            return date;
        } // end parseDate

        // This function takes in a String date and converts it to an integer
        public static int parseInteger(String date)
        {
            int integer = Integer.parseInt(date);
            return integer;
        } // end parseInteger
    } // end Methods

    // This function validates a string and checks that it is a valid date. It checks that the
    // string has the correct amount of characters and then checks that it is a valid date. It
    // returns true or false if it has passed the validation or not.
    // public static boolean validateDate(String testDate)
    public static boolean validateDate(String testDate)
    {
        // use Regex to test if the string has the correct amount of digits
        if (testDate.matches("\\d{2}/\\d{2}/\\d{4}"))
        {
            // parse the date into dd/MM/yyyy.
            // This is necessary as the user entered string date 01/01/2020 will
            // be changed to 1/1/2020 and should be properly formatted before it
            // can be changed into a julian date for querying the data base.
            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
            df1.setLenient(false);
            try {
                df1.parse(testDate);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        else // else if the String doesn't match format, return false
        {
            return false;
        }
    }
}
