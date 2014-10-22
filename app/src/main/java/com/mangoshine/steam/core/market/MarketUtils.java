package com.mangoshine.steam.core.market;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 */
public class MarketUtils {
    public static List<MarketListing> responseStringToList(String response) {
        List<MarketListing> marketListings = new ArrayList<MarketListing>();
        try {
            JSONObject responseJSON = new JSONObject(response);
            JSONArray listings = responseJSON.getJSONArray("data");
            JSONArray resultsHTML = responseJSON.getJSONArray("results_html");
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc;
            Node imgNode;
            JSONObject listing;
            for (int i = 0; i < listings.length(); i++) {
                listing = listings.getJSONObject(i);
                doc = db.parse(IOUtils.toInputStream(resultsHTML.getString(i)));
                imgNode = doc.getElementsByTagName("img").item(0);
                marketListings.add(
                        new MarketListing(
                                listing.getString("name"),
                                listing.getInt("sell_listings"),
                                listing.getInt("sell_price"),
                                ((Element) imgNode).getAttribute("src").toString()
                        )
                );
            }
        } catch (Exception e) {

        }
        return marketListings;
    }
}
