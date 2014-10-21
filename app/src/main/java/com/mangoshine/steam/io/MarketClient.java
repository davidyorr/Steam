package com.mangoshine.steam.io;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.mangoshine.steam.core.market.MarketListingsListAdapter;
import com.mangoshine.steam.core.market.MarketUtils;

import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.net.URLConnection;

/**
 * Client used to communicate with the Steam market
 */
public class MarketClient {
    public MarketClient() {

    }

    public void refreshPopularListings(MarketListingsListAdapter adapter, SwipeRefreshLayout swipeRefreshLayout) {
        try {
            new FetchFeedTask(adapter, swipeRefreshLayout).execute("http://steamcommunity.com/market/popular?country=US&language=english&currency=1&count=10");
        } catch (Exception e) {
            Log.e("MarketClient", e.getMessage());
        }
    }

    private class FetchFeedTask extends AsyncTask<String, Void, String> {
        private final MarketListingsListAdapter mMarketListingsListAdapter;
        private final SwipeRefreshLayout mSwipeRefreshLayout;

        public FetchFeedTask(MarketListingsListAdapter marketListingsListAdapter, SwipeRefreshLayout swipeRefreshLayout) {
            mMarketListingsListAdapter = marketListingsListAdapter;
            mSwipeRefreshLayout = swipeRefreshLayout;
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
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
