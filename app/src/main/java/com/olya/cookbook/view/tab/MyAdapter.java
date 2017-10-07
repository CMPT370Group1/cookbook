package com.olya.cookbook.view.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.olya.cookbook.view.fragment.AddFragment;
import com.olya.cookbook.view.fragment.AllRecipesFragment;
import com.olya.cookbook.view.fragment.CategoriesFragment;
import com.olya.cookbook.view.fragment.LinksFragment;
import com.olya.cookbook.view.fragment.RecipesFragment;
import com.olya.cookbook.view.fragment.SearchFragment;

/**
 * Created by Olya on 2017-09-21.
 */

public class MyAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles ={"ALL","ADD","SEARCH","LINKS"};

    public MyAdapter(FragmentManager fm, Context c){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag= null;

        if(position ==0){
            frag = new CategoriesFragment();
        }else if(position == 1){
            frag = new AddFragment();
        }else if(position == 2){
            frag = new SearchFragment();
        }else if(position == 3){
            frag = new LinksFragment();
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
