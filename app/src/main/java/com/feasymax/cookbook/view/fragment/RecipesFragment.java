package com.feasymax.cookbook.view.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.FileNotFoundException;
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
        final RecipeListModel recipe1 = new RecipeListModel();

        /******* Firstly take data in model object ******/
        recipe1.setRecipeTitle("Delicious cake");
        recipe1.setRecipeImage(decodeResource(getResources(), R.drawable.dessert, 100, 100));
        recipe1.setRecipeCaption("This is very good for a celebration!");

        /******** Take Model Object in ArrayList **********/
        CustomListViewValuesArr.add( recipe1 );

        final RecipeListModel recipe2 = new RecipeListModel();

        /******* Firstly take data in model object ******/
        recipe2.setRecipeTitle("Pumpkin soup");
        recipe2.setRecipeImage(decodeResource(getResources(), R.drawable.soup, 100, 100));
        recipe2.setRecipeCaption("Very light and tasty soup. Perfect for summer.");

        /******** Take Model Object in ArrayList **********/
        CustomListViewValuesArr.add( recipe2 );

    }

    /**
     * Get the bitmap constrained by specified dimensions from resource
     * @param res
     * @param id
     * @param maxWidth
     * @param maxHeight
     * @return bitmap
     */
    private Bitmap decodeResource(Resources res, int id, int maxWidth, int maxHeight) {
        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), id, dimensions);
        int height = dimensions.outHeight;
        int width =  dimensions.outWidth;

        int scale = 1;
        while (true) {
            if (width / 2 < maxWidth || height / 2 < maxHeight) {
                break;
            }
            width /= 2;
            height /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inSampleSize = scale;
        return BitmapFactory.decodeResource(res, id, o);
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
