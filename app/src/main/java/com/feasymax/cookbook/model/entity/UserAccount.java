package com.feasymax.cookbook.model.entity;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 * User information.
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
     * Public constructor setting username
     * @param username
     */
    public UserAccount(String username) {
        this.userID = -1;
        this.username = username;
        this.email = "";
    }

    /**
     * Public constructor setting all attributes
     * @param userID
     * @param username
     * @param email
     */
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

    /**
     * Set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set email address
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get username
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get email address
     * @return
     */
    public String getEmail() {
        return email;
    }
}
