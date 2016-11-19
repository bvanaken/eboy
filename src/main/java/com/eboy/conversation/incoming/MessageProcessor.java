package com.eboy.conversation.incoming;

import com.eboy.nlp.Intent;
import com.eboy.nlp.TextProcessor;
import com.eboy.nlp.apiai.ApiAiProcessor;
import com.eboy.platform.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MessageProcessor {

    private TextProcessor textProcessor;

    @Autowired
    public MessageProcessor(@Qualifier(ApiAiProcessor.QUALIFIER) TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    public void processMessage(String text, String userId, Platform platform, EventProcessor processor) {
        Assert.notNull(text);
        Assert.notNull(userId);

        Intent intent = textProcessor.getIntentFromText(text);

        if (intent != null) {
            processor.onIntentDetected(intent, userId, platform);
        } else {
            processor.onIntentNotDetected(userId, platform);
        }
    }

}
