package com.olya.cookbook.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.support.design.widget.TabLayout;

import com.olya.cookbook.R;
import com.olya.cookbook.view.tab.RecipeAdapter;
import com.olya.cookbook.view.tab.ToolsAdapter;

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
        btnRecipes.setEnabled(false);
    }
}
