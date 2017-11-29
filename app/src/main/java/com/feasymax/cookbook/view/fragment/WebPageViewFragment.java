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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.model.util.WebSearch;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;
import com.feasymax.cookbook.view.ViewTransactions;

/**
 * Created by Olya on 2017-10-12.
 */

public class WebPageViewFragment extends Fragment {

    public static final String FRAGMENT_ID = "WebPageViewFragment";

    private WebView webView;
    private Button btnBack;

    public WebPageViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webpage, container, false);

        setHasOptionsMenu(true);

        webView = view.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.println(Log.ERROR, "setWebViewClient", "Error code: " + errorCode + "(" +
                        description + ")");
            }
        });
        webView.loadUrl(Application.getDiscoverCollection().getWebsearchResult());

        btnBack = view.findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPrevFragment();
            }
        });

        return view;
    }

    /**
     * Go back to all recipes in a category
     */
    protected void enterPrevFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // Store the Fragment in stack
        ViewTransactions.getViews().add(FRAGMENT_ID);
        transaction.addToBackStack(null);
        transaction.detach(this).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (getActivity() instanceof DiscoverActivity) {
            inflater.inflate(R.menu.menu_webpage, menu);
        }
        else {
            Log.println(Log.ERROR, "MENU","unexpected activity");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (!Application.isUserSignedIn()) {
            MenuItem item = menu.findItem(R.id.action_link_add);
            if (item != null) {
                item.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Log.println(Log.INFO, "MENU","action_info has clicked");
                return true;
            case R.id.action_link_add:
                Log.println(Log.INFO, "MENU","action_link_add was clicked");
                WebpageInfo webpageInfo = WebSearch.parsePageHeaderInfo(Application.
                        getDiscoverCollection().getWebsearchResult());
                if (webpageInfo != null) {
                    Application.addLink(webpageInfo);
                }
                return true;
            default:
                break;
        }
        return false;
    }
}
