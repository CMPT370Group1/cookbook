package com.feasymax.cookbook.model.entity;

import android.graphics.Bitmap;

/**
 * Created by zhangxuesong on 2017-11-19.
 */

public class WebpageInfo {
    String title, url,websiteName,description;
    //Bitmap image;
    public WebpageInfo(String title, String url,String websiteName, String description){
        this.title = title;
        this.url = url;
        this.websiteName = websiteName;
        this.description = description;
        //this.image = image;
    }

}
