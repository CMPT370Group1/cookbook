package com.feasymax.cookbook.view.list;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.view.fragment.RecipesFragment;
import com.feasymax.cookbook.view.fragment.common.ShowRecipesFragment;

import java.util.ArrayList;

/**
 * Created by Olya on 2017-11-06.
 */

public class RecipeListAdapter extends BaseAdapter implements View.OnClickListener {
    /*********** Declare Used Variables *********/
    private ShowRecipesFragment fragment;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    RecipeListModel tempValues = null;
    int i = 0;

    /*************  CustomAdapter Constructor *****************/
    public RecipeListAdapter(ShowRecipesFragment a, ArrayList d, Resources resLocal) {

        /********** Take passed values **********/
        fragment = a;
        data = d;
        res = resLocal;

        /***********  Layout inflater to call external xml layout () ***********/
        inflater = ( LayoutInflater ) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView recipeTitle;
        public TextView recipeDuration;
        public ImageView recipeImage;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.tabitem, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.recipeTitle = (TextView) vi.findViewById(R.id.recipeTitle);
            holder.recipeDuration =(TextView)vi.findViewById(R.id.recipeCaption);
            holder.recipeImage =(ImageView)vi.findViewById(R.id.recipeImage);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder = (ViewHolder) vi.getTag();

        if(data.size() <= 0)
        {
            holder.recipeTitle.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = ( RecipeListModel ) data.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.recipeTitle.setText( tempValues.getRecipeTitle() );
            holder.recipeDuration.setText( displayDuration(tempValues.getRecipeDuration()) );
            holder.recipeImage.setImageBitmap(tempValues.getRecipeImage());

            /******** Set Item Click Listener for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
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

    private String displayDuration(int duration) {
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
