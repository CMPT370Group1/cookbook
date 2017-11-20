package com.feasymax.cookbook.view.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.fragment.common.ShowRecipesFragment;
import com.feasymax.cookbook.view.list.RecipeListAdapter;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipesFragment extends ShowRecipesFragment{
/*
    private RelativeLayout rl;
    */
    private Button btnAllCategories;

    ListView list;
    RecipeListAdapter adapter;
    public RecipesFragment CustomListView = null;
    public List<RecipeListModel> CustomListViewValuesArr;

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_recipes, container, false);

        setHasOptionsMenu(true);

        btnAllCategories = (Button) view.findViewById(R.id.buttonAllCategories);
        btnAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterAllCategoriesFragment();
            }
        });

        CustomListView = this;
        // Take some data in list ( CustomListViewValuesArr )
        setListData();

        Resources res = getResources();
        list = ( ListView )view.findViewById( R.id.list );

        // Create Custom Adapter
        adapter = new RecipeListAdapter( this, CustomListViewValuesArr, res );
        list.setAdapter( adapter );

        return view ;
    }

    // Function to set data in List
    public void setListData()
    {
        if (getActivity() instanceof RecipesActivity){
            CustomListViewValuesArr = Application.getUserCollectionRecipes();
        }
        else if (getActivity() instanceof DiscoverActivity) {
            CustomListViewValuesArr = Application.getDiscoverCollectionRecipes();
        }

        // TODO: remove when database is active
        if (CustomListViewValuesArr.size() == 0) {
            final RecipeListModel recipe1 = new RecipeListModel();

            // Firstly take data in model object
            recipe1.setRecipeId(0);
            recipe1.setRecipeTitle("Delicious cake");
            recipe1.setRecipeImage(Graphics.decodeSampledBitmapFromResource(getResources(), R.drawable.dessert, 200, 200));

            recipe1.setRecipeDuration(150);

            // Take Model Object in List
            CustomListViewValuesArr.add( recipe1 );

            final RecipeListModel recipe2 = new RecipeListModel();

            // Firstly take data in model object
            recipe2.setRecipeId(1);
            recipe2.setRecipeTitle("Pumpkin soup");
            recipe2.setRecipeImage(Graphics.decodeSampledBitmapFromResource(getResources(), R.drawable.soup, 200, 200));
            recipe2.setRecipeDuration(30);

            // Take Model Object in List
            CustomListViewValuesArr.add( recipe2 );
        }
    }


    /*  This function used by adapter */
    @Override
    public void onItemClick(int mPosition)
    {
        RecipeListModel tempValues = CustomListViewValuesArr.get(mPosition);
        // TODO: remove image from Recipe constructor and get bigger version from db in getRecipeFromShortInfo
        Recipe curr = new Recipe(tempValues.getRecipeId(), tempValues.getRecipeTitle(), tempValues.getRecipeDuration(), tempValues.getRecipeImage());
        com.feasymax.cookbook.model.Application.getRecipeFromShortInfo(curr);
        if (this.getActivity() instanceof RecipesActivity) {
            com.feasymax.cookbook.model.Application.setUserCurrentRecipe(curr);
        }
        else if (this.getActivity() instanceof DiscoverActivity) {
            com.feasymax.cookbook.model.Application.setDiscoverCurrentRecipe(curr);
        }
        else {
            Log.println(Log.ERROR, "onItemClick", "Unexpected activity");
        }
        enterRecipeViewFragment();
    }


    protected void enterRecipeViewFragment() {
        RecipeViewFragment a2Fragment = new RecipeViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    private void enterAllCategoriesFragment() {
        RecipeCategoriesFragment a2Fragment = new RecipeCategoriesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Log.println(Log.INFO, "MENU","action_info has clicked");
                return true;
            default:
                Log.println(Log.INFO, "MENU","error");
                break;
        }

        return false;
    }
}
