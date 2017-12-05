package com.feasymax.cookbook.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.util.Search;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.fragment.common.ShowRecipesFragment;
import com.feasymax.cookbook.view.list.RecipeListAdapter;
import com.feasymax.cookbook.model.entity.RecipeShortInfo;

import java.util.List;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipeSearchFragment extends ShowRecipesFragment {

    public static final String FRAGMENT_ID = "RecipeSearchFragment";

    private SearchView searchView;
    private RelativeLayout noItemsLayout;
    private Button btnAdvancedSearch;

    ListView list;
    RecipeListAdapter adapter;
    public RecipeSearchFragment CustomListView = null;
    public List<RecipeShortInfo> CustomListViewValuesArr;

    /**
     * Required empty public constructor
     */
    public RecipeSearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_search, container, false);

        setHasOptionsMenu(true);

        noItemsLayout = view.findViewById(R.id.noItemsLayout);

        // set up search view
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                int activity = 0;
                if (getActivity() instanceof RecipesActivity) {
                    activity = 0;
                }
                if (getActivity() instanceof DiscoverActivity) {
                    activity = 1;
                }
                else {
                    Log.println(Log.ERROR, "search", "unexpected activity");
                }

                // get search results from keywords
                CustomListViewValuesArr = Search.getSearchResults(s, activity);
                if (CustomListViewValuesArr.size() != 0) {
                    noItemsLayout.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    setAdapter();
                    // hide keyboard
                    InputMethodManager imm = (InputMethodManager)getActivity().
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                else {
                    noItemsLayout.setVisibility(View.VISIBLE);
                    list.setVisibility(View.GONE);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        // set up dialog for advanced search
        btnAdvancedSearch = view.findViewById(R.id.buttonAdvancedSearch);
        btnAdvancedSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("ADVANCED SEARCH");

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_advanced_search, null);
                builder.setView(dialogView);

                // Set up the input fields
                final EditText title = dialogView.findViewById(R.id.title);
                final Spinner category = dialogView.findViewById(R.id.category);
                final EditText directions = dialogView.findViewById(R.id.directions);
                final EditText ingredients = dialogView.findViewById(R.id.ingredients);
                final EditText tags = dialogView.findViewById(R.id.tags);
                final CheckBox checkBox = dialogView.findViewById(R.id.checkbox);

                // Set up the buttons
                builder.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchView.setQuery("", false);

                        String newLink = title.getText().toString();
                        Log.println(Log.INFO, "link", newLink);
                        try {
                            String titleString = title.getText().toString();
                            int categoryInt = category.getSelectedItemPosition() - 1;
                            String directionsString = directions.getText().toString();
                            String ingredientsString = ingredients.getText().toString();
                            String tagsString = tags.getText().toString();
                            int activity = 0;
                            if (getActivity() instanceof RecipesActivity) {
                                activity = 0;
                            }
                            if (getActivity() instanceof DiscoverActivity) {
                                activity = 1;
                            }
                            else {
                                Log.println(Log.ERROR, "search", "unexpected activity");
                            }
                            boolean isIncludingAllAttributes = checkBox.isChecked();

                            CustomListViewValuesArr = Search.getAdvancedSearchResults(titleString,
                                    categoryInt, directionsString, ingredientsString, tagsString,
                                    activity, isIncludingAllAttributes);
                            if (CustomListViewValuesArr.size() != 0) {
                                noItemsLayout.setVisibility(View.GONE);
                                list.setVisibility(View.VISIBLE);
                                setAdapter();
                                // hide keyboard
                                InputMethodManager imm = (InputMethodManager)getActivity().
                                        getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                            }
                            else {
                                noItemsLayout.setVisibility(View.VISIBLE);
                                list.setVisibility(View.GONE);
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
                builder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });


                final AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        title.setText("");
                        category.setSelection(0);
                        directions.setText("");
                        ingredients.setText("");
                        tags.setText("");
                    }
                });
            }
        });

        CustomListView = this;
        list = view.findViewById( R.id.list );
        setAdapter();

        return view ;
    }

    @Override
    public void setListData() {}

    public void setAdapter() {
        // Create Custom Adapter
        Resources res = getResources();
        adapter = null;
        adapter = new RecipeListAdapter( this, CustomListViewValuesArr, res );
        list.setAdapter( adapter );

    }

    public void onItemClick(int mPosition)
    {
        RecipeShortInfo tempValues = CustomListViewValuesArr.get(mPosition);
        Recipe recipe = Application.getRecipeFromShortInfo(tempValues);
        if (this.getActivity() instanceof RecipesActivity) {
            recipe.setCategory(Application.getUserCollection().getCategory());
            Application.getUserCollection().setCurRecipe(recipe);
        }
        else if (this.getActivity() instanceof DiscoverActivity) {
            recipe.setCategory(Application.getDiscoverCollection().getCategory());
            Application.getDiscoverCollection().setCurRecipe(recipe);
        }
        else {
            Log.println(Log.ERROR, "onItemClick", "Unexpected activity");
        }
        enterRecipeViewFragment();
    }

    protected void enterRecipeViewFragment() {
        RecipeSearchViewFragment a2Fragment = new RecipeSearchViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        list.setAdapter(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
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
}
