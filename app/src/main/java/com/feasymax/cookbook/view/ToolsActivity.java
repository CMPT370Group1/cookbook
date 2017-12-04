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

    /**
     * Initialize activity components
     */
    public void initializeActivity() {
        initializeDrawer();
        setButtons();

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new ToolsAdapter(getSupportFragmentManager(), this));

        initializeTabs();
    }

    /**
     * Initialize tab layout
     */
    public void initializeTabs() {
        super.initializeTabs();
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
    }

    /**
     * Set navigation menu buttons
     */
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
