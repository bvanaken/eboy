package com.eboy.conversation.incoming;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by root1 on 20/11/16.
 */
public class SearchQuery {

    private Float maxPrice;
    private Boolean isBerlin;
    private String mainKeyword;

    public SearchQuery() {
    }

    public SearchQuery(Float maxPrice, Boolean isBerlin, String mainKeyword) {
        this.maxPrice = maxPrice;
        this.isBerlin = isBerlin;
        this.mainKeyword = mainKeyword;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getBerlin() {
        return isBerlin;
    }

    public void setBerlin(Boolean berlin) {
        isBerlin = berlin;
    }

    public String getMainKeyword() {
        return mainKeyword;
    }

    public void setMainKeyword(String mainKeyword) {
        this.mainKeyword = mainKeyword;
    }

    @JsonIgnore
    public boolean isComplete() {
        return (this.maxPrice != null &&
                this.isBerlin != null &&
                this.mainKeyword != null);
    }

    @Override
    public String toString() {
        return "SearchQuery{" +
                "maxPrice=" + maxPrice +
                ", isBerlin=" + isBerlin +
                ", mainKeyword='" + mainKeyword + '\'' +
                '}';
    }
}
