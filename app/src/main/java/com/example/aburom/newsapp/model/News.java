package com.example.aburom.newsapp.model;

import java.io.Serializable;

public class News implements Serializable {

    public static final String DB_NAME = "news_db";
    public static final String TABLE_NAME = "news";

    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_IMAGE = "image";
    public static final String COL_TIME = "time";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_FAVORITE = "favorite";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ( "
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_TITLE + " TEXT, "
                    + COL_TIME + " TEXT, "
                    + COL_DESCRIPTION + " TEXT, "
                    + COL_IMAGE + " BLOB, "
                    + COL_FAVORITE + " INTEGER)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String GET_NEWS = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID DESC";
    public static final String GET_FAVORITE_NEWS = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_FAVORITE + " = 1 ORDER BY ID DESC";


    private int id;
    private byte[] image;
    private String title;
    private String time;
    private String description;
    private boolean favorite;

    public News(int id, byte[] image, String title, String time, String description) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.time = time;
        this.description = description;
        this.favorite = false;
    }

    public News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
