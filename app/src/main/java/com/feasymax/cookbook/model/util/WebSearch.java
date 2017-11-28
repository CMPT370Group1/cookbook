package com.feasymax.cookbook.model.util;
import android.util.Log;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Olya on 2017-10-12.
 */

public class WebSearch {
    private static List<WebpageInfo> results;

    private WebSearch() {}

    /**
     * Send a query to google and get the first page of results
     * @param input
     * @return a list of WebpageInfo
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
                    searchTokens += "recipe";

                            doc = Jsoup.connect("https://www.google.com/search?q=" + searchTokens).get();
                    Elements links = doc.select("div[class=g]");
                    results = new LinkedList<>();
                    WebpageInfo webpageInfo = null;

                    for (Element link : links) {

                        Elements titles = link.select("h3[class=r]");
                        String title = titles.text();
                        try {
                            title = title.substring(0, title.lastIndexOf(" | "));
                        }
                        catch (Exception e){}
                        try {
                            title = title.substring(0, title.lastIndexOf(" - "));
                        }
                        catch (Exception e){}
                        title = title.trim();

                        Elements bodies = link.select("span[class=st]");
                        String body = bodies.text();

                        Elements addrs = link.select("a[href]");
                        String url = addrs.attr("abs:href");

                        String website = "";
                        website = url.replace("https://", "");
                        website = website.replace("http://", "");
                        website = website.replace("www.", "");
                        try {
                            website = website.substring(0, website.indexOf('/'));
                        } catch (Exception e) {}

                        // TODO: also save an image

                        webpageInfo = new WebpageInfo(title,url, website, body);
                        if (!results.contains(webpageInfo)) {
                            results.add(webpageInfo);
                        }

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
