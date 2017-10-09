package com.olya.cookbook.model;

import java.util.List;

/**
 * Created by Olya on 2017-10-08.
 */

public class Recipe {

    private String title;
    private int category;
    private List<Ingredient> ingredients;
    private String directions;
    private int duration;
    private List<String> tags;

    public Recipe(String title, int category, List<Ingredient> ingredients, String directions, int duration, List<String> tags) {
        this.title = title;
        this.category = category;
        this.ingredients = ingredients;
        this.directions = directions;
        this.duration = duration;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", category=" + category +
                ", ingredients=" + ingredients +
                ", directions='" + directions + '\'' +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}
