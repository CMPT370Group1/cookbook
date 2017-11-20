package com.feasymax.cookbook.view.fragment.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feasymax.cookbook.view.BaseActivity;
import com.feasymax.cookbook.view.back_press.BaseBackPressedListener;

/**
 * Created by Olya on 2017-11-19.
 */

public abstract class OnBackPressFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BaseActivity activity = (BaseActivity) getActivity();

        ((BaseActivity)activity).setOnBackPressedListener(new BaseBackPressedListener(activity));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
