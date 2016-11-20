package com.eboy.nlp.luis;

import com.eboy.event.IntentEvent;
import com.eboy.event.MessageEvent;
import com.eboy.nlp.Intent;
import com.eboy.nlp.TextProcessor;
import com.eboy.nlp.luis.model.LuisIntent;
import com.eboy.nlp.luis.model.LuisQueryResponse;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class LuisProcessor implements TextProcessor {

    @Autowired
    private EventBus eventBus;

    public static final String QUALIFIER = "LuisProcessor";
    private final static Logger logger = Logger.getLogger(LuisProcessor.class.getName());

    private final String APP_ID = "58017722-81dd-4ba9-910c-0ca83cd3d6db";
    private final String SUBSCRIPTION_KEY = "56cd4047fc5e439f9bd34ffa72dc23fd";
    private final String BASE_URL = "https://api.projectoxford.ai/luis/v2.0/apps/";
    private final String QUERY_URL = BASE_URL + APP_ID + "?subscription-key=" + SUBSCRIPTION_KEY + "&q=";

    private RestTemplate restTemplate;

    @Autowired
    public LuisProcessor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Subscribe
    public void processText(MessageEvent event) {

        String url = QUERY_URL + event.getText();

        try {
            ResponseEntity<LuisQueryResponse> response = restTemplate.getForEntity(url, LuisQueryResponse.class);

            if (response != null && response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                LuisQueryResponse queryResponse = response.getBody();

                LuisIntent intent = queryResponse.getTopIntent();

                if (intent != null) {

                    Intent highestScoredIntent = intent.getIntent();
                    logger.info("LUIS identified Intent: " + highestScoredIntent);

                    eventBus.post(new IntentEvent(event.getUserId(), queryResponse, event.getPlatform()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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
