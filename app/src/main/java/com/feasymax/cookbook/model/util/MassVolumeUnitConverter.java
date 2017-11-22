
package com.feasymax.cookbook.model.util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.IOException;
import com.feasymax.cookbook.model.entity.ConversionFactor;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olya on 2017-10-12.
 */

public class MassVolumeUnitConverter {


    //For representing stored substance conversions factors to the view screen
    public static List<ConversionFactor> makeListFromFile(String filename, Context context){
        String toParse = "";
        try{
            FileInputStream fis = context.openFileInput(filename);
            int c;
            while( (c = fis.read()) != -1){
                toParse.concat(Character.toString((char) c));
            }

        }
        catch(IOException e){
            throw new RuntimeException("No file found for makeListFromFile in MassVolumeConverter");
        }
        List<ConversionFactor> toReturn = new LinkedList<>();
        String[] arrayOfConversionFactors = toParse.split(";");
        for (String s:arrayOfConversionFactors) {
            toReturn.add(ConversionFactorFromString(s));
        }
        return toReturn;
    }

    private static ConversionFactor ConversionFactorFromString(String s){
        String[] splitString = s.split(":");
        return new ConversionFactor(splitString[0],Double.parseDouble(splitString[1]));
    }
    //the actual conversion tool
    public static double MassToVolume(double quanA, int unitA, int unitB, double substance, String unitAType){
        if(unitAType.equals("Mass")) {
            double massToGrams = UnitConverters.MassToMass(quanA, unitA, 1);
            double volToMilliliters = massToGrams * substance;
            return UnitConverters.VolumeToVolume(volToMilliliters, 5, unitB);
        } else if(unitAType.equals("Volume")) {
            double volToMilliliters = UnitConverters.VolumeToVolume(quanA, unitA, 5);
            double massToGrams = volToMilliliters/substance;
            return UnitConverters.MassToMass(massToGrams,1,unitB);
        }
        else throw new RuntimeException("ERROR: Mass to volume converter must be given a unitAType of either Mass or Volume");
    }
}
