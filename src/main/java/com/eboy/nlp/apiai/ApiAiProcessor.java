package com.eboy.nlp.apiai;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import com.eboy.nlp.Intent;
import com.eboy.nlp.TextProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Qualifier(value = ApiAiProcessor.QUALIFIER)
public class ApiAiProcessor implements TextProcessor {

    public static final String QUALIFIER = "ApiAiProcessor";
    private final static Logger logger = Logger.getLogger(ApiAiProcessor.class.getName());

    private final String CLIENT_ACCESS_TOKEN = "8dab2b309b63484ca7c10e796a8a5b1a";

    AIConfiguration configuration;
    AIDataService dataService;

    public ApiAiProcessor() {
        configuration = new AIConfiguration(CLIENT_ACCESS_TOKEN);
        dataService = new AIDataService(configuration);
    }

    @Override
    public Intent getIntentFromText(String text) {

        AIRequest request = new AIRequest(text);

        try {
            AIResponse response = dataService.request(request);
            String intentName = response.getResult().getMetadata().getIntentName();

            if (intentName != null) {

                return Intent.valueOf(intentName);

            } else {
                logger.warning("No intent identified for text '" + text + "'.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
