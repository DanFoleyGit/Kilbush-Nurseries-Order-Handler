package com.example.kilbushnurseriesorderhandler;

/**
 * Author: Daniel Foley
 * Date: 20/11/20
 *
 * Description : This class is used to perform calculations and updates to LidlCalculatorActivity
 * and TescoCalculatorActivity.
 *
 * The calculator function will take the quantity and get the modulus of it to determine the amount
 * of pallets. It will then divide it to find out how many boxes will go on the last pallet.
 *
 * It also checks if the for a standard tesco box of 45 per pallet, if there is < 6 boxes
 * on the last pallet in will put them on top of one of the 45 pallets to save on shipping
 */
import android.widget.TextView;

public class CalculatorServices {

    void performCalculation(TextView quantity, TextView bulk, TextView remainder, int box)
    {
        int displayBulk = 0;
        int displayRemainder = 0;
        int input = convertToInt(quantity);//quantity;

        displayBulk = input / box;
        displayRemainder = input % box;

        // if there is 5 or less boxes by themselves on a standard pallet
        // they should be added on top of a 45 high pallet
        if (box == 45 && displayRemainder < 6 )
        {
            displayRemainder = box + displayRemainder;

            displayBulk--;
        }

        if(input != 0)
        {
            bulk.setText("(" + box + " boxes by " + Integer.toString(displayBulk) + ")");
            remainder.setText("(" + Integer.toString(displayRemainder) + " boxes by 1)");
        }
        else if (displayRemainder == 1)
        {
            remainder.setText("");
        }
        else{
            bulk.setText("");
            remainder.setText("");
        }
    }

    private int convertToInt(TextView inputQuantity)
    {
        String value = inputQuantity.getText().toString();
        int finalValue = Integer.parseInt(value);
        return finalValue;
    }

}
