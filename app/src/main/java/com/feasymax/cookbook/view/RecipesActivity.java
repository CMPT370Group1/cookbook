package com.feasymax.cookbook.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.view.fragment.RecipeViewFragment;
import com.feasymax.cookbook.view.tab.RecipeAdapter;

public class RecipesActivity extends ActivityMenuTabs {

    private static final String TAG = "RecipesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        initializeActivity();
    }

    public void initializeActivity() {
        initializeDrawer();

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new RecipeAdapter(getSupportFragmentManager(), this));

        initializeTabs();
    }

    public void initializeDrawer() {
        super.initializeDrawer();
    }

    public void initializeTabs() {
        super.initializeTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void setButtons() {
        super.setButtons();
        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
            }
        });
    }

    public void navigateFragment(int position, final String id){
        mViewPager.setCurrentItem(position, true);

        RecipeViewFragment a2Fragment = new RecipeViewFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();

    }
}
