package com.feasymax.cookbook.view.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Ingredient;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.ViewTransactions;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import pl.charmas.android.tagview.TagView;

/**
 * Created by Olya on 2017-09-21.
 * The fragment corresponds to fragment_res_recipe_view (ALL tab in My Recipes activity, after
 * selecting a category and a recipe from that category)
 * It displays a recipe and gives the user a posibility to edit/delete it (My Recipes activity)
 * or save it (Discover activity).
 */

public class RecipeViewFragment extends Fragment {

    public static final String FRAGMENT_ID = "RecipeViewFragment";

    /*
     * Format to display a fraction
     */
    final DecimalFormat DF = new DecimalFormat("#.############");

    /*
     * Buttons for the layout
     */
    protected Button btnCategory;
    protected ImageButton ibtnScaleRecipe;

    /*
     * Recipe attributes
     */
    protected ImageView recipeImage;
    protected TextView recipeTitle;
    protected TagView recipeDuration;
    protected TableLayout recipeIngredients;
    protected TextView recipeDirections;
    protected TagView recipeTags;

    protected List<View> ingredientRows;

    /**
     * Current recipe displayed in the fragment (scaled)
     */
    protected Recipe currentRecipe = null;
    protected boolean isBeingScaled = false;

    /**
     * Required empty public constructor
     */
    public RecipeViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_recipe_view, container, false);

        setHasOptionsMenu(true);

        // set up all the variables for components
        btnCategory = view.findViewById(R.id.buttonCategory);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterRecipesFragment();
            }
        });

        ibtnScaleRecipe = view.findViewById(R.id.ibScale);
        ibtnScaleRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (View tr: ingredientRows) {
                    EditText quantity = tr.findViewById(R.id.quantity);
                    Spinner unit = tr.findViewById(R.id.unit);

                    if (!isBeingScaled) {
                        quantity.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        unit.setEnabled(true);
                        ibtnScaleRecipe.setBackgroundColor(getResources().getColor(R.color.colorButtonInactive));
                        Drawable icon = getResources().getDrawable(android.R.drawable.ic_delete);
                        ibtnScaleRecipe.setImageDrawable(icon);
                    }
                    else {
                        quantity.setInputType(InputType.TYPE_NULL);
                        unit.setEnabled(false);
                        ibtnScaleRecipe.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        Drawable icon = getResources().getDrawable(R.drawable.resize_icon);
                        ibtnScaleRecipe.setImageDrawable(icon);
                    }
                }
                isBeingScaled = !isBeingScaled;
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
        Log.println(Log.INFO, "In RecipeViewFragment", "Current recipe: " + currentRecipe.toString());
        if (currentRecipe.getImage() != null) {
            recipeImage.setImageBitmap(currentRecipe.getImage());
        }
        else {
            recipeImage.setImageDrawable(getResources().getDrawable(R.drawable.no_image420, null));
        }

        recipeTitle.setText(currentRecipe.getTitle());
        recipeDirections.setText(currentRecipe.getDirections());

        TagView.Tag[] duration = { new TagView.Tag(displayDuration(currentRecipe.getDuration()),
                Color.parseColor("#808080")) };
        recipeDuration.setTags(duration, " ");

        ingredientRows = new LinkedList<>();
        // Fill the table layout recipeIngredients with rows, one for each ingredient
        if (currentRecipe.getIngredients() != null) {
            Log.println(Log.INFO, "In RecipeViewFragment", "Ingredients: " + currentRecipe.getIngredients().toString());

            for (Ingredient ingredient : currentRecipe.getIngredients()) {
                View tr = inflater.inflate(R.layout.ingredient_view_layout, null, false);

                // Fill the row with ingredient info
                EditText quantity = tr.findViewById(R.id.quantity);
                quantity.setText(DF.format(ingredient.getQuantity()));
                quantity.setInputType(InputType.TYPE_NULL);

                Spinner unit = tr.findViewById(R.id.unit);
                unit.setEnabled(false);

                // correctly display ingredient's units in the dropdown spinner
                setIngredientAdapter(ingredient.getUnit(), unit);

                TextView name = tr.findViewById(R.id.name);
                name.setText(ingredient.getName());

                // Add row to TableLayout.
                recipeIngredients.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                ingredientRows.add(tr);
            }
        }
        else {
            Log.println(Log.INFO, "In RecipeViewFragment", "Ingredients: null");
        }

        // Set up the recipe tags
        LinkedList<TagView.Tag> tags = new LinkedList<>();
        if (currentRecipe.getTags() != null) {
            Log.println(Log.INFO, "In RecipeViewFragment", "Tags: " + currentRecipe.getTags().toString());
            for (String content : currentRecipe.getTags()) {
                tags.add(new TagView.Tag(content, Color.parseColor("#ff4081"))); // color is colorAccent
            }
            recipeTags.setTags(tags, " ");
        }
        else {
            Log.println(Log.INFO, "In RecipeViewFragment", "Tags: null");
        }

        return view ;
    }

    protected void setIngredientAdapter(int ingredientUnit, Spinner unit) {
        if (ingredientUnit < 5) {
            ArrayAdapter adapterUnit = ArrayAdapter.createFromResource(this.getContext(),
                    R.array.mass_units, R.layout.spinner_item_center);
            adapterUnit.setDropDownViewResource(R.layout.spinner_dropdown_item);
            unit.setAdapter(adapterUnit);
            unit.setSelection(ingredientUnit);
        }
        else {
            ArrayAdapter adapterUnit = ArrayAdapter.createFromResource(this.getContext(),
                    R.array.volume_units, R.layout.spinner_item_center);
            adapterUnit.setDropDownViewResource(R.layout.spinner_dropdown_item);
            unit.setAdapter(adapterUnit);
            unit.setSelection(ingredientUnit - 5);
        }
    }

    /**
     * Go back to all recipes in a category
     */
    protected void enterRecipesFragment() {
        RecipesFragment a2Fragment = new RecipesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        ViewTransactions.getViews().add(FRAGMENT_ID);
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    /**
     * Go back to all recipes in a category
     */
    protected void enterRecipeEditFragment() {
        RecipeEditFragment a2Fragment = new RecipeEditFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        ViewTransactions.getViews().add(FRAGMENT_ID);
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    /**
     * Go back to all recipes in a category
     */
    protected void enterPrevFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // Store the Fragment in stack
        ViewTransactions.getViews().add(FRAGMENT_ID);
        transaction.addToBackStack(null);
        transaction.detach(this).commit();
    }



    /**
     * Display recipe preparation duration in hours and minutes from given minutes
     * @param duration preparation duration in minutes
     * @return string representation of duration in hours and minutes
     */
    protected String displayDuration(int duration) {
        if (duration == 0) {
            return "unspecified";
        }
        int hours = duration / 60;
        int min = duration % 60;
        if (hours == 0) {
            return "Duration: " + min + " min";
        }
        else {
            return "Duration: " + hours + " h " + min + " min";
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (getActivity() instanceof RecipesActivity) {
            inflater.inflate(R.menu.menu_recipe_view_rec, menu);
        }
        else if (getActivity() instanceof DiscoverActivity) {
            inflater.inflate(R.menu.menu_recipe_view_disc, menu);
        }
        else {
            Log.println(Log.ERROR, "MENU","unexpected activity");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Log.println(Log.INFO, "MENU","action_info has clicked");
                return true;
            case R.id.action_recipe_edit:
                Log.println(Log.INFO, "MENU","action_recipe_edit has clicked");
                editRecipe();
                return true;
            case R.id.action_recipe_delete:
                Log.println(Log.INFO, "MENU","action_recipe_delete has clicked");
                deleteRecipe();
                return true;
            case R.id.action_recipe_add:
                Log.println(Log.INFO, "MENU","action_recipe_add has clicked");
                addRecipe();
                return true;
            default:
                break;
        }
        return false;
    }

    protected void editRecipe() {
        if (getActivity() instanceof RecipesActivity) {
            enterRecipeEditFragment();
            //Application.editRecipe(Application.getDiscoverCurrentRecipe());
        }
    }

    protected void deleteRecipe() {
        if (getActivity() instanceof RecipesActivity) {
            Application.deleteRecipe(Application.getUserCurrentRecipe());
            enterRecipesFragment();
        }
    }

    protected void addRecipe() {
        if (getActivity() instanceof DiscoverActivity) {
            if (Application.isUserSignedIn()) {
                Application.addNewRecipe(true, Application.getDiscoverCurrentRecipe(), false, null, -1);
            } else {
                Log.println(Log.INFO, "addRecipe","user is not signed in");
            }
        }

    }
}
