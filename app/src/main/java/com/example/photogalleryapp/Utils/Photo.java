package com.example.photogalleryapp.Utils;

import android.graphics.Bitmap;

import java.util.Date;

public class Photo {
    private String caption;
    private Bitmap thumbnail;
    private Date date;

    public Photo(String caption, Bitmap thumbnail, Date date) {
        this.caption = caption;
        this.thumbnail = thumbnail;
        this.date = date;
    }

    public String getName() {
        return caption;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public Date getDate() {
        return date;
    }
}