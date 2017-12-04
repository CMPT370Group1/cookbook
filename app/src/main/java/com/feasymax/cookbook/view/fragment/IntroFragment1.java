package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feasymax.cookbook.R;

/**
 * Created by Olya on 2017-12-02.
 */

public class IntroFragment1 extends Fragment{

    public IntroFragment1() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.intro_1, container, false);
        return view;
    }
}
