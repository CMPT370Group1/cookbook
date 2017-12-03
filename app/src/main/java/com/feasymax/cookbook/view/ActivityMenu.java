package com.feasymax.cookbook.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;

/**
 * Created by Olya on 2017-10-08.
 * Abstruct class for activities with navigation menu
 */

public abstract class ActivityMenu extends AppCompatActivity {

    /**
     * Navigation menu buttons
     */
    protected Button btnRecipes;
    protected Button btnDiscover;
    protected Button btnTools;

    protected DrawerLayout mDrawer;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initialize activity components
     */
    public void initializeActivity() {
        initializeDrawer();
        setButtons();
    }

    /**
     * Initialize navigation menu drawer
     */
    public void initializeDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // App Info was selected
        if (id == R.id.action_appinfo) {
            Log.d("Menu", "action_appinfo was pressed");
            Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set navigation menu buttons
     */
    public void setButtons() {
        btnRecipes = (Button) findViewById(R.id.button1);
        btnDiscover = (Button) findViewById(R.id.button2);
        btnTools = (Button) findViewById(R.id.button3);

        if (!Application.isUserSignedIn()) {
            btnRecipes.setEnabled(false);
            btnRecipes.setBackground(getDrawable(R.drawable.button_recipes_inactive));
        }

        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);
                startActivity(intent);
            }
        });
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiscoverActivity.class);
                startActivity(intent);
            }
        });
        btnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ToolsActivity.class);
                startActivity(intent);
            }
        });

    }
}
