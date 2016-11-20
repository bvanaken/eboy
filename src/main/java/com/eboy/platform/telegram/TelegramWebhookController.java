package com.eboy.platform.telegram;

import com.eboy.data.EbayAdService;
import com.eboy.mv.ComputerVision;
import com.eboy.nlp.luis.LuisProcessor;
import com.eboy.platform.telegram.model.Message;
import com.eboy.platform.telegram.model.TelegramFile;
import com.eboy.platform.telegram.model.response.FileResponse;
import com.google.common.eventbus.EventBus;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TelegramWebhookController {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private LuisProcessor luisProcessor;

    @Autowired
    private EbayAdService ebayAdService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ComputerVision imageAnalyzer;

    private final static Logger logger = Logger.getLogger(TelegramWebhookController.class.getName());

    @RequestMapping(value = "/telegram/webhook", method = POST)
    public void receiveUpdate(@RequestBody com.eboy.platform.telegram.model.Update update) throws IOException {
        logger.info("Chat id: " + update.toString());

        Long chatId = update.getMessage().getChat().getId();
        String text = update.getMessage().getText();

        Message message = update.getMessage();
        boolean isTextMessage = message.getText() != null;
        boolean isPhotoMessage = message.getPhoto() != null && message.getPhoto().length > 0;

        if (isTextMessage) {
            // do luis stuff
            return;
        } else {
            if (isPhotoMessage) {
                handleTelegramPhoto(message);
                return;
            }
            // rip
            return;
        }
    }

    private void handleTelegramPhoto(Message message) {
        // do image recognition
        // getFile from telegram

        TelegramBot bot = TelegramBotAdapter.build(Constants.TOKEN);
        int length = message.getPhoto().length;
        TelegramFile[] photo = message.getPhoto();
        TelegramFile telegramFile = photo[length - 1];

        String fileId = telegramFile.fileId;

        String getFileUrl = Constants.getFileUrl(fileId);

        ResponseEntity<FileResponse> entity = restTemplate.getForEntity(getFileUrl, FileResponse.class);

        String filePath = entity.getBody().result.filePath;

        String fileUrl = Constants.getFilePath(filePath);

        String keyword = imageAnalyzer.analyzeImage(fileUrl);

        logger.info(keyword);
    }

    public String getUrlObject(String filePath) {
        return "{\"url\":" + filePath + "}";
    }
}
