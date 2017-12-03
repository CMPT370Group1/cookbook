package com.feasymax.cookbook.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.view.tab.DiscoverAdapter;

public class DiscoverActivity extends ActivityMenuTabs {

    private static final String TAG = "DiscoverActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        initializeActivity();
    }

    /**
     * Initialize activity components
     */
    public void initializeActivity() {
        initializeDrawer();
        setButtons();

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new DiscoverAdapter(getSupportFragmentManager(), this));

        initializeTabs();
    }

    /**
     * Set navigation menu buttons
     */
    public void setButtons() {
        super.setButtons();
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
            }
        });
    }
}
