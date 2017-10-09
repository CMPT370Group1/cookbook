package com.olya.cookbook.model;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 */

public class Application {

    private static UserAccount user = null;

    private Application() {}

    public static void userSignIn(String username, String password){
        int userID = 0;
        List<Integer> recipeIDs = null;
        user = new UserAccount(userID, recipeIDs);
    }

    public static void userRegister(String username, String password){
        int userID = 0;
        List<Integer> recipeIDs = null;
        user = new UserAccount(userID, recipeIDs);
    }

    public static void userSignOut(){
        user = null;
    }

    public static void userEditAccount(String userName, String userEmail, String oldPassword,
                                       String newPassword){
        // if boolean, check the return values
        userChangeInfo(userName, userEmail);
        userChangePassword(oldPassword, newPassword);

    }

    // maybe make it boolean
    private static void userChangeInfo(String userName, String userEmail){

    }

    // maybe make it boolean
    private static void userChangePassword(String oldPassword, String newPassword){

    }

    public static boolean isUserSignedIn() {
        return (user != null);
    }
}
