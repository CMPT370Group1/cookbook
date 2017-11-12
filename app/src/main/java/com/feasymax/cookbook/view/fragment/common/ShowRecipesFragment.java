package com.feasymax.cookbook.view.fragment.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.view.fragment.RecipeViewFragment;

/**
 * Created by Olya on 2017-11-11.
 */

public abstract class ShowRecipesFragment extends Fragment {

    public void onItemClick(int mPosition) {
        enterRecipeViewFragment();
    }

    public abstract void setListData();

    protected abstract void enterRecipeViewFragment();

}
