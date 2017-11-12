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
    private TableLayout recipeIngredients;
    private TextView recipeDirections;
    private TagView recipeTags;

    private Recipe currentRecipe = null;


    public RecipeViewFragment() {
        // Required empty public constructor
    }
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
        recipeIngredients = view.findViewById(R.id.recipeIngredients);
        recipeDirections = view.findViewById(R.id.recipeDirections);
        recipeTags = view.findViewById(R.id.recipeTags);

        if (this.getActivity() instanceof RecipesActivity) {
            currentRecipe = Application.getUserCurrentRecipe();
        }
        else if (this.getActivity() instanceof DiscoverActivity) {
            currentRecipe = Application.getDiscoverCurrentRecipe();
        }
        else {
            Log.println(Log.ERROR, "onItemClick", "Unexpected activity");
        }

        recipeImage.setImageBitmap(currentRecipe.getImage());
        recipeTitle.setText(currentRecipe.getTitle());
        recipeDirections.setText(currentRecipe.getDirections());

        for (Ingredient ingredient : currentRecipe.getIngredients()) {
            TableRow tr = new TableRow(this.getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 20, 0);

            /* Create a Button to be the row-content. */
            TextView quantity = new TextView(this.getContext());
            quantity.setText(DF.format(ingredient.getQuantity()));
            tr.addView(quantity, params);
            //ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) quantity.getLayoutParams();
            //mlp.setMargins(50, 0, 0, 0);

            TextView unit = new TextView(this.getContext());
            unit.setText(ingredient.getUnit());
            tr.addView(unit, params);

            TextView name = new TextView(this.getContext());
            name.setText(ingredient.getName());
            tr.addView(name, params);

            /* Add row to TableLayout. */
            //tr.setBackgroundResource(R.drawable.sf_gradient_03);
            recipeIngredients.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

        }

        LinkedList<TagView.Tag> tags = new LinkedList<TagView.Tag>();
        for (String content : currentRecipe.getTags()) {
            tags.add(new TagView.Tag(content, Color.parseColor("#ff4081"))); // color is colorAccent
        }
        recipeTags.setTags(tags, " ");

        return view ;
    }

    private void enterRecipesFragment() {
        RecipesFragment a2Fragment = new RecipesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }
}
