package com.feasymax.cookbook.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.view.fragment.IntroFragment1;
import com.feasymax.cookbook.view.fragment.IntroFragment2;
import com.feasymax.cookbook.view.fragment.IntroFragment3;
import com.feasymax.cookbook.view.fragment.IntroFragment4;
import com.feasymax.cookbook.view.fragment.IntroFragment5;
import com.feasymax.cookbook.view.fragment.IntroFragment6;
import com.feasymax.cookbook.view.fragment.IntroFragment7;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by Olya on 2017-12-01.
 * Intro tutorial activity
 */

public class IntroActivity extends AppIntro {

    private static final String TAG = "IntroActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add all slides to the tutorial
        addSlide(new IntroFragment1());
        addSlide(new IntroFragment2());
        addSlide(new IntroFragment3());
        addSlide(new IntroFragment4());
        addSlide(new IntroFragment5());
        addSlide(new IntroFragment6());
        addSlide(new IntroFragment7());

        // Show both Skip and Done buttons
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Go to main activity when users tap on Skip button.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Go to main activity when users tap on Done button.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
