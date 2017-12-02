package com.feasymax.cookbook.view.list;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.util.Graphics;
import com.feasymax.cookbook.view.fragment.common.ShowWebpagesFragment;

import java.util.List;

import pl.charmas.android.tagview.TagView;

/**
 * Created by Olya on 2017-11-06.
 */

public class LinksListAdapter extends BaseAdapter implements View.OnClickListener {
    /**
     * Declare Used Variables
     */
    private ShowWebpagesFragment fragment;
    private List data;
    private static LayoutInflater inflater = null;
    public Resources res;
    WebpageInfo tempValues = null;
    int i = 0;

    /**
     *  CustomAdapter Constructor
     */
    public LinksListAdapter(ShowWebpagesFragment a, List d, Resources resLocal) {

        // Take passed values
        fragment = a;
        data = d;
        res = resLocal;

        // Layout inflater to call external xml layout
        inflater = ( LayoutInflater ) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // What is the size of Passed list Size
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

        public TextView webpageTitle;
        public TagView webpageWebsite;
        public ImageView webpageImage;

    }

    // Depends upon data size called for each row , Create each ListView row
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){

            // Inflate tabitem_recipe_recipe.xml file for each row ( Defined below )
            vi = inflater.inflate(R.layout.tabitem_links, null);

            // View Holder Object to contain tabitem_recipe.xmlipe.xml file elements

            holder = new ViewHolder();
            holder.webpageTitle = vi.findViewById(R.id.webpageTitle);
            holder.webpageWebsite = vi.findViewById(R.id.webpageWebsite);
            holder.webpageImage =(ImageView)vi.findViewById(R.id.webpageImage);

            // Set holder with LayoutInflater
            vi.setTag( holder );
        }
        else
            holder = (ViewHolder) vi.getTag();

        if(getCount() == 0)
        {
            holder.webpageTitle.setText("No Data");

        }
        else
        {
            // Get each Model object from list
            tempValues = null;
            tempValues = ( WebpageInfo ) data.get( position );

            // Set Model values in Holder elements

            holder.webpageTitle.setText( tempValues.getTitle() );
            TagView.Tag[] website = { new TagView.Tag(tempValues.getWebsiteName(),
                    Color.parseColor("#ff4081")) };
            holder.webpageWebsite.setTags(website, " ");
            if (tempValues.getImage() != null) {
                holder.webpageImage.setImageBitmap(tempValues.getImage());
            }
            else {
                holder.webpageImage.setImageBitmap(Graphics.decodeSampledBitmapFromResource(res, R.drawable.no_image420, 200, 200));
            }

            // Set Item Click Listeners for LayoutInflater for each row
            vi.setOnClickListener(new OnItemClickListener( position ));
            vi.setOnLongClickListener(new OnItemLongClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /**
     * Called when Item click in ListView
     */
    private class OnItemClickListener implements View.OnClickListener{
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            fragment.onItemClick(mPosition);
        }
    }

    /**
     * Called when Item long click in ListView
     */
    private class OnItemLongClickListener implements View.OnLongClickListener{
        private int mPosition;

        OnItemLongClickListener(int position){
            mPosition = position;
        }

        @Override
        public boolean onLongClick(View arg0) {
            Log.d("OnItemLongClickListener", "log clicked: " + mPosition);
            fragment.onItemLongClick(mPosition);
            return true;
        }
    }

}
