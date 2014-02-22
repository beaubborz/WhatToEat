package com.beaubbe.whattoeat.models;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;

import com.beaubbe.whattoeat.R;

import java.util.ArrayList;
import java.util.List;

public class Recipe
{
    public static final String TABLE_NAME = "recipe";
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_DURATION = "duration";
    public static final String FIELD_PORTIONS = "portions";
    public static final String FIELD_SCORE = "score";
    public static final String FIELD_VOTES = "votes";
    public static final String FIELD_IMAGE_URL = "image_url";

    long id;
    int duration;
    int portions;
    int score;
    int votes;
    String imageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
