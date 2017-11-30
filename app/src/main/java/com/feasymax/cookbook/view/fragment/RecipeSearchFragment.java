package com.feasymax.cookbook.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.model.util.Search;
import com.feasymax.cookbook.model.util.WebSearch;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.ViewTransactions;
import com.feasymax.cookbook.view.fragment.common.ShowRecipesFragment;
import com.feasymax.cookbook.view.list.RecipeListAdapter;
import com.feasymax.cookbook.view.list.RecipeListModel;

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
    public List<RecipeListModel> CustomListViewValuesArr;

    public RecipeSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_search, container, false);

        setHasOptionsMenu(true);

        noItemsLayout = view.findViewById(R.id.noItemsLayout);

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

                // Set up the buttons
                builder.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newLink = title.getText().toString();
                        Log.println(Log.INFO, "link", newLink);
                        try {
                            Log.d("title", title.getText().toString());
                            Log.d("category", String.valueOf(category.getSelectedItemPosition()));
                            Log.d("directions", directions.getText().toString());
                            Log.d("ingredients", ingredients.getText().toString());
                            Log.d("tags", tags.getText().toString());
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

                //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
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
        RecipeListModel tempValues = CustomListViewValuesArr.get(mPosition);
        Recipe recipe = Application.getRecipeFromShortInfo(tempValues);
        if (this.getActivity() instanceof RecipesActivity) {
            recipe.setCategory(Application.getUserCollection().getCategory());
            Application.setUserCurrentRecipe(recipe);
        }
        else if (this.getActivity() instanceof DiscoverActivity) {
            recipe.setCategory(Application.getDiscoverCollection().getCategory());
            Application.setDiscoverCurrentRecipe(recipe);
        }
        else {
            Log.println(Log.ERROR, "onItemClick", "Unexpected activity");
        }
        enterRecipeViewFragment();
    }

    @Override
    protected void enterRecipeViewFragment() {
        RecipeViewFragment a2Fragment = new RecipeSearchViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        ViewTransactions.getViews().add(FRAGMENT_ID);
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }
}
