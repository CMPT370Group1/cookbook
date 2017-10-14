package com.olya.cookbook.model.entity;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 */

public class Recipe {

    /**
     * The recipe's title
     */
    private String title;
    /**
     * The recipe's category
     */
    private int category;
    /**
     * The recipe's ingredients
     */
    private List<Ingredient> ingredients;
    /**
     * The recipe's directions
     */
    private String directions;
    /**
     * The recipe's duration in minutes
     */
    private int duration;
    /**
     * The recipe's tags
     */
    private List<String> tags;
    /**
     * The recipe's image
     */
    private Bitmap image;

    /**
     * A public constructor that initializes all recipe's attributes
     * @param title
     * @param category
     * @param ingredients
     * @param directions
     * @param duration
     * @param tags
     */
    public Recipe(String title, int category, List<Ingredient> ingredients, String directions, int duration, List<String> tags, Bitmap image) {
        this.title = title;
        this.category = category;
        this.ingredients = ingredients;
        this.directions = directions;
        this.duration = duration;
        this.tags = tags;
        this.image = image;
    }

    /**
     * A public empty constructor
     */
    public Recipe() {}

    /**
     * Get the recipe's title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the recipe's title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the recipe's category
     * @return
     */
    public int getCategory() {
        return category;
    }

    /**
     * Set the recipe's category
     * @param category
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * Get the recipe's ingredient list
     * @return
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Add new ingredient to the recipe's ingredient list
     * @param ingredient
     */
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    /**
     * Get the recipe's directions
     * @return
     */
    public String getDirections() {
        return directions;
    }

    /**
     * Set the recipe's directions
     * @param directions
     */
    public void setDirections(String directions) {
        this.directions = directions;
    }

    /**
     * Get the recipe's duration
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set the recipe's duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Get the recipe's tag list
     * @return
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Add new tag to the recipe's tag list
     * @param tag
     */
    public void addTag(String tag) {
        this.tags.add(tag);
    }

    /**
     * Get recipe's image
     * @return image
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * Set recipe's image
     * @param image
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", category=" + category +
                ", ingredients=" + ingredients +
                ", directions='" + directions + '\'' +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}
