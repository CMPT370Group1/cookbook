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

public class AllRecipesFragment extends Fragment{

    RecipesFragment rf = new RecipesFragment();
    CategoriesFragment cf = new CategoriesFragment();

    private ImageButton btnBreakfast;

    public AllRecipesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
/*
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.categories_main_layout, cf);
        ft.add(R.id.categories_main_layout, rf);
        ft.hide(rf);
        ft.commit();

        btnBreakfast = (ImageButton) view.findViewById(R.id.ib_breakfast);
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterNextFragment();
            }
        });
*/
        return view ;
    }

    private void enterNextFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        //transaction.replace(R.id.categories_main_layout, rf);
        transaction.hide(cf);
        transaction.show(rf);
        transaction.commit();
    }
}
