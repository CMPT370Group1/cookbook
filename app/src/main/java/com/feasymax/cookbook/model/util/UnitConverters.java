package com.feasymax.cookbook.model.util;

import android.util.Log;

/**
 * Created by Chase on 2017-10-12.
 * Basic unit conversion functions (mass, volume and temperature)
 */

public class UnitConverters {

    /*Order is mg, g, kg, oz, lb
            mg
            g
            kg
            oz
            lb
     */
    private static double[][] massTable = {
            {1,0.001,0.000001,0.000035274,0.0000022046},
            {1000,1,0.001,0.0352739907,0.0022046244},
            {1000000,1000,1,35.273990723,2.2046244202},
            {28349.5,28.3495,0.0283495,1,0.0625},
            {453592,453.592,0.0453592,16,1}
    };
    /*Order is tsp, tbsp, cup, qt, l, ml, fl. oz, pt, gal
            tsp
            tbsp
            cup
            qt
            l
            ml
            fl.oz
            pt
            gal
     */
    private static double[][] volumeTable ={
            {1,0.3333,0.020537,0.005208,0.004929,4.92892,0.166667,0.010417,0.0013202},
            {3,1,0.0616115,0.015625,0.0147868,14.7868,0.5,0.3125,0.00390625},
            {48.692,16.231,1,0.2536,0.24,240,8.1154,0.5072,0.0624},
            {192,64,3.942,1,0.9464,946.35,32,2,0.25},
            {202.884,67.628,4.1667,1.05669,1,1000,33.814,2.1338,0.26417},
            {0.202884,0.067628,0.041667,0.001057,0.001,1,0.033814,0.002113,0.00026417},
            {6,2,0.12322,0.03125,0.02957,29.5735,1,0.0625,0.0078125},
            {115.291,38.4304,2.36776,0.600475,0.568261,568.261,19.2152,1,0.150119},
            {768,256,16,4,3.78541,3785410,128,8,1}
    };

    public UnitConverters() {
    }

    public static double MassToMass(double quanA, int unitA, int unitB){
        //Step 1: Convert unit ints to floating point value for conversion
        double conversionFactor = massTable[unitA][unitB];
        //Step 2: Multiply quanA with conversion factor to find quantity of B
        double quanB = quanA * conversionFactor;
        //Step 3: Return quanB
        return quanB;
    }

    public static double VolumeToVolume(double quanA, int unitA, int unitB){
        //Step 1: Convert unit ints to floating point value for conversion
        double conversionFactor = volumeTable[unitA][unitB];
        //Step 2: Multiply quanA with conversion factor to find quantity of B
        double quanB = quanA * conversionFactor;
        //Step 3: Return quanB
        return quanB;
    }

    public static double TempToTemp(double quanA, int unitA){
        double quanB;
        switch(unitA){
            case 0: //Celsius to Farenhei
                quanB = quanA * (9.0/5.0) + 32;
                break;
            case 1: //Farenheit to Celsius
                quanB = (quanA - 32) * (5.0/9.0);
                break;
            default:
                throw new RuntimeException("ERROR: UnitConverters - Temperature. Unit integer must be either 0 or 1");
        }
        Log.println(Log.INFO, "TempToTemp", quanA + " " + quanB + " " + unitA);
        return quanB;
    }

    public static double getRatio(double quanA, double quanB)  {
        return quanA / quanB;
    }

    public static double ParseFraction(String s){
        double result;
        if(s.contains("/")){
            String[] nums = s.split("/");
            result = Double.valueOf(nums[0]) / Double.valueOf(nums[1]);
        } else {
            result = Double.valueOf(s);
        }
        return result;
    }
}
