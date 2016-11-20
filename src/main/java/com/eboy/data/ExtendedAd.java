package com.eboy.data;

import com.eboy.data.dto.Ad;
import com.eboy.data.keyPhraseModel.KeyPhraseModel;

/**
 * Created by alex on 20.11.16.
 */
public class ExtendedAd {

    Long id;
    String title;
    String description;
    double price;
    String type;
    double latitude;
    double longitude;
    String status;
    String dateTime;

    String[] keyPhrases;
    public ExtendedAd(Ad ad, KeyPhraseModel keyPhraseModel) {
        this.id = ad.getId();
        this.title = ad.getTitle().getValueAsString();
        this.description = ad.getDescription().getValueAsString();
        this.price = Double.parseDouble(ad.getPrice().getAmount().getValueAsString());
        this.type = ad.getType().getValueAsString();
        this.latitude = Double.parseDouble(ad.getAddress().getLatitude().getValueAsString());
        this.longitude = Double.parseDouble(ad.getAddress().getLongitude().getValueAsString());
        this.status = ad.getStatus().getValueAsString();
        this.dateTime = ad.getDateTime().getValueAsString();
        this.keyPhrases = keyPhraseModel.getKeyPhrases(this.id);
    }
}
