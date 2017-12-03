package com.feasymax.cookbook.model.entity;

/**
 * Created by Olya on 2017-10-12.
 * Collection of all users recipes.
 */

public class DiscoverCollection extends UserCollection {

    /**
     * The current web-page to display
     */
    private String websearchResult;

    /**
     * Public constructor
     */
    public DiscoverCollection() {
        super();
        curRecipe = null;
        category = -1;
    }

    /**
     * Get the current web-page
     * @return
     */
    public String getWebsearchResult() {
        return websearchResult;
    }

    /**
     * Set the current web-page
     * @param websearchResult
     */
    public void setWebsearchResult(String websearchResult) {
        this.websearchResult = websearchResult;
    }
}
