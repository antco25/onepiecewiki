package com.example.anthony.onepiecewiki.YouTubeAPI;

import com.example.anthony.onepiecewiki.YouTubeAPI.SearchPOJO.Items;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Example GET:
//"https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q=surfing&key={YOUR_API_KEY}"
public interface YoutubeService {
    @GET("search")
    Call<Items> getSearchResults(
            @Query("part") String part,
            @Query("maxResults") String maxResults,
            @Query("q") String query,
            @Query("key") String apiKey
    );
}


