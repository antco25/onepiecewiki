package com.example.anthony.onepiecewiki;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anthony.onepiecewiki.YouTubeAPI.SearchPOJO.Items;
import com.example.anthony.onepiecewiki.YouTubeAPI.SearchPOJO.SearchResult;
import com.example.anthony.onepiecewiki.YouTubeAPI.YoutubeService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentTwo extends Fragment implements FragmentTwoPresenter.View {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FragmentTwoPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //region Instantiate RecyclerView
        recyclerView = view.findViewById(R.id.fragment2_recyclerView);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        //endregion

        presenter = new FragmentTwoPresenter(this);
    }

    public void runSearchQuery(String query) {
        presenter.updateSearchResults(query);

    }

    //Callback from Presenter
    public void updateSearchList(ArrayList<SearchResult> searchResults) {
        adapter.SetData(searchResults);
        adapter.notifyDataSetChanged();
    }

}
