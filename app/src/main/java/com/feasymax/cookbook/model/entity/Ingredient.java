package com.feasymax.cookbook.model.entity;

/**
 * Created by Olya on 2017-10-08.
 * Ingredient for a recipe.
 */

public class Ingredient {

    /**
     * The name of the ingredient
     */
    private String name;
    /**
     * The quantity of the ingredient
     */
    private double quantity;
    /**
     * The unit of measure of the ingredient
     */
    private int unit;

    /**
     * A public constructor for the ingredient that initializes all its attributes
     * @param name
     * @param quantity
     * @param unit
     */
    public Ingredient(String name, double quantity, int unit) {
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
    public double getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity for the ingredient
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the unit of measure of the ingredient
     * @return
     */
    public int getUnit() {
        return unit;
    }

    /**
     * Set the unit of measure for the ingredient
     * @param unit
     */
    public void setUnit(int unit) {
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
