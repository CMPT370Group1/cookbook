package com.olya.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olya.cookbook.R;

/**
 * Created by Olya on 2017-09-21.
 */

public class LinksFragment extends Fragment {

    public LinksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_links, container, false);
        //FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), TambahTemanActivity.class);
                //startActivity(intent);
            }
        });*/
        return view;
    }
}
