package com.feasymax.cookbook.model;

import android.graphics.Bitmap;
import android.util.Log;

import com.feasymax.cookbook.model.entity.DiscoverCollection;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.entity.UserAccount;
import com.feasymax.cookbook.model.entity.UserCollection;
import com.feasymax.cookbook.model.util.UserDao;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
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
     *
     * @return
     */
    public static List<RecipeListModel> getUserCollectionRecipes() {
        return userCollection.getRecipes();
    }

    /**
     *
     * @return
     */
    public static Recipe getUserCurrentRecipe() {
        return userCollection.getCurRecipe();
    }

    /**
     *
     * @param recipeInfo
     */
    public static void addUserRecipe(RecipeListModel recipeInfo) {
        userCollection.addNewRecipe(recipeInfo);
    }

    public static DiscoverCollection getDiscoverCollection() {
        return discoverCollection;
    }

    public static void setDiscoverCollection(DiscoverCollection discCollection){
        discoverCollection = discCollection;
    }
    /**
     *
     * @param recipe
     */
    public static void setDiscoverCurrentRecipe(Recipe recipe) {
        Log.println(Log.INFO, "setCurrentRecipe", recipe.toString());
        discoverCollection.setCurRecipe(recipe);
    }

    /**
     *
     * @return
     */
    public static Recipe getDiscoverCurrentRecipe() {
        return discoverCollection.getCurRecipe();
    }

    /**
     *
     * @return
     */
    public static List<RecipeListModel> getCollectionFromDB(final boolean userCollection,
                                                            final int userID, int category) {
        UserDao userDao = new UserDao();
        return userDao.updateRecipeCollection(userCollection, userID, category);
    }


    public static int addNewRecipe(boolean isNewRecipe, Recipe recipe, boolean owner, Bitmap image_icon) {
        UserDao userDao = new UserDao();

        int id = userDao.addNewRecipe(isNewRecipe, recipe, getUser().getUserID(), owner);
        if (id == -1) {
            return -1;
        }

        if (isNewRecipe) {
            recipe.setId(id);

        }
        setUserCurrentRecipe(recipe);

        if (recipe.getCategory() == getUserCollection().getCategory()) {
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
            addUserRecipe(recipeModel);
        }

        return 0;
    }

    public static void deleteRecipe(int recipeID) {
        Log.println(Log.INFO, "deleteRecipe","deleteRecipe");
        UserDao userDao = new UserDao();
        userDao.deleteRecipe(getUser().getUserID(), recipeID);
        // delete corresponding recipe from UserCollection.recipes
        // set the UserCollection.currentRecipe to null
    }

    // TODO: change the stub to actual function
    public static Recipe getRecipeFromShortInfo(RecipeListModel rlm) {
        Recipe recipe = new Recipe(rlm.getRecipeId(), rlm.getRecipeTitle(), rlm.getRecipeDuration());
        UserDao userDao = new UserDao();
        userDao.updateRecipe(recipe);
        return recipe;
    }
}
