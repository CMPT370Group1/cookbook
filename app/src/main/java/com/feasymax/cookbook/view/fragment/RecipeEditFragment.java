package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.Ingredient;

/**
 * Created by Olya on 21/09/2017.
 * The fragment corresponds to fragment_res_add (ADD tab in My Recipes activity)
 * It has a form to add a new user recipe manually.
 */

public class RecipeEditFragment extends RecipeAddFragment {

    public static final String FRAGMENT_ID = "RecipeEditFragment";

    private int prevCategory;
    private Button btnCancel;

    /**
     * Required empty public constructor
     */
    public RecipeEditFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);

        btnCancel = view.findViewById(R.id.buttonCancel);

        editedRecipe = Application.getUserCollection().getCurRecipe();
        prevCategory = editedRecipe.getCategory();

        recipeTitle.setText(editedRecipe.getTitle());
        recipeCategory.setSelection(editedRecipe.getCategory());
        recipeDirections.setText(editedRecipe.getDirections());
        recipeDurationHour.setText(String.valueOf(editedRecipe.getDuration() / 60));
        recipeDurationMin.setText(String.valueOf(editedRecipe.getDuration() % 60));

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

        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipe(false, editedRecipe.getId(), prevCategory);
            }
        });
        btnAddRecipe.setText("Save Recipe");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterRecipeViewFragment();
            }
        });
        btnCancel.setVisibility(View.VISIBLE);


        return view ;
    }

    /**
     * Go back to all recipes in a category
     */
    protected void enterPrevFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        recipeImage.setImageBitmap(null);
        recipeImage.destroyDrawingCache();
        transaction.detach(this).commit();
    }
}
