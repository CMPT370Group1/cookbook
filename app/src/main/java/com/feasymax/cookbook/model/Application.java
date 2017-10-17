package com.feasymax.cookbook.model;

import com.feasymax.cookbook.model.entity.DiscoverCollection;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.entity.RecipeShortInfo;
import com.feasymax.cookbook.model.entity.UserAccount;
import com.feasymax.cookbook.model.entity.UserCollection;
import com.feasymax.cookbook.model.util.UserDao;

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
    private static DiscoverCollection discoverCollection = null;

    /**
     * Private constructor, because there could not be an Application object
     */
    private Application() {}

    public static UserAccount getUser() {
        return user;
    }

    public static String getUserName() {
        return getUser().getUsername();
    }

    public static String getUserEmail() {
        UserDao userDao = new UserDao();
        String email = userDao.getEmail(getUser().getUserID());
        if (email == null) {
            return "";
        } else {
            return email;
        }
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
            discoverCollection = new DiscoverCollection();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Register a new user with given username and password
     * @param username the username of the new user
     * @param password the password of the new user
     * @return true on success, false on failure to register
     */
    public static boolean userRegister(String username, String password){
        UserDao userDao = new UserDao();
        int userID = userDao.regUser(username, password);
        if (userID != 0) {
            user = new UserAccount(userID, username);
            userCollection = new UserCollection();
            discoverCollection = new DiscoverCollection();
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
            discoverCollection = new DiscoverCollection();
            return true;
        } else {
            return false;
        }
    }

    /**
     * When the user signs out, set the user object to null
     */
    public static void userSignOut(){
        // TODO
        user = null;
        userCollection = null;
        discoverCollection = null;
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

    /**
     *
     * @param recipes
     */
    public static void setUserCollectionRecipes(List<RecipeShortInfo> recipes) {
        userCollection.setRecipes(recipes);
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
    public static List<RecipeShortInfo> getUserCollectionRecipes() {
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
    public static void addUserRecipe(RecipeShortInfo recipeInfo) {
        userCollection.addNewRecipe(recipeInfo);
    }

    /**
     *
     * @param recipeInfo
     * @return
     */
    public static boolean removeUserRecipe(RecipeShortInfo recipeInfo) {
        return userCollection.removeRecipe(recipeInfo);
    }

    /**
     *
     * @param recipes
     */
    public static void setDiscoverCollectionRecipes(List<RecipeShortInfo> recipes) {
        discoverCollection.setRecipes(recipes);
    }

    /**
     *
     * @param recipe
     */
    public static void setDiscoverCurrentRecipe(Recipe recipe) {
        discoverCollection.setCurRecipe(recipe);
    }

    /**
     *
     * @return
     */
    public static List<RecipeShortInfo> getDiscoverCollectionRecipes() {
        return discoverCollection.getRecipes();
    }

    /**
     *
     * @param recipeInfo
     */
    public static void addDiscoverRecipe(RecipeShortInfo recipeInfo) {
        discoverCollection.addNewRecipe(recipeInfo);
    }

    /**
     *
     * @param recipeInfo
     * @return
     */
    public static boolean removeDiscoverRecipe(RecipeShortInfo recipeInfo) {
        return discoverCollection.removeRecipe(recipeInfo);
    }

    /**
     *
     * @return
     */
    public static Recipe getDiscoverCurrentRecipe() {
        return discoverCollection.getCurRecipe();
    }
}
