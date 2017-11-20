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
         for(String retval : input.split(" ")){
             searchTokens.add(retval);
         }

         //searchTokens.add(input);
         //tokenize the input

         if (activity == 0){
             int userId = Application.getUser().getUserID();

             results = userDao.searchRecipes(true, userId, searchTokens);
         }
         else {
             results = userDao.searchRecipes(false, -1, searchTokens);
         }

         return results;
         //return title ,duration(int) and bitmap(image)
     }

//    Take a string, connect database, search , then return a list
//    This is from discover page



}

//the function the layout will be calling should be static

//MYRECIPES
//MUST LOGIN BEFORE SEARCHING
// WILL SEARCH RECIPEID that was saved.

//DISCOVER
//CALL ONE FUNCTION

//search ingredients table, tags table and recipes table
//at least one match,
//factorial n!
//