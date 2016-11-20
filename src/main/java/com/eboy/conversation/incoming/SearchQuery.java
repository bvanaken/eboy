package com.eboy.conversation.incoming;

/**
 * Created by root1 on 20/11/16.
 */
public class SearchQuery {

    private Float maxPrice;
    private Boolean isBerlin;
    private String mainKeyword;
    private String extraKeyword;

    public SearchQuery() {
    }

    public SearchQuery(Float maxPrice, Boolean isBerlin, String mainKeyword, String extraKeyword) {
        this.maxPrice = maxPrice;
        this.isBerlin = isBerlin;
        this.mainKeyword = mainKeyword;
        this.extraKeyword = extraKeyword;
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

    public String getExtraKeyword() {
        return extraKeyword;
    }

    public void setExtraKeyword(String extraKeyword) {
        this.extraKeyword = extraKeyword;
    }

    public boolean isComplete() {
        return (this.maxPrice != null &&
                this.isBerlin != null &&
                this.mainKeyword != null &&
                this.extraKeyword != null);
    }

    @Override
    public String toString() {
        return "SearchQuery{" +
                "maxPrice=" + maxPrice +
                ", isBerlin=" + isBerlin +
                ", mainKeyword='" + mainKeyword + '\'' +
                ", extraKeyword='" + extraKeyword + '\'' +
                '}';
    }
}
