package com.olya.cookbook.view.fragment.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.olya.cookbook.R;
import com.olya.cookbook.view.fragment.RecipesFragment;

/**
 * Created by Olya on 2017-09-21.
 */

public class CategoriesFragment extends Fragment{

    private ImageButton btnBreakfast;
    private ImageButton btnSnack;
    private ImageButton btnSoup;
    private ImageButton btnAppetizer;
    private ImageButton btnSalad;
    private ImageButton btnBread;
    private ImageButton btnBeverage;
    private ImageButton btnDessert;
    private ImageButton btnMainDish;
    private ImageButton btnSideDish;
    private ImageButton btnSauce;
    private ImageButton btnVegetable;

    private View.OnClickListener categoryOnClickListener;

    public CategoriesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dis_categories, container, false);

        btnBreakfast = view.findViewById(R.id.ib_breakfast);
        btnAppetizer = view.findViewById(R.id.ib_appetizer);
        btnBeverage = view.findViewById(R.id.ib_beverage);
        btnBread = view.findViewById(R.id.ib_bread);
        btnDessert = view.findViewById(R.id.ib_dessert);
        btnMainDish = view.findViewById(R.id.ib_main_dish);
        btnSalad = view.findViewById(R.id.ib_salad);
        btnSauce = view.findViewById(R.id.ib_sauce);
        btnSideDish = view.findViewById(R.id.ib_side_dish);
        btnSnack = view.findViewById(R.id.ib_snack);
        btnSoup = view.findViewById(R.id.ib_soup);
        btnVegetable = view.findViewById(R.id.ib_vegetable);

        // user clicks on a category
        categoryOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterRecipesFragment();
            }
        };

        // set up all category image buttons with categoyOnClickListener
        btnBreakfast.setOnClickListener(categoryOnClickListener);
        btnAppetizer.setOnClickListener(categoryOnClickListener);
        btnBeverage.setOnClickListener(categoryOnClickListener);
        btnBread.setOnClickListener(categoryOnClickListener);
        btnDessert.setOnClickListener(categoryOnClickListener);
        btnMainDish.setOnClickListener(categoryOnClickListener);
        btnSalad.setOnClickListener(categoryOnClickListener);
        btnSauce.setOnClickListener(categoryOnClickListener);
        btnSideDish.setOnClickListener(categoryOnClickListener);
        btnSnack.setOnClickListener(categoryOnClickListener);
        btnSoup.setOnClickListener(categoryOnClickListener);
        btnVegetable.setOnClickListener(categoryOnClickListener);

        return view ;
    }

    // open a category fragment that shows all recipes in that category
    protected void enterRecipesFragment() {
        RecipesFragment a2Fragment = new RecipesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }
}
