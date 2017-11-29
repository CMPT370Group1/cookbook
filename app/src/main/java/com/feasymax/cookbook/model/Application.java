package com.feasymax.cookbook.model;

import android.graphics.Bitmap;
import android.util.Log;

import com.feasymax.cookbook.model.entity.DiscoverCollection;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.entity.UserAccount;
import com.feasymax.cookbook.model.entity.UserCollection;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.model.util.UserDao;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 * A controller class that manages most of application functionality between classes.
 */

public class Application {

    /**
     * The user of the application, a single user can exist in the application
     */
    private static UserAccount user = null;
    /**
     * User's collection of recipes, a single one can exist in the application
     */
    private static UserCollection userCollection = null;
    /**
     * Other users' collection of recipes, a single one can exist in the application
     */
    private static DiscoverCollection discoverCollection;

    /**
     * Private constructor, because there could not be an Application object
     */
    private Application() {}

    public static UserAccount getUser() {
        return user;
    }

    /**
     * Sign in an existing user
     * @param username the username of the existing user
     * @param password the password of the existing user
     * @return true on success, false on failure to sign in
     */
    public static boolean userSignIn(String username, String password){
        // verify that a user with username and password exists
        // get the userID and recipes from database
        UserDao userDao = new UserDao();
        int userID = userDao.getUserID(username, password);
        String email = userDao.getEmail(userID);
        if (userID != 0) {
            user = new UserAccount(userID, username, email);
            userCollection = new UserCollection();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Register a new user with given username, password, email and first and last names
     * @param username the username of the new user
     * @param password the password of the new user
     * @param email the email address of the new user
     * @param firstName the first name of the new user
     * @param lastName the last name of the new user
     * @return true on success, false on failure to register
     */
    public static boolean userRegister(String username, String password, String email,
                                       String firstName, String lastName){
        UserDao userDao = new UserDao();
        int userID = userDao.regUser(username, password, email, firstName, lastName);
        if (userID != 0) {
            user = new UserAccount(userID, username, email);
            userCollection = new UserCollection();
            return true;
        } else {
            return false;
        }
    }

    /**
     * When the user signs out, set the user object to null
     */
    public static void userSignOut(){
        user = null;
        userCollection = null;
    }

    /**
     * Edit user account info
     * @param username
     * @param userEmail
     * @param oldPassword
     * @param newPassword
     */
    public static String userEditAccount(String username, String userEmail, String oldPassword,
                                         String newPassword){
        UserDao userDao = new UserDao();
        int userID = getUser().getUserID();
        String res = userDao.update(userID, username, userEmail, oldPassword, newPassword);
        if (res.contains("USERNAME")) {
            user.setUsername(username);
            user.setEmail(userEmail);
            // commented the following out because we don't need to create a new user
            //user = new UserAccount(userID, username, userEmail);
        } else {
            user.setEmail(userEmail);
            // commented the following out because we don't need to create a new user
            //user = new UserAccount(userID, getUser().getUsername(), userEmail);
        }
        return res;
    }

    /**
     * Checks if the user is signed in
     * @return true is user is signed in, false otherwise
     */
    public static boolean isUserSignedIn() {
        return (user != null);
    }

    public static UserCollection getUserCollection() {
        return userCollection;
    }

    /**
     *
     * @param recipe
     */
    public static void setUserCurrentRecipe(Recipe recipe) {
        userCollection.setCurRecipe(recipe);
    }

    /**
     * Get the current recipe in user's collection
     * @return
     */
    public static Recipe getUserCurrentRecipe() {
        return userCollection.getCurRecipe();
    }

    public static DiscoverCollection getDiscoverCollection() {
        return discoverCollection;
    }

    public static void setDiscoverCollection(DiscoverCollection discCollection){
        discoverCollection = discCollection;
    }
    /**
     * Set the current recipe in Discover collection
     * @param recipe
     */
    public static void setDiscoverCurrentRecipe(Recipe recipe) {
        Log.println(Log.INFO, "setCurrentRecipe", recipe.toString());
        discoverCollection.setCurRecipe(recipe);
    }

    /**
     * Get the current recipe in Discover collection
     * @return
     */
    public static Recipe getDiscoverCurrentRecipe() {
        return discoverCollection.getCurRecipe();
    }

    /**
     * Get recipes in a category from database
     * @return
     */
    public static List<RecipeListModel> getCollectionRecipes(final boolean isUserCollection,
                                                            final int userID, int category) {
        if (isUserCollection) {
            if (getUserCollection().getCategoryRecipes(category) != null) {
                return getUserCollection().getCategoryRecipes(category);
            }
            else {
                return getRecipesFromDB(isUserCollection, userID, category);
            }
        }
        else {
            if (getDiscoverCollection().getCategoryRecipes(category) != null) {
                return getDiscoverCollection().getCategoryRecipes(category);
            }
            else {
                return getRecipesFromDB(isUserCollection, userID, category);
            }
        }
    }

    public static List<RecipeListModel> getRecipesFromDB(final boolean isUserCollection,
                                                             final int userID, int category) {
        UserDao userDao = new UserDao();
        List<RecipeListModel> collectionRecipes = userDao.updateRecipeCollection(isUserCollection,
                userID, category);
        if (isUserCollection){
            getUserCollection().setRecipes(collectionRecipes, category);
        }
        else {
            getDiscoverCollection().setRecipes(collectionRecipes, category);
        }

        return collectionRecipes;
    }


    /**
     * Add new recipe to database
     * @param isNewRecipe is the recipe new or being edited
     * @param recipe the recipe to add
     * @param isOwner is the recipe new or saved from discover collection
     * @param image_icon
     * @return 0 on success, -1 on failure
     */
    public static int addNewRecipe(boolean isNewRecipe, Recipe recipe, boolean isOwner,
                                   Bitmap image_icon, int prevCategory) {
        Log.println(Log.INFO, "addNewRecipe", "isNewRecipe: " + isNewRecipe + ", isOwner: " + isOwner);
        int prevRecipeID = -1;
        if (!isNewRecipe && !isOwner) {
            isNewRecipe = true;
            isOwner = true;
            prevRecipeID = recipe.getId();
        }
        Log.println(Log.INFO, "addNewRecipe", "isNewRecipe: " + isNewRecipe + ", isOwner: " + isOwner);
        UserDao userDao = new UserDao();
        int id = userDao.addNewRecipe(isNewRecipe, recipe, getUser().getUserID(), isOwner, prevRecipeID);
        if (id == -1) {
            return -1;
        }

        if (isNewRecipe) {
            recipe.setId(id);

        }
        setUserCurrentRecipe(recipe);

        RecipeListModel recipeModel = new RecipeListModel();
        recipeModel.setRecipeId(recipe.getId());
        recipeModel.setRecipeTitle(recipe.getTitle());
        recipeModel.setRecipeDuration(recipe.getDuration());
        if (recipe.getImage() != null) {
            recipeModel.setRecipeImage(Graphics.resize(recipe.getImage(), 200, 200));
        }
        else {
            recipeModel.setRecipeImage(image_icon);
        }
        if (!isNewRecipe && recipe.getCategory() != prevCategory) {
            getUserCollection().removeRecipe(recipeModel.getRecipeId(), prevCategory);
        }
        getUserCollection().addNewRecipe(recipeModel, recipe.getCategory());
        getDiscoverCollection().addNewRecipe(recipeModel, recipe.getCategory());

        return 0;
    }

    /**
     * Delete recipe from user's collection
     * @param recipe
     */
    public static void deleteRecipe(Recipe recipe) {
        Log.println(Log.INFO, "deleteRecipe","deleteRecipe");
        UserDao userDao = new UserDao();
        userDao.deleteRecipe(getUser().getUserID(), recipe.getId());
        getUserCollection().removeRecipe(recipe.getId(), recipe.getCategory());
        getUserCollection().setCurRecipe(null);
    }

    /**
     * Get a full recipe from its short info
     * @param rlm
     * @return recipe
     */
    public static Recipe getRecipeFromShortInfo(RecipeListModel rlm) {
        Recipe recipe = new Recipe(rlm.getRecipeId(), rlm.getRecipeTitle(), rlm.getRecipeDuration());
        UserDao userDao = new UserDao();
        if (isUserSignedIn()) {
            userDao.updateRecipe(recipe, getUser().getUserID());
        }
        else {
            userDao.updateRecipe(recipe, -1);
        }

        return recipe;
    }

    public static List<WebpageInfo> getLinksFromDB() {
        UserDao userDao = new UserDao();
        List<WebpageInfo> links = userDao.getLinks(getUser().getUserID());
        return links;
    }

    public static void addLink(WebpageInfo webpageInfo) {
        UserDao userDao = new UserDao();
        userDao.addLink(getUser().getUserID(), webpageInfo);
        getUserCollection().addLink(webpageInfo);
    }
}
