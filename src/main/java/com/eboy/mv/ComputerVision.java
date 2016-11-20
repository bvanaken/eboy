package com.eboy.mv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This class performs a API Request to https://api.projectoxford.ai/vision/v1 and sends back the recognized object as json array.
 * Created by khaled on 19.11.16.
 */
@Service
public class ComputerVision {

    /**
     * The main URL for calling the Computer Vision API of Microsoft.
     */
    private static final String MAIN_URL = "https://api.projectoxford.ai/vision/v1.0/analyze?language=en";

    /**
     * The content type in the header of teh api call as final {@link String}
     */
    private static final String CONTENT_TYPE = "application/json";

    /**
     * The API_KEY for performing requests on the image recognition api as final {@link String}
     */
    private String API_KEY = "6e83c66d3aa64b9cb952aaa914491098";

    /**
     * The RestTemplate for the request as {@link RestTemplate}.
     */
    private RestTemplate restTemplate;

    /**
     * Standard Constructor generated.
     *
     * @param restTemplate
     */
    @Autowired
    public ComputerVision(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String analyzeImage(String body) {
        HttpEntity<String> request = new HttpEntity<String>(body, buildHeader());

        ResponseEntity<String> response = restTemplate.postForEntity(MAIN_URL, request, String.class);

        return response.getBody();
    }

    /**
     * This method builds and returns the header for the http reqeust.
     *
     * @return The header for the http request as {@link HttpHeaders}.
     */
    private HttpHeaders buildHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", API_KEY);
        headers.add("Content-Type", CONTENT_TYPE);

        return headers;
    }
}
