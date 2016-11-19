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

import java.util.List;
import java.util.logging.Logger;

@Service
public class EbayAdService {

    private final static Logger logger = Logger.getLogger(EbayAdService.class.getName());

    final String BASE_STRING = "https://api.ebay-kleinanzeigen.de/api/ads.json?q=";
    final String FIELDS = "&_in=price,ad-type,title,description,ad-address,ad-status,start-date-time,link,pictures";

    final String username = "ebayk_hackathon_chatbot";
    final String password = "cf73e71aa2721eba";

    RestTemplate restTemplate;

    @Autowired
    public EbayAdService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Ad> getAdsForKeywords(List<String> keywords) {

        String url = BASE_STRING;

        for (String keyword : keywords) {
            url += keyword + " ";
        }

        // delete the last %20
        url = url.substring(0, url.length() - 1);
        url += FIELDS;

        HttpHeaders headers = createHeaders(username, password);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<EbayResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, EbayResponse.class);

        EbayResponse responseBody = response.getBody();

        logger.info("Ebay URL requested: " + url);

        return responseBody.getAdsObject().getAdsValue().getAds();

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
