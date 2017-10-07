package com.olya.cookbook.view;

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
import com.olya.cookbook.view.fragment.RecipesFragment;
import com.olya.cookbook.view.tab.MyAdapter;

public class RecipesActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    public static ViewPager mViewPager;
    protected MyAdapter mPageAdapter;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;

    private Button btnRecipes;
    private Button btnDiscover;
    private Button btnTools;



    private static final String TAG = "RecipeActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

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


        /*

         */
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), this));
        //FragmentManager fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.vp_tabs, new RecipesFragment())
                .commit();

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

        setButtons();

//=======================================================================================================
//        bottomBar =(BottomBar)findViewById(R.id.bottombar);
//        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
//            Fragment fragment = null;
//            @Override
//            public void onTabSelected(@IdRes int tabId) {
//                if (tabId == R.id.tab_home){
//                    fragment = new CategoriesFragment();
//                }else if (tabId == R.id.tab_explore){
//                    fragment = new AddFragment();
//                }else if (tabId ==R.id.tab_chat){
//                    fragment = new SearchFragment();
//                }else if (tabId==R.id.tab_friends){
//                    fragment = new LinksFragment();
//                }
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.content, fragment)
//                        .commit();
//            }
//        });

 /*       btnBreakfast =(ImageButton) findViewById(R.id.ib_breakfast);
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            Fragment fragment = new AddFragment();
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, fragment)
                        .commit();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setButtons() {
        btnRecipes = (Button) findViewById(R.id.button1);
        btnDiscover = (Button) findViewById(R.id.button2);
        btnTools = (Button) findViewById(R.id.button3);

        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);
                //startActivity(intent);
            }
        });
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), DiscoverActivity.class);
                //startActivity(intent);
            }
        });
        btnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), ToolsActivity.class);
                //startActivity(intent);
            }
        });

    }
}
