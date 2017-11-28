package com.feasymax.cookbook.model.entity;

import android.graphics.Bitmap;

/**
 * Created by Xuesong Zhang on 2017-11-19.
 */

public class WebpageInfo {

    String title, url, websiteName, description;
    Bitmap image;

    public WebpageInfo(String title, String url,String websiteName, String description){
        this.title = title;
        this.url = url;
        this.websiteName = websiteName;
        this.description = description;
        this.image = null;
    }

    @Override
    public String toString() {
        return "{ Title: " + title + ", URL: " + url + ", Website: " + websiteName +
                ",\nDescription: " + description + " }\n";
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
