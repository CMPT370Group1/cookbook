package com.feasymax.cookbook.model.util;

/**
 * Created by Olya on 2017-10-12.
 * Utility class for searching a collection of recipes by keywords in title and directions
 * (basic search) or specifying each attribute individually (advanced search)
 */

import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.RecipeShortInfo;

import java.util.LinkedList;
import java.util.List;


public class Search {

    /**
     * Private empty constructor
     */
    private Search() {}

    /**
     * Get search results for basic search from the database
     * @param input
     * @param activity
     * @return
     */
    public static List<RecipeShortInfo> getSearchResults(String input, int activity) {
         UserDao userDao = new UserDao();
         List<RecipeShortInfo> results;

        // Tokenize keywords
         List<String> searchTokens = new LinkedList<>();
         for (String retval : input.split(" ")) {
             searchTokens.add(retval);
         }

         if (activity == 0) {
             int userId = Application.getUser().getUserID();
             results = userDao.searchRecipes(true, userId, searchTokens);
         } else {
             results = userDao.searchRecipes(false, -1, searchTokens);
         }

         return results;
    }

    /**
     * Get search results for advanced search from the database
     * @param title
     * @param category
     * @param directions
     * @param ingredients
     * @param tags
     * @param activity
     * @param isIncludingAllAttributes flag indicating if the result recipes should contain all
     *                                 specified attributes
     * @return
     */
    public static List<RecipeShortInfo> getAdvancedSearchResults(String title, int category, String
            directions, String ingredients, String tags, int activity, boolean isIncludingAllAttributes) {

        UserDao userDao = new UserDao();
        List<RecipeShortInfo> results;

        // Tokenize keywords string for each attribute
        List<String> titleTokens = new LinkedList<>();
        if (!title.trim().equals("")) {
            for (String retval : title.split(" ")) {
                titleTokens.add(retval);
            }
        }
        List<String> directionsTokens = new LinkedList<>();
        if (!directions.trim().equals("")) {
            for (String retval : directions.split(" ")) {
                directionsTokens.add(retval);
            }
        }
        List<String> ingredientsTokens = new LinkedList<>();
        if (!ingredients.trim().equals("")) {
            for (String retval : ingredients.split(" ")) {
                ingredientsTokens.add(retval);
            }
        }
        List<String> tagsTokens = new LinkedList<>();
        if (!tags.trim().equals("")) {
            for (String retval : tags.split(" ")) {
                tagsTokens.add(retval);
            }
        }

        if (activity == 0) {
            int userId = Application.getUser().getUserID();
            results = userDao.advancedSearchRecipes(true, userId, titleTokens, directionsTokens,
                    ingredientsTokens, tagsTokens, category, isIncludingAllAttributes);
        } else {
            results = userDao.advancedSearchRecipes(false, -1, titleTokens, directionsTokens,
                    ingredientsTokens, tagsTokens, category, isIncludingAllAttributes);
        }

        return results;
    }
}