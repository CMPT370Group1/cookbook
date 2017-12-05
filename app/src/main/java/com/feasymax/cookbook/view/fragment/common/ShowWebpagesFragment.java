package com.feasymax.cookbook.view.fragment.common;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.view.fragment.WebPageViewFragment;

import java.util.List;

/**
 * Created by Olya on 2017-11-11.
 * An abstract class used by LinksListAdapter for a list of web-pages
 */

public abstract class ShowWebpagesFragment extends Fragment {

    protected RelativeLayout noItemsLayout;

    /**
     * Variables to display the list of web-pages
     */
    protected ListView list;
    public List<WebpageInfo> CustomListViewValuesArr;
    public ShowWebpagesFragment CustomListView = null;

    public abstract void setListData();

    public void onItemClick(int mPosition) {
        enterWebpageViewFragment();
    }

    /**
     * Copy web-page's url to clipboard on long click
     * @param mPosition
     */
    public void onItemLongClick(int mPosition)
    {
        WebpageInfo tempValues = CustomListViewValuesArr.get(mPosition);
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("web-page", tempValues.getUrl());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), "The url was copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    /**
     * Enter fragment with webview that displays chosen web-page
     */
    protected void enterWebpageViewFragment() {
        WebPageViewFragment a2Fragment = new WebPageViewFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        list.setAdapter(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

}
