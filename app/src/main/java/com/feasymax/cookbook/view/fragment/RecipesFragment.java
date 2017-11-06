package com.feasymax.cookbook.view.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.view.list.RecipeListAdapter;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.ArrayList;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipesFragment extends Fragment{
/*
    private RelativeLayout rl;
    */
    private Button btnAllCategories;

    ListView list;
    RecipeListAdapter adapter;
    public  RecipesFragment CustomListView = null;
    public ArrayList<RecipeListModel> CustomListViewValuesArr = new ArrayList<RecipeListModel>();

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
/*
        rl = (RelativeLayout) view.findViewById(R.id.recipe1_layout);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterRecipeViewFragment();
            }
        });
*/
        CustomListView = this;
        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        Resources res =getResources();
        list= ( ListView )view.findViewById( R.id.list );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter = new RecipeListAdapter( CustomListView.getActivity(), CustomListViewValuesArr, res );
        list.setAdapter( adapter );

        return view ;
    }

    /****** Function to set data in ArrayList *************/
    public void setListData()
    {

        for (int i = 0; i < 11; i++) {

            final RecipeListModel sched = new RecipeListModel();

            /******* Firstly take data in model object ******/
            sched.setCompanyName("Company "+i);
            sched.setImage("image"+i);
            sched.setUrl("http:\\www."+i+".com");

            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add( sched );
        }

    }

    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        RecipeListModel tempValues = ( RecipeListModel ) CustomListViewValuesArr.get(mPosition);
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
