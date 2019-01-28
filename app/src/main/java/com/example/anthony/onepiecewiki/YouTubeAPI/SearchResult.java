package com.example.anthony.onepiecewiki.YouTubeAPI;

import com.google.gson.annotations.SerializedName;

public class SearchResult {

    @SerializedName("snippet")
    private Snippet snippet;

    public SearchResult(Snippet snippet) {
        this.snippet = snippet;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

}
