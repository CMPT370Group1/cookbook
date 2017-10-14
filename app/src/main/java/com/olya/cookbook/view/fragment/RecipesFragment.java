package com.olya.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.olya.cookbook.R;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipesFragment extends Fragment{

    private RelativeLayout rl;
    private Button btnAllCategories;

    public RecipesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_recipes, container, false);

        btnAllCategories = (Button) view.findViewById(R.id.buttonAllCategories);
        btnAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterAllCategoriesFragment();
            }
        });

        rl = (RelativeLayout) view.findViewById(R.id.recipe1_layout);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterRecipeViewFragment();
            }
        });

        return view ;
    }

    private void enterRecipeViewFragment() {
        RecipeViewFragment a2Fragment = new RecipeViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    private void enterAllCategoriesFragment() {
        RecipeCategoriesFragment a2Fragment = new RecipeCategoriesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }
}
