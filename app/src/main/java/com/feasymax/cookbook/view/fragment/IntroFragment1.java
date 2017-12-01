package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;

import java.util.ArrayList;

/**
 * Created by Olya on 2017-12-01.
 */

public class IntroFragment1 extends Fragment {

    public IntroFragment1() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.intro_1, container, false);

        return view ;
    }

}
