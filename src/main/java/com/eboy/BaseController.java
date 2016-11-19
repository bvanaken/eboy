package com.eboy;

import com.eboy.data.EbayAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BaseController {

    EbayAdService adService;

    @Autowired
    public BaseController(EbayAdService adService) {
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
//        keywords.add("pro");

        return adService.getAdsForKeywords(keywords);
    }

}
