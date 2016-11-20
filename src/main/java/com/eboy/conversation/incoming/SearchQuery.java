package com.eboy.conversation.incoming;

import com.eboy.data.dto.Price;

/**
 * Created by root1 on 20/11/16.
 */
public class SearchQuery {

    private Float maxPrice;
    private Boolean isBerlin;
    private String main_keyword;
    private String extra_keyword;

    public SearchQuery() {
        //
    }

    public SearchQuery(Float maxPrice, Boolean isBerlin, String main_keyword, String extra_keyword) {
        this.maxPrice = maxPrice;
        this.isBerlin = isBerlin;
        this.main_keyword = main_keyword;
        this.extra_keyword = extra_keyword;
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

    public String getMain_keyword() {
        return main_keyword;
    }

    public void setMain_keyword(String main_keyword) {
        this.main_keyword = main_keyword;
    }

    public String getExtra_keyword() {
        return extra_keyword;
    }

    public void setExtra_keyword(String extra_keyword) {
        this.extra_keyword = extra_keyword;
    }
}
