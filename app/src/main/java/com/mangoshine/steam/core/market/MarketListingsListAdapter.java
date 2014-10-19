package com.mangoshine.steam.core.market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mangoshine.steam.R;
import com.mangoshine.steam.util.CurrencyUtils;

import java.util.ArrayList;
import java.util.List;

public class MarketListingsListAdapter extends BaseAdapter {
    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private List<MarketListing> mMarketListings = new ArrayList<MarketListing>();

    public MarketListingsListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mMarketListings.size();
    }

    @Override
    public Object getItem(int position) {
        return mMarketListings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout marketListingsLayout;
        if (convertView == null) {
            marketListingsLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.market_listings_item, parent, false);
        } else {
            marketListingsLayout = (LinearLayout) convertView;
        }

        MarketListing marketListing = mMarketListings.get(position);
        TextView textView;
        textView = (TextView) marketListingsLayout.findViewById(R.id.market_listing_name);
        textView.setText(marketListing.getName());

        textView = (TextView) marketListingsLayout.findViewById(R.id.market_listing_quantity);
        textView.setText(marketListing.getQuantity().toString());

        textView = (TextView) marketListingsLayout.findViewById(R.id.market_listing_price);
        textView.setText(CurrencyUtils.parseCentsToDollars(marketListing.getPrice()));

        return marketListingsLayout;
    }

    public void updateListings(List<MarketListing> listings) {
        mMarketListings = listings;
        notifyDataSetChanged();
    }
}
