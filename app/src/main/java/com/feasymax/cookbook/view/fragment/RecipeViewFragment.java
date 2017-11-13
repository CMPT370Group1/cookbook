package com.feasymax.cookbook.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Ingredient;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;

import java.text.DecimalFormat;
import java.util.LinkedList;

import pl.charmas.android.tagview.TagView;

/**
 * Created by Olya on 2017-09-21.
 * The fragment corresponds to fragment_res_recipe_view (ALL tab in My Recipes activity, after
 * selecting a category and a recipe from that category)
 * It displays a recipe and gives the user a posibility to edit/delete it (My Recipes activity)
 * or save it (Discover activity).
 */

public class RecipeViewFragment extends Fragment{

    /*
     * Format to display a fraction
     */
    final DecimalFormat DF = new DecimalFormat("#.############");

    /*
     * Button to go back to the recipes in a category
     */
    private Button btnCategory;

    /*
     * Recipe attributes
     */
    private ImageView recipeImage;
    private TextView recipeTitle;
    private TagView recipeDuration;
    private TableLayout recipeIngredients;
    private TextView recipeDirections;
    private TagView recipeTags;

    /**
     * Current recipe displayed in the fragment (scaled)
     */
    private Recipe currentRecipe = null;


    /**
     * Required empty public constructor
     */
    public RecipeViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_recipe_view, container, false);

        // set up all the variables for components
        btnCategory = view.findViewById(R.id.buttonCategory);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterRecipesFragment();
            }
        });

        recipeImage = view.findViewById(R.id.recipeImage);
        recipeTitle = view.findViewById(R.id.recipeTitle);
        recipeDuration = view.findViewById(R.id.recipeDuration);
        recipeIngredients = view.findViewById(R.id.recipeIngredients);
        recipeDirections = view.findViewById(R.id.recipeDirections);
        recipeTags = view.findViewById(R.id.recipeTags);

        // Get the correct recipe to display depending on which activity is active
        if (this.getActivity() instanceof RecipesActivity) {
            currentRecipe = Application.getUserCurrentRecipe();
        }
        else if (this.getActivity() instanceof DiscoverActivity) {
            currentRecipe = Application.getDiscoverCurrentRecipe();
        }
        else {
            Log.println(Log.ERROR, "onItemClick", "Unexpected activity");
        }

        // Set up the recipe info
        recipeImage.setImageBitmap(currentRecipe.getImage());
        recipeTitle.setText(currentRecipe.getTitle());
        recipeDirections.setText(currentRecipe.getDirections());

        TagView.Tag[] duration = { new TagView.Tag(displayDuration(currentRecipe.getDuration()),
                Color.parseColor("#808080")) };
        recipeDuration.setTags(duration, " ");


        // Fill the table layout recipeIngredients with rows, one for each ingredient
        for (Ingredient ingredient : currentRecipe.getIngredients()) {
            View tr = inflater.inflate(R.layout.ingredient_view_layout, null, false);

            // Fill the row with ingredient info
            EditText quantity = tr.findViewById(R.id.quantity);
            quantity.setText(DF.format(ingredient.getQuantity()));

            Spinner unit = tr.findViewById(R.id.unit);
            // correctly display recipe categories in the dropdown spinner
            if (ingredient.getUnit() < 5) {
                ArrayAdapter adapterUnit = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.mass_units, R.layout.spinner_item_center);
                adapterUnit.setDropDownViewResource(R.layout.spinner_dropdown_item);
                unit.setAdapter(adapterUnit);
                unit.setSelection(ingredient.getUnit());
            }
            else {
                ArrayAdapter adapterUnit = ArrayAdapter.createFromResource(this.getContext(),
                        R.array.volume_units, R.layout.spinner_item_center);
                adapterUnit.setDropDownViewResource(R.layout.spinner_dropdown_item);
                unit.setAdapter(adapterUnit);
                unit.setSelection(ingredient.getUnit() - 5);
            }


            TextView name = tr.findViewById(R.id.name);
            name.setText(ingredient.getName());

            // Add row to TableLayout.
            recipeIngredients.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }

        // Set up the recipe tags
        LinkedList<TagView.Tag> tags = new LinkedList<>();
        for (String content : currentRecipe.getTags()) {
            tags.add(new TagView.Tag(content, Color.parseColor("#ff4081"))); // color is colorAccent
        }
        recipeTags.setTags(tags, " ");

        return view ;
    }

    /**
     * Go back to all recipes in a category
     */
    private void enterRecipesFragment() {
        RecipesFragment a2Fragment = new RecipesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    /**
     * Display recipe preparation duration in hours and minutes from given minutes
     * @param duration preparation duration in minutes
     * @return string representation of duration in hours and minutes
     */
    private String displayDuration(int duration) {
        int hours = duration / 60;
        int min = duration % 60;
        if (hours == 0) {
            return "Duration: " + min + " min";
        }
        else {
            return "Duration: " + hours + " h " + min + " min";
        }
    }
}
