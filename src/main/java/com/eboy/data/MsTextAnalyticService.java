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

/**
 * Created by alex on 19.11.16.
 */
@Service
public class MsTextAnalyticService {

    final String BASE_STRING = "https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/keyPhrases";


    RestTemplate restTemplate;

    @Autowired
    public MsTextAnalyticService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String analyze(String body) {

        String url = BASE_STRING;

        String apiKey = "52216691a2ec443a97bd36103d9b8630";

        HttpHeaders headers = createHeaders(apiKey);


        HttpEntity<String> request = new HttpEntity<String>(body,headers);

        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        String account = response.getBody();

        return account;

    }

    HttpHeaders createHeaders(String apiKey) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", apiKey);
        headers.add("Content-Type", "application/json");

        return headers;
    }
}