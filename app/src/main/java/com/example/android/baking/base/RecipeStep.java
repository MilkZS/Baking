package com.example.android.baking.base;

import java.io.Serializable;

/**
 * Created by milkdz on 2018/3/4.
 */

public class RecipeStep implements Serializable {

    private String id;
    private String sVideo;
    private String sDescription;
    private String sTitle;

    public RecipeStep() {
    }

    public RecipeStep(String id, String sVideo, String sDescription, String sTitle) {
        this.id = id;
        this.sVideo = sVideo;
        this.sDescription = sDescription;
        this.sTitle = sTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsVideo() {
        return sVideo;
    }

    public void setsVideo(String sVideo) {
        this.sVideo = sVideo;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }
}