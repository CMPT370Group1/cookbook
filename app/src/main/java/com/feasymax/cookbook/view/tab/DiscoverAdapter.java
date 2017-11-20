package com.feasymax.cookbook.view.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feasymax.cookbook.view.fragment.DiscoverWebsearchFragment;
import com.feasymax.cookbook.view.fragment.RecipeCategoriesFragment;
import com.feasymax.cookbook.view.fragment.RecipeSearchFragment;

/**
 * Created by Olya on 2017-09-21.
 */

public class DiscoverAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles ={"RECIPES","SEARCH", "WEB SEARCH"};

    public DiscoverAdapter(FragmentManager fm, Context c){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag= null;

        if(position ==0){
            frag = new RecipeCategoriesFragment();
        }else if(position == 1){
            frag = new RecipeSearchFragment();
        }else if(position == 2){
            frag = new DiscoverWebsearchFragment();
        }

        Bundle b = new Bundle();
        b.putInt("position", position);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    public CharSequence getPageTitle(int position){
        return titles[position];
    }

}
