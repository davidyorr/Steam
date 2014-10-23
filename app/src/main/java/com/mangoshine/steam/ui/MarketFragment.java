package com.mangoshine.steam.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mangoshine.steam.R;
import com.mangoshine.steam.core.market.MarketListingsListAdapter;
import com.mangoshine.steam.io.MarketClient;

public class MarketFragment extends AbstractBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    MarketClient mMarketClient;
    MarketListingsListAdapter mAdapter;

    public MarketFragment() {
        mLayout = R.layout.fragment_market;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mSwipeRefreshLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.market_listings_scrollable_list, (ViewGroup) view, false);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        ListView listView = (ListView) mSwipeRefreshLayout.findViewById(R.id.market_listings_scrollable_list);
        View marketListingsHeader = inflater.inflate(R.layout.market_listings_header, (ViewGroup) view, false);
        mAdapter = new MarketListingsListAdapter(getActivity());
        listView.setAdapter(mAdapter);

        ((ViewGroup) view).addView(marketListingsHeader);
        ((ViewGroup) view).addView(mSwipeRefreshLayout);

        mMarketClient = new MarketClient();
        mMarketClient.refreshPopularListings(mAdapter, mSwipeRefreshLayout);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.market_games, menu);
    }

    @Override
    public void onRefresh() {
        mMarketClient.refreshPopularListings(mAdapter, mSwipeRefreshLayout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
