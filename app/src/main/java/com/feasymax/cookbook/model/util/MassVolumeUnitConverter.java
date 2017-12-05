
package com.feasymax.cookbook.model.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.feasymax.cookbook.model.entity.ConversionFactor;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olya on 2017-10-12.
 */

public class MassVolumeUnitConverter{



    //For representing stored substance conversions factors to the view screen
    public static List<ConversionFactor> makeListFromFile(Context context)throws IOException{
        String baseConversions = "Butter:1.04\n";
        FileInputStream fis;
        try {
            fis = context.openFileInput("conversionFactors.txt");
        } catch (IOException e){
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("conversionFactors.txt",Context.MODE_PRIVATE));
            osw.write(baseConversions);
            osw.close();
            return makeListFromFile(context);
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        fis.close();
        String toParse = sb.toString();
        List<ConversionFactor> toReturn = new LinkedList<>();
        String[] arrayOfConversionFactors = toParse.split(";");
        for (String s:arrayOfConversionFactors) {
            toReturn.add(ConversionFactorFromString(s));
        }
        return toReturn;
    }

    public static void AddConversionFactor(String name, double factor, Context context) throws IOException{
        OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput("conversionFactors.txt",Context.MODE_PRIVATE));
        String toAdd = name + ":"+factor+";\n";
        osw.write(toAdd);
        osw.close();
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
