package com.eboy.data.keyPhraseModel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by alex on 20.11.16.
 */
public class Document {

    public String[] keyPhrases;
    public Long id;

    public String[] getKeyPhrases() {
        return keyPhrases;
    }

    public Long getId() {
        return id;
    }
}
