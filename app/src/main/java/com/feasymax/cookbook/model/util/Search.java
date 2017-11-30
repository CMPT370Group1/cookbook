package com.feasymax.cookbook.model.util;

/**
 * Created by Olya on 2017-10-12.
 */
import android.util.Log;

import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.LinkedList;
import java.util.List;


//search collection by many attributes such as

public class Search {

     private Search() {}

     public static List<RecipeListModel> getSearchResults(String input, int activity) {
         UserDao userDao = new UserDao();
         List<RecipeListModel> results;

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
     //advanced search

    public static List<RecipeListModel> getAdvancedSearchResults(String title, int category, String
            directions, String ingredients, String tags, int activity, boolean isIncludingAllAttributes) {

        UserDao userDao = new UserDao();
        List<RecipeListModel> results;


        //splitting title string and putting it in titleTokens
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


//List<String> searchTokens = new LinkedList<>();
//        for (String retval : input.split(" ")) {
//            searchTokens.add(retval);
//        }