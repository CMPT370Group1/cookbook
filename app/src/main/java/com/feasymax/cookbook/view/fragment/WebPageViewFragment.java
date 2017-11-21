package com.feasymax.cookbook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.feasymax.cookbook.R;

/**
 * Created by Olya on 2017-10-12.
 */

public class WebPageViewFragment extends Fragment {

    private WebView webView;

    public WebPageViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webpage, container, false);

        webView = view.findViewById(R.id.web_view);
        webView.loadUrl("http://allrecipes.com/recipe/17481/simple-white-cake/");

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
}
