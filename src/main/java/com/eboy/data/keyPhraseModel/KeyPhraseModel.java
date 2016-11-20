package com.eboy.data.keyPhraseModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

/**
 * Created by alex on 20.11.16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyPhraseModel {

    public List<Document> documents;

    public KeyPhraseModel() {
    }

    public String[] getKeyPhrases(Long id) {
        for (int i = 0; i < documents.size(); i++) {
            Long newid = documents.get(i).id;
            String[] newprhase = documents.get(i).keyPhrases;
            if (newid.equals(id)) {
                return documents.get(i).keyPhrases;
            }
        }
        return null;
    }
}

