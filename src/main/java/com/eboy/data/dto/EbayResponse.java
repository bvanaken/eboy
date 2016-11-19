package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EbayResponse {

    @JsonProperty("{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ads")
    AdsObject adsObject;

    public EbayResponse() {
    }

    public AdsObject getAdsObject() {
        return adsObject;
    }

    @Override
    public String toString() {
        return "EbayResponse{" +
                "adsObject=" + adsObject +
                '}';
    }
}
