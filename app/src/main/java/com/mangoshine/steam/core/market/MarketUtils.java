package com.mangoshine.steam.core.market;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MarketUtils {
    public static List<MarketListing> responseStringToList(String response) {
        List<MarketListing> marketListings = new ArrayList<MarketListing>();
        try {
            JSONObject responseJSON = new JSONObject(response);
            JSONArray listings = responseJSON.getJSONArray("data");
            JSONObject listing;
            for (int i = 0; i < listings.length(); i++) {
                listing = listings.getJSONObject(i);
                marketListings.add(new MarketListing(listing.getString("name"), listing.getInt("sell_listings"), listing.getInt("sell_price")));
            }
        } catch (Exception e) {

        }
        return marketListings;
    }
}
