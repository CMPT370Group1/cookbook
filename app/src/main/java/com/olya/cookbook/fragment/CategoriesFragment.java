package com.olya.cookbook.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.olya.cookbook.R;
import com.olya.cookbook.tab.MyAdapter;

/**
 * Created by Olya on 2017-09-21.
 */

public class CategoriesFragment extends Fragment{

    private ImageButton btnBreakfast;

    public CategoriesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        btnBreakfast = view.findViewById(R.id.ib_breakfast);
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNextFragment();
            }
        });

        return view ;
    }

    private void enterNextFragment() {
        RecipesFragment a2Fragment = new RecipesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }
}
