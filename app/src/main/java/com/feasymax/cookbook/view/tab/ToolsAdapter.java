package com.feasymax.cookbook.view.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feasymax.cookbook.view.fragment.ToolsAccountFragment;
import com.feasymax.cookbook.view.fragment.ToolsMassFragment;
import com.feasymax.cookbook.view.fragment.ToolsMassVolumeFragment;
import com.feasymax.cookbook.view.fragment.ToolsTemperatureFragment;
import com.feasymax.cookbook.view.fragment.ToolsVolumeFragment;

/**
 * Created by Olya on 2017-09-21.
 */

public class ToolsAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] titles ={"ACCOUNT","MASS", "VOLUME", "TEMPERATURE", "MASS-VOLUME"};

    public ToolsAdapter(FragmentManager fm, Context c){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag= null;

        if(position ==0){
            frag = new ToolsAccountFragment();
        }else if(position == 1){
            frag = new ToolsMassFragment();
        }else if(position == 2){
            frag = new ToolsVolumeFragment();
        }else if(position == 3){
            frag = new ToolsTemperatureFragment();
        }else if(position == 4){
            frag = new ToolsMassVolumeFragment();
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