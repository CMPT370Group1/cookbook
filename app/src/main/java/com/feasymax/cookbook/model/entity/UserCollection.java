package com.feasymax.cookbook.model.entity;

import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olya on 2017-10-12.
 */

public class UserCollection {

    private List<RecipeListModel> recipes;
    private Recipe curRecipe;
    private int category;

    public UserCollection() {
        recipes = new LinkedList<>();
    }

    public List<RecipeListModel> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeListModel> recipes) {
        this.recipes = recipes;
    }

    public Recipe getCurRecipe() {
        return curRecipe;
    }

    public void setCurRecipe(Recipe curRecipe) {
        this.curRecipe = curRecipe;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void addNewRecipe(RecipeListModel recipeInfo) {
        this.recipes.add(recipeInfo);
    }

    public boolean removeRecipe(RecipeListModel recipeInfo) {
        return this.recipes.remove(recipeInfo);
    }

}
