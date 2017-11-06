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

    public void initializeActivity() {
        initializeDrawer();

        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new DiscoverAdapter(getSupportFragmentManager(), this));

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
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.closeDrawers();
            }
        });
    }
}
