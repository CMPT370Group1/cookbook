package com.feasymax.cookbook.view.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feasymax.cookbook.view.fragment.DiscoverWebsearchFragment;
import com.feasymax.cookbook.view.fragment.RecipeSearchFragment;
import com.feasymax.cookbook.view.fragment.CategoriesFragment;

/**
 * Created by Olya on 2017-09-21.
 * Adapter for tabs in DiscoverActivity
 */

public class DiscoverAdapter extends FragmentStatePagerAdapter {
    /**
     * List of tabs
     */
    private String[] titles ={"RECIPES","SEARCH", "WEB SEARCH"};
    private Fragment[] fragments = {new CategoriesFragment(),new RecipeSearchFragment(),
            new DiscoverWebsearchFragment()};

    public DiscoverAdapter(FragmentManager fm, Context c){
        super(fm);
    }

    /**
     * Get the fragment corresponding to the selected tab
     * @param position the tab number
     * @return the fragment
     */
    @Override
    public Fragment getItem(int position) {
        Fragment frag = fragments[position];
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
