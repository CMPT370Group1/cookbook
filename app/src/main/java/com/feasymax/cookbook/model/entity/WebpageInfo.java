package com.feasymax.cookbook.model.entity;

import android.graphics.Bitmap;

/**
 * Created by Xuesong Zhang on 2017-11-19.
 * Web-page info to display in the list of web-search results or the list of saved web-pages
 */

public class WebpageInfo {

    int id;
    String title, url, websiteName, description;
    Bitmap image;

    /**
     * Public constructor setting all attributes
     * @param id
     * @param title
     * @param url
     * @param websiteName
     * @param description
     * @param image
     */
    public WebpageInfo(int id, String title, String url,String websiteName, String description, Bitmap image){
        this.id = id;
        this.title = title;
        this.url = url;
        this.websiteName = websiteName;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return "{ ID: " + id + ", Title: " + title + ", URL: " + url + ", Website: " + websiteName +
                ", Description: " + description + " }\n";
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.url.equals(((WebpageInfo)obj).url)) {
            return true;
        }
        return false;
    }
}
