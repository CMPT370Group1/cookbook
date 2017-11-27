package com.feasymax.cookbook.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 */

public abstract class ActivityMenuTabs extends AppCompatActivity {

    protected TabLayout mTabLayout;
    public static ViewPager mViewPager;

    protected DrawerLayout mDrawer;
    protected Toolbar mToolbar;

    protected Button btnRecipes;
    protected Button btnDiscover;
    protected Button btnTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initializeDrawer() {
        /*

         */
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        /*

         */
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        setButtons();
    }

    public void initializeTabs() {
        mTabLayout = (TabLayout) findViewById(R.id.stl_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {}

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

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
                finish();
            }
        });
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DiscoverActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ToolsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean onBackPressed(FragmentManager fm) {
        if (fm != null) {
            Log.println(Log.INFO, "fm fragments", fm.getFragments().toString());
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                return true;
            }

            List<Fragment> fragList = fm.getFragments();
            if (fragList != null && fragList.size() > 0) {
                for (Fragment frag : fragList) {
                    if (frag == null) {
                        continue;
                    }
                    if (frag.isVisible()) {
                        if (onBackPressed(frag.getChildFragmentManager())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.println(Log.INFO, "onBackPressed", "activity");
        Log.println(Log.INFO, "fragments", ViewTransactions.getViews().toString());
        FragmentManager fm = getSupportFragmentManager();
        if (onBackPressed(fm)) {
            return;
        }
        super.onBackPressed();
    }

}
