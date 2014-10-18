package com.mangoshine.steam.io;

import android.os.AsyncTask;
import android.util.Log;

import com.mangoshine.steam.core.market.MarketListing;
import com.mangoshine.steam.core.market.MarketListingsListAdapter;
import com.mangoshine.steam.core.market.MarketUtils;

import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Client used to communicate with the Steam market
 */
public class MarketClient {
    public MarketClient() {

    }

    public List<MarketListing> getPopularListings(MarketListingsListAdapter adapter) {
        List<MarketListing> popularListings = new ArrayList<MarketListing>();
        try {
            new FetchFeedTask(adapter).execute("http://steamcommunity.com/market/popular?country=US&language=english&currency=1&count=10");
        } catch (Exception e) {
            Log.e("MarketClient", e.getMessage());
        }
        return popularListings;
    }

    private class FetchFeedTask extends AsyncTask<String, Void, String> {
        private final MarketListingsListAdapter mMarketListingsListAdapter;

        public FetchFeedTask(MarketListingsListAdapter marketListingsListAdapter) {
            mMarketListingsListAdapter = marketListingsListAdapter;
        }

        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                URLConnection urlConnection = url.openConnection();
                return IOUtils.toString(urlConnection.getInputStream());
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            mMarketListingsListAdapter.updateListings(MarketUtils.responseStringToList(response));
        }
    }
}
