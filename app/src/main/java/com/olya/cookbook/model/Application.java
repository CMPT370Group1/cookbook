package com.olya.cookbook.model;

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
     * Private constructor, because there could not be an Application object
     */
    private Application() {}

    public static UserAccount getUser() {
        return user;
    }

    public static String getUserName() {
        return "user_name";
    }

    public static String getUserEmail() {
        return "user_name@domain.com";
    }

    /**
     * Sign in an existing user
     * @param username the username of the existing user
     * @param password the password of the existing user
     */
    public static void userSignIn(String username, String password){
        // verify that a user with username and password exists
        // get the userID and recipes from database
        int userID = 0;
        List<Integer> recipeIDs = null;
        user = new UserAccount(userID, recipeIDs);
    }

    /**
     * Register a new user
     * @param username the username of the new user
     * @param password the password of the new user
     */
    public static void userRegister(String username, String password){
        int userID = 0;
        List<Integer> recipeIDs = null;
        user = new UserAccount(userID, recipeIDs);
    }

    /**
     * When the user signs out, set the user object to null
     */
    public static void userSignOut(){
        user = null;
    }

    /**
     * Edit user account info
     * @param userName
     * @param userEmail
     * @param oldPassword
     * @param newPassword
     */
    public static void userEditAccount(String userName, String userEmail, String oldPassword,
                                       String newPassword){
        // check the return values
        userChangeInfo(userName, userEmail);
        userChangePassword(oldPassword, newPassword);

    }

    // maybe make it boolean

    /**
     * Change account information for the user
     * @param userName the new username
     * @param userEmail the new email address
     * @return true if the new account info is set successfully, false otherwise
     */
    private static boolean userChangeInfo(String userName, String userEmail){
        // if we choose to verify email address, send an email message and somehow accept
        // verification
        return false;
    }

    /**
     * Change password for the user
     * @param oldPassword the existing password that must be verified in order to set the new one
     * @param newPassword the new password
     * @return true if the new password is set successfully, false otherwise
     */
    private static boolean userChangePassword(String oldPassword, String newPassword){
        // verify old password, if not right return false
        // set the new one, return true
        return false;
    }

    /**
     * Checks if the user is signed in
     * @return true is user is signed in, false otherwise
     */
    public static boolean isUserSignedIn() {
        return (user != null);
    }
}