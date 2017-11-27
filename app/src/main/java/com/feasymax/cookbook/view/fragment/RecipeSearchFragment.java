package com.feasymax.cookbook.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.util.Search;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.ViewTransactions;
import com.feasymax.cookbook.view.fragment.common.ShowRecipesFragment;
import com.feasymax.cookbook.view.list.RecipeListAdapter;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipeSearchFragment extends ShowRecipesFragment {

    public static final String FRAGMENT_ID = "RecipeSearchFragment";

    private SearchView searchView;

    ListView list;
    RecipeListAdapter adapter;
    public RecipeSearchFragment CustomListView = null;
    public List<RecipeListModel> CustomListViewValuesArr;

    public RecipeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_search, container, false);

        setHasOptionsMenu(true);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                int activity = 0;
                if (getActivity() instanceof RecipesActivity) {
                    activity = 0;
                }
                if (getActivity() instanceof DiscoverActivity) {
                    activity = 1;
                }
                else {
                    Log.println(Log.ERROR, "search", "unexpected activity");
                }

                CustomListViewValuesArr = Search.getSearchResults(s, activity);
                Log.println(Log.INFO, "search", CustomListViewValuesArr.toString());
                setAdapter();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        CustomListView = this;

        list = view.findViewById( R.id.list );

        setAdapter();

        return view ;
    }

    @Override
    public void setListData() {}

    public void setAdapter() {
        // Create Custom Adapter
        Resources res = getResources();
        adapter = null;
        adapter = new RecipeListAdapter( this, CustomListViewValuesArr, res );
        list.setAdapter( adapter );

    }

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

    @Override
    protected void enterRecipeViewFragment() {
        RecipeViewFragment a2Fragment = new RecipeViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        ViewTransactions.getViews().add(FRAGMENT_ID);
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    //
}
