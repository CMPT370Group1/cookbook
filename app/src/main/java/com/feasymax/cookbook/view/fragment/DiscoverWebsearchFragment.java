package com.feasymax.cookbook.view.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.util.WebSearch;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.view.fragment.common.ShowWebpagesFragment;
import com.feasymax.cookbook.view.list.WebsearchListAdapter;

/**
 * Created by Olya on 2017-09-21.
 * Fragment for Webserach tab in Discover screen
 */

public class DiscoverWebsearchFragment extends ShowWebpagesFragment {

    public static final String FRAGMENT_ID = "DiscoverWebsearchFragment";

    private android.widget.SearchView searchView;
    WebsearchListAdapter adapter;

    /**
     * Required empty public constructor
     */
    public DiscoverWebsearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dis_websearch, container, false);

        setHasOptionsMenu(true);

        noItemsLayout = view.findViewById(R.id.noItemsLayout);

        // set up search view
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.println(Log.INFO, "websearch", s);
                // obtain the list of web-pages from the search query
                CustomListViewValuesArr = WebSearch.getWebSearch(s);
                if (CustomListViewValuesArr.size() != 0) {
                    // show the search results
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

        // set up the listview
        CustomListView = this;
        list = view.findViewById( R.id.list );
        setAdapter();

        return view;
    }

    /**
     * Nothing to do since we get the list of web-pages from search query
     */
    @Override
    public void setListData() {}

    /**
     * Set adapter for the listview to display web-pages
     */
    public void setAdapter() {
        // Create Custom Adapter
        Resources res = getResources();
        adapter = null;
        adapter = new WebsearchListAdapter(this, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    /**
     * Open clicked web-page in webview
     * @param mPosition
     */
    public void onItemClick(int mPosition)
    {
        WebpageInfo tempValues = CustomListViewValuesArr.get(mPosition);
        Application.getDiscoverCollection().setWebsearchResult(tempValues.getUrl());
        enterWebpageViewFragment();
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
}