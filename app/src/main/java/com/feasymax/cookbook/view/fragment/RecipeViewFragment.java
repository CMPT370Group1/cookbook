package com.feasymax.cookbook.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
 */

public class RecipeViewFragment extends Fragment{

    final DecimalFormat DF = new DecimalFormat("#.############");

    private Button btnCategory;

    private ImageView recipeImage;
    private TextView recipeTitle;
    private TagView recipeDuration;
    private TableLayout recipeIngredients;
    private TextView recipeDirections;
    private TagView recipeTags;

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

        btnCategory = (Button) view.findViewById(R.id.buttonCategory);
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
            View tr = inflater.inflate(R.layout.ingredient_layout, null, false);

            // Fill the row with ingredient info
            EditText quantity = tr.findViewById(R.id.quantity);
            quantity.setText(DF.format(ingredient.getQuantity()));

            TextView unit = tr.findViewById(R.id.unit);
            unit.setText(ingredient.getUnit());

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
     *
     */
    private void enterRecipesFragment() {
        RecipesFragment a2Fragment = new RecipesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

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
