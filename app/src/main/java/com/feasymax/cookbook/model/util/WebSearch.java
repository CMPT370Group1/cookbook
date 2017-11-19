package com.feasymax.cookbook.model.util;

import android.util.Log;

import com.feasymax.cookbook.view.list.RecipeListModel;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Olya on 2017-10-12.
 */

public class WebSearch {

    private WebSearch() {}

    public static List<RecipeListModel> getSearchResults(String searchString){
        // TODO: tokenize searchString or simply substitute delimiters but '+' to use the
        // resulting string for the address in connect()

        Document doc;
        try{
            doc = Jsoup.connect("https://www.google.ca/search?q=bread+recipe").userAgent("Mozilla").ignoreHttpErrors(true).timeout(0).get();
            Elements links = doc.select("div[class=g]");
            for (Element link : links) {
                Elements titles = link.select("h3[class=r]");
                String title = titles.text();

                Elements bodies = link.select("span[class=st]");
                String body = bodies.text();

                Elements addrs = link.select("a[href]");
                String addr = addrs.attr("abs:href");

                // TODO: also save an image

                Log.println(Log.INFO, "getSearchResults", "Title: " + title);
                Log.println(Log.INFO, "getSearchResults", "Body: "+body);
                Log.println(Log.INFO, "getSearchResults", "Link: "+addr+"\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: create a LinkedList<LinkInfo>, put every link info there and return it
        return null;
    }


}
