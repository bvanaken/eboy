package com.eboy;

import com.eboy.data.EbayAdService;
import com.eboy.data.ExtendedAd;
import com.eboy.data.MsAnalyticService.MsTextAnalyticService;
import com.eboy.data.dto.Ad;
import com.eboy.data.keyPhraseModel.KeyPhraseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




@RestController
public class BaseController {

    MsTextAnalyticService textAnalyser;
    EbayAdService adService;

    @Autowired
    public BaseController(EbayAdService adService, MsTextAnalyticService textAnalyzer) {
        this.textAnalyser = textAnalyzer;
        this.adService = adService;
    }

    @RequestMapping("/")
    public String wakeUp() {

        return "Bot is awake now :)";
    }

    @RequestMapping("/ads")
    public List getAds() {

        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("macbook");
        keywords.add("pro");
        keywords.add("2014");


        return adService.getAdsForKeywords(keywords);
    }

    @RequestMapping("/analyze")
    public String getAnalyseStrings() {

        /* Test JSON-String */
        String[] text = new String[]{"Macbook Pro Mid 2014, Retina i5 2.8 GHz,512 GB SSD, Top Zustand",
        "Verkaufe meinen Macbook Retina Pro 13 von mitte 2014. Das Gerät ist wie neu, keinerlei...",
        "MacBook Pro 13 Zoll Retina 2014 + Magic Mouse2"};
        return this.textAnalyser.analyzeStrings(text);
    }

    @RequestMapping("/analyze-ads")
    public String getAnalyseAds() {

        List<Ad> ads = this.getAds();
        return this.textAnalyser.analyzeAds(ads);
    }

    @RequestMapping("/find-mac-ssd-gold")
    public String getMacSSD() throws IOException {

        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("macbook");
        keywords.add("ssd");

        List<Ad> ads = adService.getAdsForKeywords(keywords);

        ObjectMapper mapper = new ObjectMapper();
        String json  = this.textAnalyser.analyzeAds(ads);
        KeyPhraseModel keyPhrases = mapper.readValue( json, KeyPhraseModel.class);

        ArrayList<ExtendedAd> extAds = new ArrayList<>();

        for (Ad ad: ads) {

            extAds.add(new ExtendedAd(ad, keyPhrases));
        }

        return "Sucess!";
    }
}

