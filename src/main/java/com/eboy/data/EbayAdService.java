package com.eboy.data;

import com.eboy.data.dto.Ad;
import com.eboy.data.dto.EbayResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class EbayAdService {

    private final static Logger logger = Logger.getLogger(EbayAdService.class.getName());

    final String BASE_STRING = "https://api.ebay-kleinanzeigen.de/api/ads.json?q=";
    final String FIELDS = "&_in=price,ad-type,title,description,ad-address,ad-status,start-date-time,link,pictures";
    final String IS_BERLIN = "&latitude=52.5200&longitude=13.4050&distance=4";
    final String MAX_PRICE = "&maxPrice=";

    final String username = "ebayk_hackathon_chatbot";
    final String password = "cf73e71aa2721eba";

    RestTemplate restTemplate;

    @Autowired
    public EbayAdService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EbayResponse getAdsRequest(List<String> keywords) {
        return this.getAdsRequest(keywords, null, null);
    }

    private EbayResponse getAdsRequest(List<String> keywords, Float priceMax, Boolean isBerlin) {
        String url = BASE_STRING;

        for (String keyword : keywords) {
            url += keyword + " ";
        }

        // delete the last space
        url = url.substring(0, url.length() - 1);
        url += FIELDS;

        if (isBerlin != null && isBerlin) {
            url += IS_BERLIN;
        }

        if (priceMax != null) {
            url += MAX_PRICE + priceMax;
        }

        HttpHeaders headers = createHeaders(username, password);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<EbayResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, EbayResponse.class);
        EbayResponse responseBody = response.getBody();

        logger.info("Ebay URL requested: " + url);

        return responseBody;
    }

    public int getNumberOfAdsForKeywords(List<String> keywords) {
        return this.getNumberOfAdsForKeywords(keywords, null, null);
    }

    public int getNumberOfAdsForKeywords(List<String> keywords, Float priceMax, Boolean isBerlin) {
        EbayResponse adsRequest = this.getAdsRequest(keywords, priceMax, isBerlin);
        return Integer.parseInt(adsRequest.getAdsObject().getAdsValue().getPaging().getEntriesFound());
    }

    public ArrayList<Ad> getAdsForKeywords(List<String> keywords) {
        return this.getAdsForKeywords(keywords, null, null);
    }

    public ArrayList<Ad> getAdsForKeywords(List<String> keywords, Float priceMax, Boolean isBerlin) {
        EbayResponse adsRequest = this.getAdsRequest(keywords, priceMax, isBerlin);
        return adsRequest.getAdsObject().getAdsValue().getAds();
    }

    public Ad getLatestAd(List<String> keywords) {

        List<Ad> ads = this.getAdsForKeywords(keywords, null, null);

        if (ads != null && !ads.isEmpty()) {
            return ads.get(0);
        }

        return null;

    }

    public Ad getLatestAdInBerlin(List<String> keywords) {

        List<Ad> ads = this.getAdsForKeywords(keywords, null, true);

        if (ads != null && !ads.isEmpty()) {
            return ads.get(0);
        }

        return null;
    }

    HttpHeaders createHeaders(String username, String password) {
        String plainCreds = username + ":" + password;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        return headers;
    }
}
