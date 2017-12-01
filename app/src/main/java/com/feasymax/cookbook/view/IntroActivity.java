package com.feasymax.cookbook.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.feasymax.cookbook.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by Olya on 2017-12-01.
 */

public class IntroActivity extends AppIntro {

    private static final String TAG = "IntroActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Welcome to CookBook", "Sign in or register to start " +
                "your collection of recipes", R.drawable.intro1, getResources().getColor(R.color.colorBackgroundGrey)));
        addSlide(AppIntroFragment.newInstance("Your collection", "You can add new recipes, view, " +
                "modify or delete existing recipes, and also save links to your favourite web-sites!",
                R.drawable.intro2, getResources().getColor(R.color.colorBackgroundGrey)));
        addSlide(AppIntroFragment.newInstance("Discover collection", "Save recipes from web or other " +
                        "users' collections",
                R.drawable.intro3, getResources().getColor(R.color.colorBackgroundGrey)));
        addSlide(AppIntroFragment.newInstance("Useful tools", "Use unit converters to make cooking easier!",
                R.drawable.intro4, getResources().getColor(R.color.colorBackgroundGrey)));

        // OPTIONAL METHODS
        // Override bar/separator color.
        //setBarColor(Color.parseColor("#3F51B5"));
        //setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }


}
