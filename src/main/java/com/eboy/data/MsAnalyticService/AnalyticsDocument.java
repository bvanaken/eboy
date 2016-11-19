package com.eboy.data.MsAnalyticService;

/**
 * Created by alex on 19.11.16.
 */
public class AnalyticsDocument {

    final String LANG = "de";
    String language;
    String text;
    long id;

    AnalyticsDocument(long id, String text) {
        this.id = id;
        this.text = text;
        this.language = LANG;
    }

    public String toJSON() {
        return "{ \"language\": \"" + language +
                "\", \"text\": \"" + text +
                "\", \"id\": \"" + id +
                "\"}";
    }
}
