package com.olya.cookbook.model;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 */

public class UserAccount {

    private int userID;
    private List<Integer> recipeIDs;

    public UserAccount(int userID, List<Integer> recipeIDs) {
        this.userID = userID;
        this.recipeIDs = recipeIDs;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Integer> getRecipeIDs() {
        return recipeIDs;
    }

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
}
