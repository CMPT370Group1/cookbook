package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feasymax.cookbook.R;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipeSearchFragment extends Fragment {

    public RecipeSearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_search, container, false);
        return view ;
    }
}
