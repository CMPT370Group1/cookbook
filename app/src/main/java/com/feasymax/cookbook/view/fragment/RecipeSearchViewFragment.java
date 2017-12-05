package com.feasymax.cookbook.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.view.RecipesActivity;

/**
 * Created by Olya on 2017-09-21.
 * The fragment corresponds to fragment_res_recipe_view (ALL tab in My Recipes activity, after
 * selecting a category and a recipe from that category)
 * It displays a recipe and gives the user a posibility to edit/delete it (My Recipes activity)
 * or save it (Discover activity).
 */

public class RecipeSearchViewFragment extends RecipeViewFragment {

    public static final String FRAGMENT_ID = "RecipeSearchViewFragment";

    /**
     * Required empty public constructor
     */
    public RecipeSearchViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // set up all the variables for components
        btnCategory = view.findViewById(R.id.buttonCategory);
        btnCategory.setText("Search Results");
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterSearchFragment();
            }
        });

        return view ;
    }

    protected void enterSearchFragment() {
        RecipeSearchFragment a2Fragment = new RecipeSearchFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        recipeImage.setImageBitmap(null);
        recipeImage.destroyDrawingCache();
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    protected void deleteRecipe() {
        if (getActivity() instanceof RecipesActivity) {
            Application.deleteRecipe(Application.getUserCollection().getCurRecipe());
            enterSearchFragment();
            Toast.makeText(getContext(), "The recipe has been deleted",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
