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
}