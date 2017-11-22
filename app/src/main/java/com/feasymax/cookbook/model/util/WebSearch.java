package com.feasymax.cookbook.model.util;
import android.util.Log;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.List;


/**
 * Created by Olya on 2017-10-12.
 */

public class WebSearch {
    private static List<WebpageInfo> results;

    private WebSearch() {}

    /**
     * 
     * @param input
     * @return
     */
    public static List<WebpageInfo> getWebSearch(final String input) {

        results = null;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    org.jsoup.nodes.Document doc;

                    String searchTokens = "";
                    for (String retval : input.split(" ")) {
                        searchTokens += retval + "+";
                    }
                    searchTokens = searchTokens.substring(0, searchTokens.length() - 1);
                    Log.println(Log.INFO, "searchTokens", searchTokens);

                    doc = Jsoup.connect("https://www.google.com/search?q=" + searchTokens).get();
                    Elements links = doc.select("div[class=g]");
                    //Log.println(Log.INFO, "elements", links.toString());
                    for (Element link : links) {
                        Elements titles = link.select("h3[class=r]");
                        String title = titles.text();

                        Elements bodies = link.select("span[class=st]");
                        String body = bodies.text();

                        Elements addrs = link.select("a[href]");
                        String addr = addrs.attr("abs:href");
                        //results.
                        // TODO: also save an image

                        Log.println(Log.INFO, "getSearchResults", "Title: " + title);
                        Log.println(Log.INFO, "getSearchResults", "Body: " + body);
                        Log.println(Log.INFO, "getSearchResults", "Link: " + addr + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;

    }

}
