package com.feasymax.cookbook.model.entity;

import android.util.Log;
import android.util.SparseArray;

import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Olya on 2017-10-12.
 * Collection of a user's recipes
 */

public class UserCollection {

    protected final int NUM_CATEGORIES = 13;

    protected SparseArray<List<RecipeListModel>> recipes;
    protected Recipe curRecipe;
    protected int category;
    private List<WebpageInfo> links;
    private WebpageInfo curLink;

    public UserCollection() {
        recipes = new SparseArray<>(NUM_CATEGORIES);
    }

    public SparseArray<List<RecipeListModel>> getRecipes() {
        return recipes;
    }

    public List<RecipeListModel> getCategoryRecipes(int category) {
        return this.recipes.get(category);
    }

    public void setRecipes(List<RecipeListModel> recipes, int category) {
        if (this.recipes.get(category) != null) {
            this.recipes.delete(category);
        }
        this.recipes.put(category, recipes);
    }

    public void updateRecipes(int category) {
        Log.d("category "+category+" before", String.valueOf(this.recipes.get(category) == null));
        if (this.recipes.get(category) != null) {
            this.recipes.delete(category);
        }
        Log.d("category "+category+" deleted", String.valueOf(this.recipes.get(category) == null));
        boolean isUserCollection = false;
        int userID = -1;
        if (this.getClass() == UserCollection.class) {
            isUserCollection = true;
            userID = Application.getUser().getUserID();
        }
        this.recipes.put(category, Application.getRecipesFromDB(isUserCollection, userID, category));
        Log.d("category "+category+" after", this.recipes.get(category).toString());
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

    public void addNewRecipe(RecipeListModel recipeInfo, int category) {
        if (this.recipes.get(category) == null) {
            boolean isUserCollection = false;
            int userID = -1;
            if (this.getClass() == UserCollection.class) {
                isUserCollection = true;
                userID = Application.getUser().getUserID();
            }
            this.recipes.put(category, Application.getRecipesFromDB(isUserCollection, userID, category));
            Log.println(Log.INFO, "addNewRecipe: recipes", this.recipes.get(category).toString());
        }
        else {
            for (RecipeListModel recipe: this.recipes.get(category)) {
                if (recipe.getRecipeId() == recipeInfo.getRecipeId()) {
                    int index = this.recipes.get(category).indexOf(recipe);
                    this.recipes.get(category).set(index, recipeInfo);
                    return;
                }
            }
        }
    }

    public boolean removeRecipe(int recipeId, int category) {
        for (RecipeListModel recipe: this.recipes.get(category)) {
            if (recipe.getRecipeId() == recipeId) {
                return this.recipes.get(category).remove(recipe);
            }
        }
        return false;
    }

    public WebpageInfo getCurLink() {
        return curLink;
    }

    public void setCurLink(WebpageInfo curLink) {
        this.curLink = curLink;
    }

    public List<WebpageInfo> getLinks() {
        if (this.links == null) {
            this.links = Application.getLinksFromDB();
        }
        return links;
    }

    public List<WebpageInfo> updateLinks() {
        this.links = null;
        this.links = Application.getLinksFromDB();
        return links;
    }

    public void setLinks(List<WebpageInfo> links) {
        this.links = links;
    }

    public void addLink(WebpageInfo link) {
        if (this.links == null) {
            this.links = Application.getLinksFromDB();
        }
        this.links.add(link);
    }

    public boolean containsLink(String link) {
        for (WebpageInfo webpageInfo: this.links) {
            if (webpageInfo.getUrl().equals(link)) {
                return true;
            }
        }
        return false;
    }
}
