package com.feasymax.cookbook.view.list;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.entity.RecipeShortInfo;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.fragment.common.ShowRecipesFragment;

import java.util.List;

/**
 * Created by Olya on 2017-11-06.
 * Adapter for list of recipes
 */

public class RecipeListAdapter extends BaseAdapter implements View.OnClickListener {
    /**
     * Declare Used Variables
     */
    private ShowRecipesFragment fragment;
    private List data;
    private static LayoutInflater inflater = null;
    public Resources res;
    RecipeShortInfo tempValues = null;
    int i = 0;

    /**
     *  RecipeListAdapter Constructor
     */
    public RecipeListAdapter(ShowRecipesFragment a, List d, Resources resLocal) {

        // Take passed values
        fragment = a;
        data = d;
        res = resLocal;

        // Layout inflater to call external xml layout
        inflater = ( LayoutInflater ) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /**
     * Get the size of passed list
     */
    public int getCount() {

        if (data == null)
            return 0;
        if(data.size()<=0)
            return 0;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * Create a holder Class to contain inflated xml file elements
     */
    public static class ViewHolder{

        public TextView recipeTitle;
        public TextView recipeDuration;
        public ImageView recipeImage;

    }

    /**
     * Depends upon data size called for each row , Create each ListView row
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){

            // Inflate tabitem_recipe.xml file for each row
            vi = inflater.inflate(R.layout.tabitem_recipe, null);

            // View Holder Object to contain tabitem_recipe.xml file elements
            holder = new ViewHolder();
            holder.recipeTitle = (TextView) vi.findViewById(R.id.recipeTitle);
            holder.recipeDuration =(TextView)vi.findViewById(R.id.recipeCaption);
            holder.recipeImage =(ImageView)vi.findViewById(R.id.recipeImage);

            // Set holder with LayoutInflater
            vi.setTag( holder );
        }
        else
            holder = (ViewHolder) vi.getTag();

        // No data to display
        if(getCount() == 0)
        {
            holder.recipeTitle.setText("No Data");
        }
        else
        {
            // Get each Model object from list
            tempValues = null;
            tempValues = (RecipeShortInfo) data.get( position );

            // Set Model values in Holder elements
            holder.recipeTitle.setText( tempValues.getRecipeTitle() );
            holder.recipeDuration.setText( displayDuration(tempValues.getRecipeDuration()) );
            if (tempValues.getRecipeImage() != null) {
                holder.recipeImage.setImageBitmap(tempValues.getRecipeImage());
            }
            else {
                holder.recipeImage.setImageBitmap(Graphics.decodeSampledBitmapFromResource(res, R.drawable.no_image420, 200, 200));
            }


            // Set Item Click Listener for LayoutInflater for each row
            vi.setOnClickListener(new OnItemClickListener( position ));
        }

        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /**
     * Called when Item clicked in ListView
     */
    private class OnItemClickListener  implements View.OnClickListener{
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            fragment.onItemClick(mPosition);

        }
    }

    /**
     * Display duration in hours and minutes
     * @param duration
     * @return
     */
    private String displayDuration(int duration) {
        if (duration == 0) {
            return "Duration: unspecified";
        }
        int hours = duration / 60;
        int min = duration % 60;
        if (hours == 0) {
            return "Duration: " + min + " min";
        }
        else {
            return "Duration: " + hours + " h " + min + " min";
        }
    }

}
