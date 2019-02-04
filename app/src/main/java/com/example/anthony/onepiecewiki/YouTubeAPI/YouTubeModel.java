package com.example.anthony.onepiecewiki.YouTubeAPI;

import com.example.anthony.onepiecewiki.YouTubeAPI.SearchPOJO.Items;
import com.example.anthony.onepiecewiki.YouTubeAPI.SearchPOJO.SearchResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//TODO: getResultsFromApi -> onFailure
public class YouTubeModel {
    private YoutubeService mService;
    private ArrayList<SearchResult> mSearchResults;
    private int mSearchMaxResults;
    private List<SearchResultListener> searchResultListeners;

    private static final String API_KEY = "AIzaSyDK5NzplKlsnMSX2h_3JKnTeubUZpAZBfI";
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String SEARCH_PART = "snippet";


    public YouTubeModel() {
        mService = getRetrofitInstance().create(YoutubeService.class);
        searchResultListeners = new ArrayList<SearchResultListener>();
        mSearchMaxResults = 15; //Default
    }

    public void searchYouTube(String query) {
        Call<Items> searchResults = mService.getSearchResults(
                SEARCH_PART, String.valueOf(mSearchMaxResults), query, API_KEY);

        searchResults.enqueue(new Callback<Items>() {

            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                mSearchResults = response.body().getSearchResultList();
                onSearchComplete();
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
            }
        });
    }

    private void onSearchComplete() {
        for (SearchResultListener sRL : searchResultListeners)
            sRL.onSearchResultUpdate(mSearchResults);
    }

    public void addSearchResultListener(SearchResultListener toAdd) {
        searchResultListeners.add(toAdd);
    }

    private Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public ArrayList<SearchResult> getSearchResults() {
        return mSearchResults;
    }

    public int getSearchMaxResults() {
        return mSearchMaxResults;
    }

    public void setSearchMaxResults(int searchMaxResults) {
        mSearchMaxResults = searchMaxResults;
    }

    public interface SearchResultListener {
        void onSearchResultUpdate(ArrayList<SearchResult> searchResults);
    }
}
