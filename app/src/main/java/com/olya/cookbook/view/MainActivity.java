package com.olya.cookbook.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.olya.cookbook.R;
import com.olya.cookbook.model.Application;

public class MainActivity extends ActivityMenu {

    private static final String TAG = "MainActivity";
    private FloatingActionButton btnFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeActivity();
    }

    @Override
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

        btnFab = (FloatingActionButton) findViewById(R.id.fab);
        if (Application.isUserSignedIn()) {
            btnFab.setEnabled(false);
            btnFab.setBackgroundTintList(getResources().getColorStateList(R.color.colorButtonInactive));
        }
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
        });

    }
}
