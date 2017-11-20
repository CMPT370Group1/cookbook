package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.util.Search;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.fragment.common.OnBackPressFragment;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipeSearchFragment extends OnBackPressFragment {

    private SearchView searchView;
    private List<RecipeListModel> searchResults;

    public RecipeSearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_search, container, false);



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
                searchResults = Search.getSearchResults(s, activity);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return view ;
    }

    //
}
