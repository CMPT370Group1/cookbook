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

    private final String FRAGMENT_ID = "RecipesFragment";

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
            CustomListViewValuesArr = Application.getCollectionFromDB(true,
                    Application.getUser().getUserID(), Application.getUserCollection().getCategory());
        }
        else if (getActivity() instanceof DiscoverActivity) {
            CustomListViewValuesArr = Application.getCollectionFromDB(false, -1,
                    Application.getDiscoverCollection().getCategory());
        }
    }


    /*  This function used by adapter */
    @Override
    public void onItemClick(int mPosition)
    {
        RecipeListModel tempValues = CustomListViewValuesArr.get(mPosition);
        Recipe recipe = Application.getRecipeFromShortInfo(tempValues);
        if (this.getActivity() instanceof RecipesActivity) {
            recipe.setCategory(Application.getUserCollection().getCategory());
            Application.setUserCurrentRecipe(recipe);
        }
        else if (this.getActivity() instanceof DiscoverActivity) {
            recipe.setCategory(Application.getDiscoverCollection().getCategory());
            Application.setDiscoverCurrentRecipe(recipe);
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
        transaction.addToBackStack(FRAGMENT_ID);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    private void enterAllCategoriesFragment() {
        RecipeCategoriesFragment a2Fragment = new RecipeCategoriesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(FRAGMENT_ID);
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
