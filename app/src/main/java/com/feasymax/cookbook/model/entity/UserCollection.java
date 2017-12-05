package com.feasymax.cookbook.model.entity;

import android.util.Log;
import android.util.SparseArray;

import com.feasymax.cookbook.model.Application;

import java.util.List;

/**
 * Created by Olya on 2017-10-12.
 * Collection of a user's recipes and links
 */

public class UserCollection {

    /**
     * Number of recipe categories
     */
    protected final int NUM_CATEGORIES = 13;

    /**
     * Lists of recipes for each category
     */
    protected List<RecipeShortInfo> recipes;
    /**
     * Current recipe to display
     */
    protected Recipe curRecipe;
    /**
     * Current category to display
     */
    protected int category;
    /**
     * List of saved web-pages
     */
    private List<WebpageInfo> links;
    /**
     * URL of the current web-page
     */
    private WebpageInfo curLink;

    /**
     * Public constructor
     */
    public UserCollection() {}

    /**
     * Get all lists of recipes
     * @return
     */
    public List<RecipeShortInfo> getRecipes() {
        return recipes;
    }

    /**
     * Set the list of recipes
     * @param recipes
     */
    public void setRecipes(List<RecipeShortInfo> recipes) {
        this.recipes = recipes;
    }

    /**
     * Update the list of recipes from the database
     * @param category
     */
    public void updateRecipes(int category) {
        boolean isUserCollection = false;
        int userID = -1;
        if (this.getClass() == UserCollection.class) {
            isUserCollection = true;
            userID = Application.getUser().getUserID();
        }
        this.recipes = Application.getRecipesFromDB(isUserCollection, userID, category);
    }

    /**
     * Get the currently displayed recipe
     * @return
     */
    public Recipe getCurRecipe() {
        return curRecipe;
    }

    /**
     * Set the current recipe to display
     * @param curRecipe
     */
    public void setCurRecipe(Recipe curRecipe) {
        this.curRecipe = curRecipe;
    }

    /**
     * Get the currently displayed category
     * @return
     */
    public int getCategory() {
        return category;
    }

    /**
     * Set the current category to display
     * @param category
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * Add new recipe to the appropriate category list
     * @param recipeInfo
     * @param category
     */
    public void addNewRecipe(RecipeShortInfo recipeInfo, int category) {
        // if the list of recipes has not been obtained from the database yet, obtain it
        if (this.recipes == null) {
            boolean isUserCollection = false;
            int userID = -1;
            if (this.getClass() == UserCollection.class) {
                isUserCollection = true;
                userID = Application.getUser().getUserID();
            }
            this.recipes = Application.getRecipesFromDB(isUserCollection, userID, category);
            Log.println(Log.INFO, "addNewRecipe: recipes", this.recipes.get(category).toString());
        }
        // if the list of recipes exists
        else {
            // if recipe is already in the list, updateUserAccount it
            for (RecipeShortInfo recipe: this.recipes) {
                if (recipe.getRecipeId() == recipeInfo.getRecipeId()) {
                    int index = this.recipes.indexOf(recipe);
                    this.recipes.set(index, recipeInfo);
                    return;
                }
            }
            // otherwise, add it to the list
            this.recipes.add(recipeInfo);
        }
    }

    /**
     * Delete a recipe from its list
     * @param recipeId
     * @param category
     * @return
     */
    public boolean removeRecipe(int recipeId, int category) {
        if (this.recipes != null) {
            for (RecipeShortInfo recipe: this.recipes) {
                if (recipe.getRecipeId() == recipeId) {
                    return this.recipes.remove(recipe);
                }
            }
        }
        return false;
    }

    /**
     * Get the link of the currently displayed web-page
     * @return
     */
    public WebpageInfo getCurLink() {
        return curLink;
    }

    /**
     * Set the list of saved links
     * @param curLink
     */
    public void setCurLink(WebpageInfo curLink) {
        this.curLink = curLink;
    }

    /**
     * Get the list of the links, if it doesn't exist, obtain it from the database
     * @return
     */
    public List<WebpageInfo> getLinks() {
        if (this.links == null) {
            this.links = Application.getLinksFromDB();
        }
        return links;
    }

    /**
     * Update the list of links from the database
     * @return
     */
    public List<WebpageInfo> updateLinks() {
        this.links = null;
        this.links = Application.getLinksFromDB();
        return links;
    }

    /**
     * Set the list of links
     * @param links
     */
    public void setLinks(List<WebpageInfo> links) {
        this.links = links;
    }

    /**
     * Add a new link to the list, if the list doesn't exist, obtain it from the database first
     * @param link
     */
    public void addLink(WebpageInfo link) {
        if (this.links == null) {
            this.links = Application.getLinksFromDB();
        }
        this.links.add(link);
    }

    /**
     * Check if the list of links already contains the specified link
     * @param link
     * @return
     */
    public boolean containsLink(String link) {
        // if the list has not been obtained from the database, obtain it first
        if (this.links == null) {
            getLinks();
        }
        // check all links
        for (WebpageInfo webpageInfo: this.links) {
            if (webpageInfo.getUrl().equals(link)) {
                return true;
            }
        }
        return false;
    }
}
