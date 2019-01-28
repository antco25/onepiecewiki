package com.example.anthony.onepiecewiki.YouTubeAPI;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Items {

    @SerializedName("items")
    private ArrayList<SearchResult> searchResultList;

    public ArrayList<SearchResult> getSearchResultList() {
        return searchResultList;
    }

    public void setSearchResultList(ArrayList<SearchResult> searchResultList) {
        this.searchResultList = searchResultList;
    }
}
