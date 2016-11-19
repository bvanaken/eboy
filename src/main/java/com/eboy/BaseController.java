package com.eboy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @RequestMapping("/")
    public String wakeUp() {

        return "Bot is awake now :)";
    }

}
