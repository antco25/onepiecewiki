package com.example.anthony.onepiecewiki;

import com.example.anthony.onepiecewiki.YouTubeAPI.SearchPOJO.SearchResult;
import com.example.anthony.onepiecewiki.YouTubeAPI.YouTubeModel;

import java.util.ArrayList;

public class FragmentTwoPresenter implements YouTubeModel.SearchResultListener {

    private YouTubeModel youTubeModel;
    private View view;

    public FragmentTwoPresenter(View view) {
        this.youTubeModel = new YouTubeModel();
        youTubeModel.addSearchResultListener(this);
        this.view = view;
    }

    public void updateSearchResults(String query) {
        youTubeModel.searchYouTube(query);
    }

    //Callback from Model
    public void onSearchResultUpdate(ArrayList<SearchResult> searchResults) {
        view.updateSearchList(searchResults);
    }

    public interface View {
        void updateSearchList(ArrayList<SearchResult> searchResults);
    }
}
