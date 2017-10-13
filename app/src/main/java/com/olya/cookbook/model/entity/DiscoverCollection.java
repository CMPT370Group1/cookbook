package com.olya.cookbook.model.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olya on 2017-10-12.
 */

public class DiscoverCollection {

    private List<RecipeShortInfo> recipes;
    private Recipe curRecipe;

    public DiscoverCollection() {
        recipes = new LinkedList<>();
    }

    public List<RecipeShortInfo> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeShortInfo> recipes) {
        this.recipes = recipes;
    }

    public Recipe getCurRecipe() {
        return curRecipe;
    }

    public void setCurRecipe(Recipe curRecipe) {
        this.curRecipe = curRecipe;
    }
}
