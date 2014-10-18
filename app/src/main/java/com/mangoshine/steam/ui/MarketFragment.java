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
        MarketListingsListAdapter adapter = new MarketListingsListAdapter(getActivity());
        ((ListView) view).setAdapter(adapter);

        MarketClient marketClient = new MarketClient();
        marketClient.getPopularListings(adapter);

        return view;
    }
}
