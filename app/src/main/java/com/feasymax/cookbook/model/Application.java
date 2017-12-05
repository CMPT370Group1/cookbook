package com.feasymax.cookbook.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.feasymax.cookbook.model.entity.DiscoverCollection;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.entity.UserAccount;
import com.feasymax.cookbook.model.entity.UserCollection;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.model.util.UserDao;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.model.entity.RecipeShortInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
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

    /**
     * Get the user
     * @return
     */
    public static UserAccount getUser() {
        return user;
    }

    /**
     * Sign in an existing user
     * @param username the username of the existing user
     * @param password the password of the existing user
     * @return true on success, false on failure to sign in
     */
    public static boolean userSignIn(String username, String password, Context context) throws SQLException{
        // verify that a user with username and password exists
        // get the userID and recipes from database
        UserDao userDao = new UserDao();
        user = new UserAccount(username);
        int userID = userDao.signInUser(user, password);
        if (userID != 0) {
            userCollection = new UserCollection();
            saveSession(context);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sign in user automatically using session
     * @param userID
     * @return
     */
    public static boolean userSignIn(int userID){
        UserDao userDao = new UserDao();
        user = new UserAccount(userID, "", "");
        userDao.getUserInfo(user);
        userCollection = new UserCollection();
        return true;
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
                                       String firstName, String lastName, Context context) throws SQLException{
        UserDao userDao = new UserDao();
        int userID = userDao.registerUser(username, password, email, firstName, lastName);
        if (userID != 0) {
            user = new UserAccount(userID, username, email);
            userCollection = new UserCollection();
            saveSession(context);
            return true;
        } else {
            return false;
        }
    }

    /**
     * When the user signs out, set the user object to null
     */
    public static void userSignOut(Context context){
        user = null;
        userCollection = null;
        removeSession(context);
    }

    /**
     * Delete session
     * @param context
     */
    public static void removeSession(Context context) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput("user.txt", Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            outputStream.close();
        }
        catch (Exception e) {
            File file = new File(context.getFilesDir(), "user.txt");
            try {
                outputStream = context.openFileOutput("user.txt", Context.MODE_PRIVATE);
                outputStream.write("".getBytes());
                outputStream.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Save the current session to internal storage
     * @param context
     */
    public static void saveSession(Context context) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput("user.txt", Context.MODE_PRIVATE);
            outputStream.write(Integer.toString(getUser().getUserID()).getBytes());
            outputStream.close();
        }
        catch (Exception e) {
            File file = new File(context.getFilesDir(), "user.txt");
            try {
                outputStream = context.openFileOutput("user.txt", Context.MODE_PRIVATE);
                outputStream.write(Integer.toString(getUser().getUserID()).getBytes());
                outputStream.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Read the user id from the file saved in internal storage for the app to get the session
     * @param context
     * @return the user id
     */
    public static int readSession(Context context) {
        File file = new File(context.getFilesDir(), "user.txt");
        FileInputStream inputStream;
        byte[] bytes = new byte[(int)file.length()];
        int userID = -1;

        // Get the user id if the session is saved
        if (file.exists()) {
            try {
                inputStream = new FileInputStream(file);
                inputStream.read(bytes);
                inputStream.close();
                String contents = new String(bytes);
                userID = Integer.parseInt(contents);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Otherwise, it's -1, indicating that there is no session
        return userID;
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
        String res = userDao.updateUserAccount(userID, username, userEmail, oldPassword, newPassword);
        Log.d("res", res);
        if (res.contains("USERNAME")) {
            user.setUsername(username);
            user.setEmail(userEmail);
        } else {
            user.setEmail(userEmail);
        }
        return res;
    }

    /**
     * Check if there is a user signed in
     * @return true is user is signed in, false otherwise
     */
    public static boolean isUserSignedIn() {
        return (user != null);
    }

    /**
     * Get user collection
     * @return
     */
    public static UserCollection getUserCollection() {
        return userCollection;
    }

    /**
     * Get the discover collection
     * @return
     */
    public static DiscoverCollection getDiscoverCollection() {
        return discoverCollection;
    }

    public static void setDiscoverCollection(DiscoverCollection discCollection){
        discoverCollection = discCollection;
    }

    /**
     * Get recipes from a category
     * @param isUserCollection
     * @param userID
     * @param category
     * @return
     */
    public static List<RecipeShortInfo> getCollectionRecipes(final boolean isUserCollection,
                                                             final int userID, int category) {
        if (isUserCollection) {
            // if list exist, just return it
            if (getUserCollection().getCategory() == category && getUserCollection().getRecipes() != null) {
                return getUserCollection().getRecipes();
            }
            // otherwise, obtain from database
            else {
                return getRecipesFromDB(isUserCollection, userID, category);
            }
        }
        else {
            // if list exist, just return it
            if (getDiscoverCollection().getCategory() == category && getDiscoverCollection().getRecipes() != null) {
                return getDiscoverCollection().getRecipes();
            }
            // otherwise, obtain from database
            else {
                return getRecipesFromDB(isUserCollection, userID, category);
            }
        }
    }

    /**
     * Get recipes in a category from database and set the corresponding list to them
     * @param isUserCollection
     * @param userID
     * @param category
     * @return
     */
    public static List<RecipeShortInfo> getRecipesFromDB(final boolean isUserCollection,
                                                         final int userID, int category) {
        UserDao userDao = new UserDao();
        List<RecipeShortInfo> collectionRecipes = userDao.updateRecipeCollection(isUserCollection,
                userID, category);
        if (isUserCollection){
            getUserCollection().setRecipes(collectionRecipes);
            getUserCollection().setCategory(category);
        }
        else {
            getDiscoverCollection().setRecipes(collectionRecipes);
            getDiscoverCollection().setCategory(category);
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
        // if the user is editing the recipe saved from another use, save the edited recipe as the
        // new recipe belonging to the user
        if (!isNewRecipe && !isOwner) {
            isNewRecipe = true;
            isOwner = true;
            prevRecipeID = recipe.getId();
        }
        UserDao userDao = new UserDao();
        int id = userDao.addNewRecipe(isNewRecipe, recipe, getUser().getUserID(), isOwner, prevRecipeID);
        if (id == -1) {
            return -1;
        }

        // if new recipe set the obtained id
        if (isNewRecipe) {
            recipe.setId(id);
        }
        // set the current recipe and category to display it
        getUserCollection().setCurRecipe(recipe);
        getUserCollection().setCategory(recipe.getCategory());

        // get the short info to add to the list of recipes in both collections
        RecipeShortInfo recipeModel = new RecipeShortInfo();
        recipeModel.setRecipeId(recipe.getId());
        recipeModel.setRecipeTitle(recipe.getTitle());
        recipeModel.setRecipeDuration(recipe.getDuration());
        if (recipe.getImage() != null) {
            recipeModel.setRecipeImage(Graphics.resize(recipe.getImage(), 200, 200));
        }
        else {
            recipeModel.setRecipeImage(image_icon);
        }
        // if the category has been changed, remove the short info from the previous category
        if (!isNewRecipe && recipe.getCategory() != prevCategory) {
            getUserCollection().removeRecipe(recipeModel.getRecipeId(), prevCategory);
        }
        // add the recipe to the list of recipes in both collections
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
        // delete from the database
        userDao.deleteRecipe(getUser().getUserID(), recipe.getId());
        // delete from the list in user collection
        getUserCollection().removeRecipe(recipe.getId(), recipe.getCategory());
        getUserCollection().setCurRecipe(null);
    }

    /**
     * Get a full recipe from its short info
     * @param rlm
     * @return recipe
     */
    public static Recipe getRecipeFromShortInfo(RecipeShortInfo rlm) {
        Recipe recipe = new Recipe(rlm.getRecipeId(), rlm.getRecipeTitle(), rlm.getRecipeDuration());
        UserDao userDao = new UserDao();
        if (isUserSignedIn()) {
            userDao.getFullRecipe(recipe, getUser().getUserID());
        }
        else {
            userDao.getFullRecipe(recipe, -1);
        }

        return recipe;
    }

    /**
     * Get user's saved links from the database
     * @return
     */
    public static List<WebpageInfo> getLinksFromDB() {
        UserDao userDao = new UserDao();
        List<WebpageInfo> links = userDao.getLinks(getUser().getUserID());
        return links;
    }

    /**
     * Add a new link to user's collection
     * @param webpageInfo
     */
    public static boolean addLink(WebpageInfo webpageInfo) {
        // if the link has already been added, ignore it
        if (getUserCollection().getLinks() != null) {
            if (getUserCollection().getLinks().contains(webpageInfo)) {
                return false;
            }
        }
        UserDao userDao = new UserDao();
        // add to database, obtaining id
        int id = userDao.addLink(getUser().getUserID(), webpageInfo);
        webpageInfo.setId(id);
        // add to the list in user collection
        getUserCollection().addLink(webpageInfo);
        return true;
    }

    /**
     * Remove a link from user's collection
     * @param webpageInfo
     */
    public static void deleteLink(WebpageInfo webpageInfo) {
        UserDao userDao = new UserDao();
        // delete from database
        userDao.deleteLink(webpageInfo.getId());
        // delete from the list in user collection
        getUserCollection().getLinks().remove(webpageInfo);
    }
}