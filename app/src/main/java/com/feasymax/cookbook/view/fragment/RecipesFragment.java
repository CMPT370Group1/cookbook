package com.feasymax.cookbook.view.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.entity.Recipe;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.fragment.common.ShowRecipesFragment;
import com.feasymax.cookbook.view.list.RecipeListAdapter;
import com.feasymax.cookbook.view.list.RecipeListModel;

import java.util.ArrayList;

/**
 * Created by Olya on 2017-09-21.
 */

public class RecipesFragment extends ShowRecipesFragment{
/*
    private RelativeLayout rl;
    */
    private Button btnAllCategories;

    ListView list;
    RecipeListAdapter adapter;
    public RecipesFragment CustomListView = null;
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

        CustomListView = this;
        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        Resources res =getResources();
        list = ( ListView )view.findViewById( R.id.list );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter = new RecipeListAdapter( this, CustomListViewValuesArr, res );
        list.setAdapter( adapter );

        return view ;
    }

    /****** Function to set data in ArrayList *************/
    public void setListData()
    {
        final RecipeListModel recipe1 = new RecipeListModel();

        /******* Firstly take data in model object ******/
        recipe1.setRecipeId(0);
        recipe1.setRecipeTitle("Delicious cake");
        recipe1.setRecipeImage(decodeResource(getResources(), R.drawable.dessert, 100, 100));
        recipe1.setRecipeDuration(150);

        /******** Take Model Object in ArrayList **********/
        CustomListViewValuesArr.add( recipe1 );

        final RecipeListModel recipe2 = new RecipeListModel();

        /******* Firstly take data in model object ******/
        recipe2.setRecipeId(1);
        recipe2.setRecipeTitle("Pumpkin soup");
        recipe2.setRecipeImage(decodeResource(getResources(), R.drawable.soup, 100, 100));
        recipe2.setRecipeDuration(30);

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
    @Override
    public void onItemClick(int mPosition)
    {
        RecipeListModel tempValues = CustomListViewValuesArr.get(mPosition);
        // TODO: remove image from Recipe constructor and get bigger version from db in getRecipeFromShortInfo
        Recipe curr = new Recipe(tempValues.getRecipeId(), tempValues.getRecipeTitle(), tempValues.getRecipeDuration(), tempValues.getRecipeImage());
        com.feasymax.cookbook.model.Application.getRecipeFromShortInfo(curr);
        if (this.getActivity() instanceof RecipesActivity) {
            com.feasymax.cookbook.model.Application.setUserCurrentRecipe(curr);
        }
        else if (this.getActivity() instanceof DiscoverActivity) {
            com.feasymax.cookbook.model.Application.setDiscoverCurrentRecipe(curr);
        }
        else {
            Log.println(Log.ERROR, "onItemClick", "Unexpected activity");
        }
        enterRecipeViewFragment();
    }


    protected void enterRecipeViewFragment() {
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
