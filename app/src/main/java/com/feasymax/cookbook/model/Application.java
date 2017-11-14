package com.feasymax.cookbook.model;

import android.util.Log;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.entity.DiscoverCollection;
import com.feasymax.cookbook.model.entity.Ingredient;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.entity.RecipeShortInfo;
import com.feasymax.cookbook.model.entity.UserAccount;
import com.feasymax.cookbook.model.entity.UserCollection;
import com.feasymax.cookbook.model.util.UserDao;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.LinkedList;
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
    private static DiscoverCollection discoverCollection = new DiscoverCollection();

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
        // TODO: remove if statement when DB is working
        /*
        if (user == null) {
            user = new UserAccount(0, "temp", "temp@domain.com");
            userCollection = new UserCollection();
        }
        */
        return (user != null);
    }

    /**
     *
     * @param recipes
     */
    public static void setUserCollectionRecipes(List<RecipeListModel> recipes) {
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

    /**
     *
     * @param recipeInfo
     * @return
     */
    public static boolean removeUserRecipe(RecipeListModel recipeInfo) {
        return userCollection.removeRecipe(recipeInfo);
    }

    /**
     *
     * @param recipes
     */
    public static void setDiscoverCollectionRecipes(List<RecipeListModel> recipes) {
        discoverCollection.setRecipes(recipes);
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
    public static List<RecipeListModel> getDiscoverCollectionRecipes() {
        return discoverCollection.getRecipes();
    }

    /**
     *
     * @param recipeInfo
     */
    public static void addDiscoverRecipe(RecipeListModel recipeInfo) {
        discoverCollection.addNewRecipe(recipeInfo);
    }

    /**
     *
     * @param recipeInfo
     * @return
     */
    public static boolean removeDiscoverRecipe(RecipeListModel recipeInfo) {
        return discoverCollection.removeRecipe(recipeInfo);
    }

    /**
     *
     * @return
     */
    public static Recipe getDiscoverCurrentRecipe() {
        return discoverCollection.getCurRecipe();
    }

    // TODO: change the stub to actual function
    public static void getRecipeFromShortInfo(Recipe recipe) {
        //recipe.setImage(null);
        recipe.setCategory(0);
        recipe.setDirections("Directions to make this recipe. Directions to make this recipe. " +
                "Directions to make this recipe. Directions to make this recipe. Directions to " +
                "make this recipe. Directions to make this recipe. Directions to make this recipe.");
        List<Ingredient> ingredients = new LinkedList<>();
        ingredients.add(new Ingredient("butter", 200, 1));
        ingredients.add(new Ingredient("milk", 1, 7));
        ingredients.add(new Ingredient("flour", 2, 7));
        ingredients.add(new Ingredient("salt", 0.5, 5));
        ingredients.add(new Ingredient("sugar", 3, 6));
        recipe.setIngredients(ingredients);
        List<String> tags = new LinkedList<>();
        tags.add("Breakfast");
        tags.add("Yummy");
        tags.add("Breakfast");
        tags.add("Yummyfdfd");
        tags.add("Breakfast");
        tags.add("Yummydfdfdfdfdfd");
        tags.add("Breakfa");
        tags.add("Yummyaa");
        tags.add("Breakfast");
        tags.add("YummyYummyYummy");

        recipe.setTags(tags);
    }
}
