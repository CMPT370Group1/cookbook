package com.feasymax.cookbook.view.fragment;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.ViewTransactions;
import com.feasymax.cookbook.view.fragment.common.ShowWebpagesFragment;
import com.feasymax.cookbook.view.list.LinksListAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipeLinksFragment extends ShowWebpagesFragment {

    public static final String FRAGMENT_ID = "RecipeLinksFragment";

    private Button btnAddLink;
    private RelativeLayout noItemsLayout;

    ListView list;
    LinksListAdapter adapter;
    public RecipeLinksFragment CustomListView = null;
    public List<WebpageInfo> CustomListViewValuesArr;

    private String m_Text = "";

    public RecipeLinksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_links, container, false);

        setHasOptionsMenu(true);

        btnAddLink = view.findViewById(R.id.buttonAddLink);
        btnAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add New Web-link");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected and hint
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setHint("URL");
                // Set the margins for input edit text
                FrameLayout container = new FrameLayout(getContext());
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int marginPxl = Graphics.dpToPx(getResources(), (int) getResources().
                        getDimension(R.dimen.tiny_margin));
                params.setMargins(marginPxl, 0, marginPxl, 0);
                input.setLayoutParams(params);
                container.addView(input);
                builder.setView(container);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        Log.println(Log.INFO, "link", m_Text);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        noItemsLayout = view.findViewById(R.id.noItemsLayout);
        CustomListView = this;
        list = view.findViewById( R.id.list );

        //openWebpage = view.findViewById(R.id.buttonOpen1);
        /*openWebpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterWebpageViewFragment();
            }
        });*/

        return view;
    }

    @Override
    public void setListData() {
        //TODO
        WebpageInfo link1 = new WebpageInfo("Bread", "https://www.thespruce.com/super-easy-bread-for-beginners-428108",
                "thespruce.com", "Description Description Description Description Description Description Description Description");
        WebpageInfo link2 = new WebpageInfo("Bread2", "http://allrecipes.com/recipes/156/bread/",
                "allrecipes.com", "Description2 Description2 Description2 Description2 Description2 Description2 Description2");
        CustomListViewValuesArr = new LinkedList<>();
        CustomListViewValuesArr.add(link1);
        CustomListViewValuesArr.add(link2);
    }

    public void setAdapter() {
        // Create Custom Adapter
        Resources res = getResources();
        adapter = null;
        adapter = new LinksListAdapter( this, CustomListViewValuesArr, res );
        list.setAdapter( adapter );
    }

    public void onItemClick(int mPosition)
    {
        WebpageInfo tempValues = CustomListViewValuesArr.get(mPosition);
        Application.getDiscoverCollection().setWebsearchResult(tempValues.getUrl());
        enterWebpageViewFragment();
    }


    @Override
    protected void enterWebpageViewFragment() {
        WebPageViewFragment a2Fragment = new WebPageViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        ViewTransactions.getViews().add(FRAGMENT_ID);
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Log.println(Log.INFO, "MENU","action_info has clicked");
                return true;
            default:
                Log.println(Log.INFO, "MENU","error");
                break;
        }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        CustomListViewValuesArr = null;
        setListData();
        if (CustomListViewValuesArr.size() != 0) {
            noItemsLayout.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            setAdapter();
        }
    }
}
