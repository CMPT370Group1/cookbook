package com.feasymax.cookbook.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.view.tab.ToolsAdapter;

public class ToolsActivity extends ActivityMenuTabs {

    private static final String TAG = "ToolsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        initializeActivity();
    }

    public void initializeActivity() {
        initializeDrawer();

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new ToolsAdapter(getSupportFragmentManager(), this));

        initializeTabs();
    }

    public void initializeDrawer() {
        super.initializeDrawer();
    }

    public void initializeTabs() {
        super.initializeTabs();
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
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
        btnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
            }
        });
    }
}
