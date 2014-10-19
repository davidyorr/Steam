package com.mangoshine.steam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mangoshine.steam.R;
import com.mangoshine.steam.core.market.MarketListingsListAdapter;
import com.mangoshine.steam.io.MarketClient;

public class MarketFragment extends AbstractBaseFragment {
    public MarketFragment() {
        mLayout = R.layout.fragment_market;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView) inflater.inflate(R.layout.market_listings_scrollable_list, (ViewGroup) view, false);
        View marketListingsHeader = inflater.inflate(R.layout.market_listings_header, (ViewGroup) view, false);
        MarketListingsListAdapter adapter = new MarketListingsListAdapter(getActivity());
        listView.setAdapter(adapter);

        ((ViewGroup) view).addView(marketListingsHeader);
        ((ViewGroup) view).addView(listView);

        MarketClient marketClient = new MarketClient();
        marketClient.getPopularListings(adapter);

        return view;
    }
}
