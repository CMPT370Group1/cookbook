package com.feasymax.cookbook.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.fragment.common.ShowRecipesFragment;
import com.feasymax.cookbook.view.list.RecipeListAdapter;
import com.feasymax.cookbook.model.entity.RecipeShortInfo;

import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipesFragment extends ShowRecipesFragment{

    public static final String FRAGMENT_ID = "RecipesFragment";

    private Button btnAllCategories;
    private RelativeLayout noItemsLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    ListView list;
    RecipeListAdapter adapter;
    public RecipesFragment CustomListView = null;
    public List<RecipeShortInfo> CustomListViewValuesArr;

    /**
     * Required empty public constructor
     */
    public RecipesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_recipes, container, false);

        setHasOptionsMenu(true);

        noItemsLayout = view.findViewById(R.id.noItemsLayout);

        btnAllCategories = view.findViewById(R.id.buttonAllCategories);
        btnAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterAllCategoriesFragment();
            }
        });
        if (getActivity() instanceof RecipesActivity) {
            btnAllCategories.setText(getResources().getStringArray(R.array.categories)[Application.
                    getUserCollection().getCategory()]);
        }
        else if (getActivity() instanceof DiscoverActivity){
            btnAllCategories.setText(getResources().getStringArray(R.array.categories)[Application.
                    getDiscoverCollection().getCategory()]);
        }


        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d("Swipe refresh", "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        int category;
                        if (getActivity() instanceof RecipesActivity) {
                            category = Application.getUserCollection().getCategory();
                            Application.getUserCollection().updateRecipes(category);
                        }
                        else if (getActivity() instanceof DiscoverActivity) {
                            category = Application.getDiscoverCollection().getCategory();
                            Application.getDiscoverCollection().updateRecipes(category);
                        }
                        onResume();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        list = view.findViewById( R.id.list );
        CustomListView = this;

        return view ;
    }

    // Function to set data in List
    public void setListData()
    {
        if (getActivity() instanceof RecipesActivity){
            CustomListViewValuesArr = Application.getUserCollection().getRecipes();
        }
        else if (getActivity() instanceof DiscoverActivity) {
            CustomListViewValuesArr = Application.getDiscoverCollection().getRecipes();
        }
        Log.println(Log.INFO, "setListData: recipes", CustomListViewValuesArr.toString());
    }

    public void setAdapter() {
        // Create Custom Adapter
        Resources res = getResources();
        adapter = null;
        adapter = new RecipeListAdapter( this, CustomListViewValuesArr, res );
        list.setAdapter( adapter );

    }


    /*  This function used by adapter */
    @Override
    public void onItemClick(int mPosition)
    {
        RecipeShortInfo tempValues = CustomListViewValuesArr.get(mPosition);
        Recipe recipe = Application.getRecipeFromShortInfo(tempValues);
        if (this.getActivity() instanceof RecipesActivity) {
            recipe.setCategory(Application.getUserCollection().getCategory());
            Application.getUserCollection().setCurRecipe(recipe);
        }
        else if (this.getActivity() instanceof DiscoverActivity) {
            recipe.setCategory(Application.getDiscoverCollection().getCategory());
            Application.getDiscoverCollection().setCurRecipe(recipe);
        }
        else {
            Log.println(Log.ERROR, "onItemClick", "Unexpected activity");
        }
        enterRecipeViewFragment();
    }


    protected void enterRecipeViewFragment() {
        RecipeViewFragment a2Fragment = new RecipeViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        list.setAdapter(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    private void enterAllCategoriesFragment() {
        CategoriesFragment a2Fragment = new CategoriesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        list.setAdapter(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_fragment, menu);
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

    @Override
    public void onResume() {
        super.onResume();

        CustomListViewValuesArr = null;
        setListData();
        if (CustomListViewValuesArr.size() != 0) {
            noItemsLayout.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            setAdapter();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {}
}
