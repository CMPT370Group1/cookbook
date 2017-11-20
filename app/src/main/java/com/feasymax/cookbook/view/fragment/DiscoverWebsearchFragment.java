package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.util.WebSearch;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.model.entity.WebpageInfo;

import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class DiscoverWebsearchFragment extends Fragment {
    android.widget.SearchView searchView;
    private List<WebpageInfo> searchResults;

    public DiscoverWebsearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dis_websearch, container, false);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.println(Log.INFO, "websearch", s);
                WebSearch.getWebSearch(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }




}