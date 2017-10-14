package com.olya.cookbook.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olya.cookbook.view.fragment.common.CategoriesFragment;

/**
 * Created by Olya on 2017-09-21.
 */

public class DiscoverCategoriesFragment extends CategoriesFragment {

    public DiscoverCategoriesFragment() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState) ;
    }

    // open a category fragment that shows all recipes in that category
    protected void enterRecipesFragment() {
        super.enterRecipesFragment();
    }
}
