package com.feasymax.cookbook.model.util;

/**
 * Created by Olya on 2017-10-12.
 */

public class MassVolumeUnitConverter {

    // use mass and volume converters
    public MassVolumeUnitConverter() {
    }

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
