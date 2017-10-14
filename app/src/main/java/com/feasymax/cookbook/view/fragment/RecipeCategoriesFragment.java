package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feasymax.cookbook.view.fragment.common.CategoriesFragment;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipeCategoriesFragment extends CategoriesFragment {

    public RecipeCategoriesFragment() {
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
