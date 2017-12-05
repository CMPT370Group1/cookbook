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
import android.widget.ImageButton;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import java.util.ArrayList;

/**
 * Created by Olya on 2017-09-21.
 * Fragment for categories layout (ALL in MyRecipes and Discover screens in app)
 */

public class CategoriesFragment extends Fragment{

    public static final String FRAGMENT_ID = "CategoriesFragment";

    private ArrayList<ImageButton> categoryButtons;

    private View.OnClickListener categoryOnClickListener;

    /**
     * Required empty public constructor
     */
    public CategoriesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_categories, container, false);

        setHasOptionsMenu(true);

        // find all category buttons
        categoryButtons = new ArrayList<>();
        categoryButtons.add(0, (ImageButton) view.findViewById(R.id.ib_breakfast));
        categoryButtons.add(1, (ImageButton) view.findViewById(R.id.ib_snack));
        categoryButtons.add(2, (ImageButton) view.findViewById(R.id.ib_soup));
        categoryButtons.add(3, (ImageButton) view.findViewById(R.id.ib_appetizer));
        categoryButtons.add(4, (ImageButton) view.findViewById(R.id.ib_salad));
        categoryButtons.add(5, (ImageButton) view.findViewById(R.id.ib_bread));
        categoryButtons.add(6, (ImageButton) view.findViewById(R.id.ib_beverage));
        categoryButtons.add(7, (ImageButton) view.findViewById(R.id.ib_dessert));
        categoryButtons.add(8, (ImageButton) view.findViewById(R.id.ib_main_dish));
        categoryButtons.add(9, (ImageButton) view.findViewById(R.id.ib_side_dish));
        categoryButtons.add(10, (ImageButton) view.findViewById(R.id.ib_sauce));
        categoryButtons.add(11, (ImageButton) view.findViewById(R.id.ib_vegetable));
        categoryButtons.add(12, (ImageButton) view.findViewById(R.id.ib_other));

        categoryButtons.get(0).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.breakfast, 150, 150));
        categoryButtons.get(1).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.snack, 150, 150));
        categoryButtons.get(2).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.soup, 150, 150));
        categoryButtons.get(3).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.appetizer, 150, 150));
        categoryButtons.get(4).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.salad, 150, 150));
        categoryButtons.get(5).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.bread, 150, 150));
        categoryButtons.get(6).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.beverage, 150, 150));
        categoryButtons.get(7).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.dessert, 150, 150));
        categoryButtons.get(8).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.main_dish, 150, 150));
        categoryButtons.get(9).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.side_dish, 150, 150));
        categoryButtons.get(10).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.sauce, 150, 150));
        categoryButtons.get(11).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.vegetable, 150, 150));
        categoryButtons.get(12).setImageBitmap(Graphics.decodeSampledBitmapFromResource(
                getResources(), R.drawable.vegetable, 150, 150));

        // user clicks on a category
        categoryOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof RecipesActivity) {
                    Application.getCollectionRecipes(true, Application.getUser().getUserID(),
                            categoryButtons.indexOf(v));
                    Log.println(Log.INFO, "category", ""+Application.getUserCollection().getCategory());
                }
                else if (getActivity() instanceof DiscoverActivity){
                    Application.getCollectionRecipes(false, -1, categoryButtons.indexOf(v));
                    Log.println(Log.INFO, "category", ""+Application.getDiscoverCollection().getCategory());
                }
                enterRecipesFragment();
            }
        };

        // set up all category image buttons with categoyOnClickListener
        for (ImageButton button: categoryButtons) {
            button.setOnClickListener(categoryOnClickListener);
        }

        return view ;
    }

    /**
     * Open a category fragment that shows all recipes in that category
     */
    protected void enterRecipesFragment() {
        RecipesFragment a2Fragment = new RecipesFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Remove all image bitmaps
        clearFragment();
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_fragment, menu);
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

    protected void clearFragment() {
        for (ImageButton button: categoryButtons) {
            button.setImageBitmap(null);
            button.destroyDrawingCache();
        }
    }
}
