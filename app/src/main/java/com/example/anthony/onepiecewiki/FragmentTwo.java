package com.example.anthony.onepiecewiki;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.anthony.onepiecewiki.YouTubeAPI.Items;
import com.example.anthony.onepiecewiki.YouTubeAPI.SearchResult;
import com.example.anthony.onepiecewiki.YouTubeAPI.YoutubeService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentTwo extends Fragment {

    private TextView outputText;
    private Button pushButton;
    private boolean isPushButtonClicked = false;

    private static final String API_KEY = "AIzaSyDK5NzplKlsnMSX2h_3JKnTeubUZpAZBfI";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        outputText = view.findViewById(R.id.responseText);
        outputText.setText("Push the button to call API");

        pushButton = view.findViewById(R.id.button);
        pushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPushButtonClicked) {
                    getResultsFromApi();
                    isPushButtonClicked = true;
                }
            }
        });
    }

    private void getResultsFromApi() {
        YoutubeService service = getRetrofitInstance().create(YoutubeService.class);
        Call<Items> searchResults = service.getSearchResults(
                "snippet",
                "5",
                "surfing",
                API_KEY);

        searchResults.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                ArrayList<SearchResult> results = response.body().getSearchResultList();

                String output = "";

                for (SearchResult searchResult : results) {
                    output = output.concat(searchResult.getSnippet().getTitle());
                    output = output.concat("\n");
                }

                outputText.setText(output);

            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
                outputText.setText("Something went wrong... " + t.getMessage());
            }
        });
    }

    private Retrofit getRetrofitInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
