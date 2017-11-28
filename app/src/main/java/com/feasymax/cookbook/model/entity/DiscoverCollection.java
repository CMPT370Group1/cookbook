package com.feasymax.cookbook.model.entity;

import android.util.Log;

import com.feasymax.cookbook.view.list.RecipeListModel;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olya on 2017-10-12.
 * Collection of all users recipes.
 */

public class DiscoverCollection extends UserCollection {

    private String websearchResult;

    public DiscoverCollection() {
        super();
        curRecipe = null;
        category = -1;
    }

    public String getWebsearchResult() {
        return websearchResult;
    }

    public void setWebsearchResult(String websearchResult) {
        this.websearchResult = websearchResult;
    }
}
