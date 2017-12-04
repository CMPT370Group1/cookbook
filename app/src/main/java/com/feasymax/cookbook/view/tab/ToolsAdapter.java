package com.feasymax.cookbook.view.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.feasymax.cookbook.view.fragment.ToolsAccountFragment;
import com.feasymax.cookbook.view.fragment.ToolsMassFragment;
import com.feasymax.cookbook.view.fragment.ToolsMassVolumeFragment;
import com.feasymax.cookbook.view.fragment.ToolsTemperatureFragment;
import com.feasymax.cookbook.view.fragment.ToolsVolumeFragment;

/**
 * Created by Olya on 2017-09-21.
 * Adapter for tabs in ToolsActivity
 */

public class ToolsAdapter extends FragmentStatePagerAdapter {
    /**
     * List of tabs
     */
    private String[] titles ={"ACCOUNT","MASS", "VOLUME", "TEMPERATURE", "MASS-VOLUME"};
    private Fragment[] fragments = {new ToolsAccountFragment(),new ToolsMassFragment(),
            new ToolsVolumeFragment(), new ToolsTemperatureFragment(), new ToolsMassVolumeFragment()};

    public ToolsAdapter(FragmentManager fm, Context c){
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
