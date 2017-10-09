package com.olya.cookbook.model;

/**
 * Created by Olya on 2017-10-08.
 */

public class Ingredient {

    /**
     * The name of the ingredient
     */
    private String name;
    /**
     * The quantity of the ingredient
     */
    private int quantity;
    /**
     * The unit of measure of the ingredient
     */
    private String unit;

    /**
     * A public constructor for the ingredient that initializes all its attributes
     * @param name
     * @param quantity
     * @param unit
     */
    public Ingredient(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Get the name of the ingredient
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name for the ingredient
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the quantity of the ingredient
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity for the ingredient
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the unit of measure of the ingredient
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set the unit of measure for the ingredient
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
