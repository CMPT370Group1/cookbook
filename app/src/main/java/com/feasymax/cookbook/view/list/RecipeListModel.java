package com.feasymax.cookbook.view.list;

import android.graphics.Bitmap;

/**
 * Created by Olya on 2017-11-06.
 */

public class RecipeListModel {

    private int RecipeId;
    private String RecipeTitle = "";
    private Bitmap RecipeImage = null;
    private int RecipeDuration = 0;

    public RecipeListModel() {}

    public RecipeListModel(int id, String title, Bitmap image, int duration) {
        RecipeId = id;
        RecipeTitle = title;
        RecipeImage = image;
        RecipeDuration = duration;
    }

    /*********** Set Methods ******************/

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

    /*********** Get Methods ****************/

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
