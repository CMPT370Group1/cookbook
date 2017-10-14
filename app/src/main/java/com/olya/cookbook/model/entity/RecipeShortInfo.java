package com.olya.cookbook.model.entity;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 */

public class RecipeShortInfo {

    /**
     * The recipe's title
     */
    private String title;
    /**
     * The recipe's duration in minutes
     */
    private int duration;
    /**
     * The recipe's image
     */
    private Bitmap image;

    /**
     * A public constructor that initializes all recipe's attributes
     * @param title
     * @param duration
     * @param image
     */
    public RecipeShortInfo(String title, int duration, Bitmap image) {
        this.title = title;
        this.duration = duration;
        this.image = image;
    }

    /**
     * Get the recipe's title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the recipe's title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the recipe's duration
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set the recipe's duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Get the recipe's image
     * @return
     */
    public Bitmap getImage() {
        return image;
    }

    /**
     * Set the recipe's image
     * @param image
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
