package com.feasymax.cookbook.model.entity;

import android.graphics.Bitmap;

/**
 * Created by Olya on 2017-11-06.
 * Short recipe information.
 */

public class RecipeShortInfo {

    /**
     * Recipe id in the database
     */
    private int RecipeId;
    /**
     * The recipe's title
     */
    private String RecipeTitle = "";
    /**
     * The recipe's image
     */
    private Bitmap RecipeImage = null;
    /**
     * The recipe's duration in minutes
     */
    private int RecipeDuration = 0;

    /**
     * Public empty constructor
     */
    public RecipeShortInfo() {}

    /**
     * Public constructor with all attributes
     * @param id
     * @param title
     * @param image
     * @param duration
     */
    public RecipeShortInfo(int id, String title, Bitmap image, int duration) {
        RecipeId = id;
        RecipeTitle = title;
        RecipeImage = image;
        RecipeDuration = duration;
    }

    // Set Methods

    public void setRecipeId(int recipeId) {
        this.RecipeId = recipeId;
    }

    public void setRecipeTitle(String RecipeTitle)
    {
        this.RecipeTitle = RecipeTitle;
    }

    public void setRecipeImage(Bitmap Image)
    {
        this.RecipeImage = Image;
    }

    public void setRecipeDuration(int duration)
    {
        this.RecipeDuration = duration;
    }

    // Get Methods

    public int getRecipeId() {
        return RecipeId;
    }

    public String getRecipeTitle()
    {
        return this.RecipeTitle;
    }

    public Bitmap getRecipeImage()
    {
        return this.RecipeImage;
    }

    public int getRecipeDuration()
    {
        return this.RecipeDuration;
    }

    public String toString() {
        return "Recipe{" + RecipeId +
                ", title = '" + RecipeTitle + '\'' +
                ", duration = " + RecipeDuration +
                ", image = " + RecipeImage +
                '}';
    }

}
