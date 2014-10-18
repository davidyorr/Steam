package com.mangoshine.steam.core.market;

/**
 * A listing on the Steam market
 */
public class MarketListing {
    private String mName;
    private Integer mQuantity;
    private Integer mPrice;

    public MarketListing(String name, Integer quantity, Integer price) {
        mName = name;
        mQuantity = quantity;
        mPrice = price;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Integer getQuantity() {
        return mQuantity;
    }

    public void setQuantity(Integer quantity) {
        this.mQuantity = quantity;
    }

    public Integer getPrice() {
        return mPrice;
    }

    public void setPrice(Integer price) {
        this.mPrice = price;
    }
}
