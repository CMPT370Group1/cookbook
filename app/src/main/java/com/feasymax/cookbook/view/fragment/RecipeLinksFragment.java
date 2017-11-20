package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.view.fragment.common.OnBackPressFragment;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipeLinksFragment extends OnBackPressFragment {

    private Button openWebpage;

    public RecipeLinksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_links, container, false);

        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Log.println(Log.INFO, "MENU","action_info has clicked");
                return true;
            default:
                Log.println(Log.INFO, "MENU","error");
                break;
        }

        return false;
    }
}
