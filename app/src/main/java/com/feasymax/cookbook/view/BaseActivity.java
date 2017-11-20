package com.feasymax.cookbook.view;

import android.support.v7.app.AppCompatActivity;

import com.feasymax.cookbook.view.back_press.OnBackClickListener;

/**
 * Created by Olya on 2017-11-20.
 */

public class BaseActivity extends AppCompatActivity{

    protected OnBackClickListener onBackPressedListener;

    public void setOnBackPressedListener(OnBackClickListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }
}
