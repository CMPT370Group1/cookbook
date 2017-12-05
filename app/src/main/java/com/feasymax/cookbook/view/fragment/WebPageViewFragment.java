package com.feasymax.cookbook.view.fragment;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.feasymax.cookbook.R;
import com.feasymax.cookbook.model.Application;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.model.util.WebSearch;
import com.feasymax.cookbook.view.DiscoverActivity;
import com.feasymax.cookbook.view.RecipesActivity;

/**
 * Created by Olya on 2017-10-12.
 */

public class WebPageViewFragment extends Fragment {

    public static final String FRAGMENT_ID = "WebPageViewFragment";

    private WebView webView;
    private Button btnBack;

    /**
     * Required empty public constructor
     */
    public WebPageViewFragment() {}

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
                if (getActivity() instanceof RecipesActivity) {
                    enterLinksFragment();
                }
                else if (getActivity() instanceof DiscoverActivity) {
                    enterPrevFragment();
                }
            }
        });

        return view;
    }

    protected void enterLinksFragment() {
        RecipeLinksFragment a2Fragment = new RecipeLinksFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.categories_main_layout, a2Fragment).commit();
    }

    /**
     * Go back to all recipes in a category
     */
    protected void enterPrevFragment() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.detach(this).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_webpage, menu);
        if (!Application.isUserSignedIn() || (Application.isUserSignedIn() && Application.getUserCollection().
                containsLink(Application.getDiscoverCollection().getWebsearchResult()))) {
            MenuItem item = menu.findItem(R.id.action_link_add);
            if (item != null) {
                item.setEnabled(false);
            }
        }
        if (!Application.isUserSignedIn() || !Application.getUserCollection().
                containsLink(Application.getDiscoverCollection().getWebsearchResult())) {
            MenuItem item = menu.findItem(R.id.action_link_delete);
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
                    if (!Application.addLink(webpageInfo)) {
                        Toast.makeText(getContext(), "The link has already been " +
                                "added before", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    else {
                        Toast.makeText(getContext(), "The link has been added",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                // hide keyboard
                InputMethodManager imm = (InputMethodManager)getActivity().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getCurrentFocus().getWindowToken(), 0);
                return true;
            case R.id.action_link_delete:
                Log.println(Log.INFO, "MENU","action_link_delete was clicked");
                Application.deleteLink(Application.getUserCollection().getCurLink());
                Toast.makeText(getContext(), "The link has been deleted",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                break;
        }
        return false;
    }
}
