package com.olya.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.olya.cookbook.R;

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

    private View.OnClickListener categoyOnClickListener;

    public CategoriesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

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

        categoyOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterRecipesFragment();
            }
        };

        btnBreakfast.setOnClickListener(categoyOnClickListener);
        btnAppetizer.setOnClickListener(categoyOnClickListener);
        btnBeverage.setOnClickListener(categoyOnClickListener);
        btnBread.setOnClickListener(categoyOnClickListener);
        btnDessert.setOnClickListener(categoyOnClickListener);
        btnMainDish.setOnClickListener(categoyOnClickListener);
        btnSalad.setOnClickListener(categoyOnClickListener);
        btnSauce.setOnClickListener(categoyOnClickListener);
        btnSideDish.setOnClickListener(categoyOnClickListener);
        btnSnack.setOnClickListener(categoyOnClickListener);
        btnSoup.setOnClickListener(categoyOnClickListener);
        btnVegetable.setOnClickListener(categoyOnClickListener);

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
