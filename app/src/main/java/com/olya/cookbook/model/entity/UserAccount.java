package com.olya.cookbook.model.entity;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 */

public class UserAccount {

    /**
     * User id from the database
     */
    private int userID;
    /**
     * List of recipe ids that the user has in the collection
     */
    private List<Integer> recipeIDs;

    /**
     * Username from the database
     */
    private String username;
    /**
     * User email from the database
     */
    private String email;

    /**
     * A public constructor that initializes all user's attributes
     * @param userID
     * @param recipeIDs
     */
    public UserAccount(int userID, List<Integer> recipeIDs) {
        this.userID = userID;
        this.recipeIDs = recipeIDs;
    }

    public UserAccount(int userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    public UserAccount(int userID, String username, String email) {
        this.userID = userID;
        this.username = username;
        this.email = email;
    }

    /**
     * Get user id
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Set user id
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Get list of recipe ids that the user has in the collection
     * @return
     */
    public List<Integer> getRecipeIDs() {
        return recipeIDs;
    }

    /**
     * Add a new recipe id to the list of user's recipes
     * @param recipeID
     */
    public void addRecipeID(Integer recipeID) {
        this.recipeIDs.add(recipeID);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userID=" + userID +
                ", recipeIDs=" + recipeIDs +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
