package com.feasymax.cookbook.view;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Olya on 2017-11-26.
 */

public class ViewTransactions {
    public static List<String> views = new LinkedList<>();

    private ViewTransactions() {}

    public static List<String> getViews() {
        return views;
    }
}
