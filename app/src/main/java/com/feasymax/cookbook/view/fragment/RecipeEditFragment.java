package com.feasymax.cookbook.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Ingredient;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.fragment.common.OnBackPressFragment;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Olya on 21/09/2017.
 * The fragment corresponds to fragment_res_add (ADD tab in My Recipes activity)
 * It has a form to add a new user recipe manually.
 */

public class RecipeEditFragment extends RecipeAddFragment {

    private final String FRAGMENT_ID = "RecipeEditFragment";

    private Recipe editedRecipe;

    /**
     * Required empty public constructor
     */
    public RecipeEditFragment() {}

    // TODO: add the button to cancel
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        editedRecipe = Application.getUserCurrentRecipe();

        recipeTitle.setText(editedRecipe.getTitle());
        recipeCategory.setSelection(editedRecipe.getCategory());
        recipeDirections.setText(editedRecipe.getDirections());
        recipeDurationHour.setText(String.valueOf(editedRecipe.getDuration() / 60));
        recipeDurationMin.setText(String.valueOf(editedRecipe.getDuration() % 60));

        //
        recipeImageBitmap = editedRecipe.getImage();
        recipeImage.setImageBitmap(recipeImageBitmap);

        if (editedRecipe.getIngredients() != null) {
            for (Ingredient ingr: editedRecipe.getIngredients()) {
                addNewIngredient(ingr);
            }
        }
        if (editedRecipe.getTags() != null) {
            for (String tag: editedRecipe.getTags()) {
                addNewTag(tag);
            }
        }


        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe(false, editedRecipe.getId());
            }
        });

        return view ;
    }
/*
    @Override
    protected void enterRecipeViewFragment() {
        RecipeViewFragment a2Fragment = new RecipeViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(FRAGMENT_ID);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }
    */
}
