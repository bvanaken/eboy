package com.eboy.subscriptions.model;

public class SellerInfo {

    Integer numberSubscribers;
    Float priceMin;
    Float priceMax;

    public SellerInfo(Integer numberSubscribers, Float priceMin, Float priceMax) {
        this.numberSubscribers = numberSubscribers;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
    }

    public Integer getNumberSubscribers() {
        return numberSubscribers;
    }

    public Float getPriceMin() {
        return priceMin;
    }

    public Float getPriceMax() {
        return priceMax;
    }
}
