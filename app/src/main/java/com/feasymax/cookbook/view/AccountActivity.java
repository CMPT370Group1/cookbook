package com.feasymax.cookbook.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.feasymax.cookbook.R;

/**
 * Created by Kristine
 * Activity used for log in
 */
public class AccountActivity extends ActivityMenu {

    private static final String TAG = "AccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initializeActivity();

    }

    public void initializeActivity() {
        super.initializeActivity();
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
    }
}
