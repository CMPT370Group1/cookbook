package com.feasymax.cookbook.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.DiscoverCollection;

/**
 * The activity for the main screen of application.
 */
public class MainActivity extends ActivityMenu {

    private static final String TAG = "MainActivity";
    private FloatingActionButton btnFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Application.getDiscoverCollection() == null) {
            Application.setDiscoverCollection(new DiscoverCollection());
        }

        Log.d("isUserSignedIn", Application.isUserSignedIn() + "");
        if (!Application.isUserSignedIn()) {
            int userID = Application.readSession(this);
            Log.d("readSession", userID + "");
            if (userID != -1) {
                Application.userSignIn(userID);
            }
        }

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
