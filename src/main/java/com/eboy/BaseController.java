package com.eboy;

import com.eboy.data.EbayAdService;
import com.eboy.data.MsAnalyticService.MsTextAnalyticService;
import com.eboy.data.dto.Ad;
import com.eboy.mv.ComputerVision;
import com.eboy.platform.Platform;
import com.eboy.subscriptions.SubscriptionPersister;
import com.eboy.subscriptions.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class BaseController {

    MsTextAnalyticService textAnalyser;
    EbayAdService adService;
    ComputerVision imageAnalyzer;
    SubscriptionPersister persister;

    @Autowired
    public BaseController(EbayAdService adService, MsTextAnalyticService textAnalyzer, ComputerVision imageAnalyzer, SubscriptionPersister persister) {
        this.textAnalyser = textAnalyzer;
        this.adService = adService;
        this.imageAnalyzer = imageAnalyzer;
        this.persister = persister;
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

    @RequestMapping("/subscribe")
    public void subscribe() {

        String key = "Deutsche Bahn";

        persister.persistSubscription(key, new Subscription(123L, Platform.FACEBOOK, new Date(1507500000000L), 1234L, key, 12.4f));

        System.out.println("result: " + persister.getSubscriptions(key));
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

    @RequestMapping("/analyzeImage")
    public String getCategorieOfAnImage() {
        return this.imageAnalyzer.analyzeImage(
                //TODO: image url should be the telegram fileserver image url sent to the chatbot.
                "{\"url\": \"http://assets.inhabitat.com/wp-content/blogs.dir/1/files/2015/12/Fortified-Bicycle-Invincible-Theft-Proof-Bike-10.jpg\"}"
        );
    }
}

