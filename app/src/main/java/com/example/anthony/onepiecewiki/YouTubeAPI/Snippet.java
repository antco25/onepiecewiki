package com.example.anthony.onepiecewiki.YouTubeAPI;

import com.google.gson.annotations.SerializedName;

public class Snippet {
    @SerializedName("title")
    private String title;

    public Snippet(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
