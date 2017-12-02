package com.feasymax.cookbook.view.fragment.common;

import android.support.v4.app.Fragment;

/**
 * Created by Olya on 2017-11-11.
 */

public abstract class ShowWebpagesFragment extends Fragment {

    public void onItemClick(int mPosition) {
        enterWebpageViewFragment();
    }

    public abstract void onItemLongClick(int mPosition);

    public abstract void setListData();

    protected abstract void enterWebpageViewFragment();

}
