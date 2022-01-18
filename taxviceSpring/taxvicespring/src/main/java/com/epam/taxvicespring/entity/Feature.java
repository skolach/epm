package com.epam.taxvicespring.entity;

import java.io.Serializable;

public class Feature implements Serializable{
    private int id;
    private int rating;
    private String featureDescription;
    private String featureDescriptionLong;
    
    public Feature() {
    }

    public Feature(int id, int rating, String featureDescription,
            String featureDescriptionLong) {
        this.id = id;
        this.rating = rating;
        this.featureDescription = featureDescription;
        this.featureDescriptionLong = featureDescriptionLong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getFeatureDescriptionLong() {
        return featureDescriptionLong;
    }

    public void setFeatureDescriptionLong(String featureDescriptionLong) {
        this.featureDescriptionLong = featureDescriptionLong;
    }

    @Override
    public String toString() {
        return "Feature [featureDescription=" + featureDescription + ", featureDescriptionLong="
                + featureDescriptionLong + ", id=" + id + ", rating=" + rating + "]";
    }
}
