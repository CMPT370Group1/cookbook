package com.feasymax.cookbook.model.util;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.feasymax.cookbook.model.entity.WebpageInfo;
import com.feasymax.cookbook.util.Graphics;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Olya on 2017-10-12.
 */

public class WebSearch {

    private static List<WebpageInfo> results;
    private static WebpageInfo webpageInfo;

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

                        webpageInfo = new WebpageInfo(title, url, website, body, null);
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

    public static WebpageInfo parsePageHeaderInfo(final String urlStr) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection con = Jsoup.connect(urlStr).referrer("http://www.google.com").
                            userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) " +
                                    "Gecko/20070725 Firefox/2.0.0.6");

                    Document doc = con.get();

                    String title = doc.title();

                    String imageUrl = null;
                    Elements metaOgImage = doc.select("meta[property=og:image]");
                    if (metaOgImage!=null) {
                        imageUrl = metaOgImage.attr("content");
                    }

                    Bitmap image = null;
                    if (imageUrl != null) {
                        try {
                            URL url = new URL(imageUrl);
                            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            image = Graphics.resize(image, 200, 200);
                        } catch(IOException e) {
                            System.out.println(e);
                        }

                    }

                    String website = "";
                    website = urlStr.replace("https://", "");
                    website = website.replace("http://", "");
                    website = website.replace("www.", "");
                    try {
                        website = website.substring(0, website.indexOf('/'));
                    } catch (Exception e) {}

                    webpageInfo = new WebpageInfo(title, urlStr, website, "", image);

                }
                catch (Exception e) {

                }

            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return webpageInfo;
    }

}
