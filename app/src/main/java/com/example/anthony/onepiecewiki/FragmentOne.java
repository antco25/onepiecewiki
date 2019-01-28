package com.example.anthony.onepiecewiki;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


//TODO: Get the data from Google API
public class FragmentOne extends Fragment implements EasyPermissions.PermissionCallbacks {

    private TextView outputText;
    private Button pushButton;
    private boolean isPushButtonClicked = false;
    GoogleAccountCredential mCredential;

    private static final String[] SCOPES = { YouTubeScopes.YOUTUBE_READONLY };

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

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getActivity().getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

    }

    /**
     * Call API after verifying all preconditions are satisfied
     */
    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            outputText.setText("Google Play Services unavailable");
            return;
        }

        /*

        if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
            return;
        }

        */

        if (!isDeviceOnline()) {
            outputText.setText("No network connection available.");
            return;
        }

        new MakeRequestTask(mCredential).execute();
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    private void chooseAccount() {
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //Do nothing
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //Do nothing
    }

    private boolean isDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Asynchronous task handling Youtube API calls
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.youtube.YouTube mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.youtube.YouTube.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("YouTube Data API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call YouTube Data API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch information about the "GoogleDevelopers" YouTube channel.
         * @return List of Strings containing information about the channel.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // Get a list of up to 10 files.
            List<String> searchInfo = new ArrayList<String>();
            SearchListResponse result = mService.search().list("snippet")
                    .setMaxResults((long) 3)
                    .setQ("surfing")
                    .setType("video")
                    .execute();
            /*
            ChannelListResponse result = mService.channels().list("snippet,contentDetails,statistics")
            .setForUsername("GoogleDevelopers")
            .execute();



            List<Channel> channels = result.getItems();
            if (channels != null) {
                Channel channel = channels.get(0);
                channelInfo.add("This channel's ID is " + channel.getId() + ". " +
                        "Its title is '" + channel.getSnippet().getTitle() + ", " +
                        "and it has " + channel.getStatistics().getViewCount() + " views.");
            }

                        if (channels != null) {
                Channel channel = channels.get(0);
                channelInfo.add("This channel's ID is " + channel.getId() + ". " +
                        "Its title is '" + channel.getSnippet().getTitle() + ", " +
                        "and it has " + channel.getStatistics().getViewCount() + " views.");
            return channelInfo;
            */

            List<SearchResult> searchResults = result.getItems();
            for (SearchResult searchResult : searchResults) {
                searchInfo.add(searchResult.getSnippet().getTitle());
            }

            return searchInfo;
        }


        @Override
        protected void onPreExecute() {
            outputText.setText("");
        }

        @Override
        protected void onPostExecute(List<String> output) {
            if (output == null || output.size() == 0) {
                outputText.setText("No results returned.");
            } else {
                output.add(0, "Data retrieved using the YouTube Data API:");
                outputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
            if (mLastError != null) {
                    outputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());
            } else {
                outputText.setText("Request cancelled.");
            }
        }
    }
}



