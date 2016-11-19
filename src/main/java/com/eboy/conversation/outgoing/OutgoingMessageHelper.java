package com.eboy.conversation.outgoing;

import com.eboy.conversation.outgoing.dto.MessageEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OutgoingMessageHelper {

    public enum Domain {
        GENERAL, ONBOARDING, ANIMALS, MARKET, FAQ
    }

    @Value("classpath:general.json")
    private Resource generalDomain;

    private final static String PARAM_PATTERN = "@p@";
    private final static String GOAT_EMOTICON_PATTERN = "@goat@";
    private final static String SHEEP_EMOTICON_PATTERN = "@sheep@";
    private final static String BOT_EMOTICON_PATTERN = "@bot@";

    private static int[] GOAT_EMOTICON = {0x1F410};
    private static int[] SHEEP_EMOTICON = {0x1F40F};
    private static int[] BOT_EMOTICON = {0x1F916};

    private static Map<String, int[]> REPLACE_PATTERNS = new HashMap<>();

    static {
        REPLACE_PATTERNS.put(GOAT_EMOTICON_PATTERN, GOAT_EMOTICON);
        REPLACE_PATTERNS.put(BOT_EMOTICON_PATTERN, BOT_EMOTICON);
        REPLACE_PATTERNS.put(SHEEP_EMOTICON_PATTERN, SHEEP_EMOTICON);
    }

    public Map<String, List<MessageEntry>> loadMessageMap(Domain domain) {

        Resource resource;

        switch (domain) {
            case GENERAL:
                resource = generalDomain;
                break;
            case ONBOARDING:
                resource = onBoardingDomain;
                break;
            case ANIMALS:
                resource = animalDomain;
                break;
            case MARKET:
                resource = marketDomain;
                break;
            case FAQ:
                resource = faqDomain;
                break;
            default:
                return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            Map<String, List<MessageEntry>> value = objectMapper.readValue(resource.getInputStream(), new TypeReference<Map<String, List<MessageEntry>>>() {
            });
            return value;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isRelativeUrl(String url) {
        return !url.startsWith("http://") && !url.startsWith("https://");
    }

    public String fillInPatterns(String content, final String[] params) {
        content = this.convertEmoticons(content);
        if (params != null) {
            content = this.fillInParams(content, params);
        }
        return content;
    }

    private String fillInParams(String content, final String[] params) {

        int index = 0;
        while (content.indexOf(PARAM_PATTERN) > 0 && params.length > index) {
            if (params[index] != null) {
                content = content.replaceFirst(PARAM_PATTERN, params[index]);
                index++;
            }
        }

        return content;
    }

    private String convertEmoticons(String content) {

        for (String pattern : REPLACE_PATTERNS.keySet()) {
            content = content.replaceAll(pattern, stringFromCodepoints(REPLACE_PATTERNS.get(pattern)));
        }

        return content;
    }

    private String stringFromCodepoints(int[] codepoints) {
        return new String(codepoints, 0, codepoints.length);
    }
}
