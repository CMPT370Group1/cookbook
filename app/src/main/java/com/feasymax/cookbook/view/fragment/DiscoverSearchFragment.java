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

public class DiscoverSearchFragment extends Fragment {

    public DiscoverSearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dis_search, container, false);
        return view ;
    }
}
