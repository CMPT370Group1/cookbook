package com.feasymax.cookbook.view.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.model.util.WebSearch;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.fragment.common.ShowWebpagesFragment;
import com.feasymax.cookbook.view.list.LinksListAdapter;

/**
 * Created by Olya on 2017-09-21.
 * Fragment to display saved links in the user's collection
 */

public class RecipeLinksFragment extends ShowWebpagesFragment {

    public static final String FRAGMENT_ID = "RecipeLinksFragment";

    /**
     * Layout elements
     */
    private Button btnAddLink;
    private SwipeRefreshLayout swipeRefreshLayout;
    protected LinksListAdapter adapter;

    private String newLink = "";

    /**
     * Required empty public constructor
     */
    public RecipeLinksFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_links, container, false);

        setHasOptionsMenu(true);

        // Set up button to add new link to user's collection
        btnAddLink = view.findViewById(R.id.buttonAddLink);
        btnAddLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The dialog to input the link's URL
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
                // Set up the buttons for the dialog
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newLink = input.getText().toString();
                        Log.println(Log.INFO, "link", newLink);
                        try {
                            WebpageInfo newWebpageInfo = WebSearch.parsePageHeaderInfo(newLink);
                            if (newWebpageInfo != null) {
                                if (Application.addLink(newWebpageInfo)) {
                                    CustomListViewValuesArr = Application.getUserCollection().getLinks();
                                    if (CustomListViewValuesArr.size() != 0) {
                                        noItemsLayout.setVisibility(View.GONE);
                                        list.setVisibility(View.VISIBLE);
                                        setAdapter();
                                    }
                                }
                                else {
                                    Toast.makeText(getContext(), "The link has already been " +
                                            "added before", Toast.LENGTH_SHORT).show();
                                }


                            }
                            else {
                                throw new Exception("Wrong link format");
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
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

        // Set up the refresh layout to obtain the list of links from the database on refresh
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d("Swipe refresh", "onRefresh called from SwipeRefreshLayout");

                        // Perform the actual data-refresh operation
                        if (getActivity() instanceof RecipesActivity) {
                            Application.getUserCollection().updateLinks();
                        }
                        // Update the listview
                        onResume();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        // set up the listview
        noItemsLayout = view.findViewById(R.id.noItemsLayout);
        CustomListView = this;
        list = view.findViewById( R.id.list );

        return view;
    }

    /**
     * Get the list of links
     */
    @Override
    public void setListData() {
        CustomListViewValuesArr = Application.getUserCollection().getLinks();
    }

    /**
     * Set adapter for the listview to display web-pages
     */
    public void setAdapter() {
        // Create Custom Adapter
        Resources res = getResources();
        adapter = null;
        adapter = new LinksListAdapter(this, CustomListViewValuesArr, res);
        list.setAdapter(adapter);
    }

    /**
     * Open clicked web-page in webview
     * @param mPosition
     */
    public void onItemClick(int mPosition)
    {
        WebpageInfo tempValues = CustomListViewValuesArr.get(mPosition);
        Application.getUserCollection().setCurLink(tempValues);
        Application.getDiscoverCollection().setWebsearchResult(tempValues.getUrl());
        enterWebpageViewFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_fragment, menu);
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
