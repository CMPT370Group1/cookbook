package com.feasymax.cookbook.view.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.util.WebSearch;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.view.ViewTransactions;
import com.feasymax.cookbook.view.fragment.common.ShowWebpagesFragment;
import com.feasymax.cookbook.view.list.LinksListAdapter;
import com.feasymax.cookbook.view.list.RecipeListAdapter;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 * Fragment for Webserach tab in Discover screen
 */

public class DiscoverWebsearchFragment extends ShowWebpagesFragment {

    public static final String FRAGMENT_ID = "DiscoverWebsearchFragment";

    private android.widget.SearchView searchView;
    private RelativeLayout noItemsLayout;

    ListView list;
    LinksListAdapter adapter;
    public DiscoverWebsearchFragment CustomListView = null;
    public List<WebpageInfo> CustomListViewValuesArr;

    public DiscoverWebsearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dis_websearch, container, false);

        setHasOptionsMenu(true);

        noItemsLayout = view.findViewById(R.id.noItemsLayout);

        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.println(Log.INFO, "websearch", s);
                CustomListViewValuesArr = WebSearch.getWebSearch(s);
                if (CustomListViewValuesArr.size() != 0) {
                    noItemsLayout.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    setAdapter();
                    // hide keyboard
                    InputMethodManager imm = (InputMethodManager)getActivity().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        CustomListView = this;
        list = view.findViewById( R.id.list );
        setAdapter();

        return view;
    }

    @Override
    public void setListData() {}

    public void setAdapter() {
        // Create Custom Adapter
        Resources res = getResources();
        adapter = null;
        adapter = new LinksListAdapter( this, CustomListViewValuesArr, res );
        list.setAdapter( adapter );
    }

    public void onItemClick(int mPosition)
    {
        WebpageInfo tempValues = CustomListViewValuesArr.get(mPosition);
        Application.getDiscoverCollection().setWebsearchResult(tempValues.getUrl());
        enterWebpageViewFragment();
    }

    @Override
    protected void enterWebpageViewFragment() {
        WebPageViewFragment a2Fragment = new WebPageViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        ViewTransactions.getViews().add(FRAGMENT_ID);
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }
}