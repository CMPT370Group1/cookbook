package com.feasymax.cookbook.view.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feasymax.cookbook.view.fragment.RecipeAddFragment;
import com.feasymax.cookbook.view.fragment.RecipeLinksFragment;
import com.feasymax.cookbook.view.fragment.RecipeSearchFragment;
import com.feasymax.cookbook.view.fragment.CategoriesFragment;

/**
 * Created by Olya on 2017-09-21.
 * Adapter for tabs in RecipesActivity
 */

public class RecipeAdapter extends FragmentStatePagerAdapter {
    /**
     * List of tabs
     */
    private String[] titles ={"ALL","ADD","SEARCH","LINKS"};

    public RecipeAdapter(FragmentManager fm, Context c){
        super(fm);
    }

    /**
     * Get the fragment corresponding to the selected tab
     * @param position the tab number
     * @return the fragment
     */
    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;

        if(position == 0){
            frag = new CategoriesFragment();
        }else if(position == 1){
            frag = new RecipeAddFragment();
        }else if(position == 2){
            frag = new RecipeSearchFragment();
        }else if(position == 3){
            frag = new RecipeLinksFragment();
        }

        return frag;
    }

    /**
     * Get the number of tabs
     * @return
     */
    @Override
    public int getCount() {
        return titles.length;
    }

    /**
     * Get the tab title from its position
     * @param position
     * @return
     */
    public CharSequence getPageTitle(int position){
        return titles[position];
    }

}
