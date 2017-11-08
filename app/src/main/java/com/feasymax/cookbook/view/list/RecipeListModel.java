package com.feasymax.cookbook.view.list;

import android.graphics.Bitmap;

/**
 * Created by Olya on 2017-11-06.
 */

public class RecipeListModel {
    private int recipeId;
    private String RecipeTitle="";
    private Bitmap RecipeImage=null;
    private String RecipeCaption="";

    /*********** Set Methods ******************/

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setRecipeTitle(String RecipeTitle)
    {
        this.RecipeTitle = RecipeTitle;
    }

    public void setRecipeImage(Bitmap Image)
    {
        this.RecipeImage = Image;
    }

    public void setRecipeCaption(String Url)
    {
        this.RecipeCaption = Url;
    }

    /*********** Get Methods ****************/

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeTitle()
    {
        return this.RecipeTitle;
    }

    public Bitmap getRecipeImage()
    {
        return this.RecipeImage;
    }

    public String getRecipeCaption()
    {
        return this.RecipeCaption;
    }
}
