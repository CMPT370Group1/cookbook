package com.olya.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.olya.cookbook.R;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipeLinksFragment extends Fragment {

    private Button openWebpage;

    public RecipeLinksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_links, container, false);

        openWebpage = view.findViewById(R.id.buttonOpen1);
        openWebpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterWebPageViewFragment();
            }
        });
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

    protected void enterWebPageViewFragment() {
        WebPageViewFragment a2Fragment = new WebPageViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.links_main_layout, a2Fragment).commit();
    }
}
