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

public class IntroFragment6 extends Fragment{

    public IntroFragment6() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.intro_6, container, false);
        return view;
    }
}