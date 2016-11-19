package com.eboy.nlp.luis;

import com.eboy.nlp.Intent;
import com.eboy.nlp.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class LuisProcessor implements TextProcessor {

    public static final String QUALIFIER = "LuisProcessor";
    private final static Logger logger = Logger.getLogger(LuisProcessor.class.getName());

    private final String APP_ID = "bacd3ac3-ffe7-48f5-a0fa-2da8e0f9213a";
    private final String SUBSCRIPTION_KEY = "9bc29da5e4714733b9d09537f4ac3ce7";
    private final String BASE_URL = "https://api.projectoxford.ai/luis/v2.0/apps/";
    private final String QUERY_URL = BASE_URL + APP_ID + "?subscription-key=" + SUBSCRIPTION_KEY + "&q=";

    private RestTemplate restTemplate;

    @Autowired
    public LuisProcessor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Intent getIntentFromText(String text) {
        Assert.notNull(text);

        String url = QUERY_URL + text;

        try {
            ResponseEntity<LuisQueryResponse> response = restTemplate.getForEntity(url, LuisQueryResponse.class);

            if (response != null && response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                LuisQueryResponse queryResponse = response.getBody();

                LuisIntent intent = queryResponse.getTopIntent();

                if (intent != null) {

                    Intent highestScoredIntent = intent.getIntent();
                    logger.info("LUIS identified Intent: " + highestScoredIntent);

                    return highestScoredIntent;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
