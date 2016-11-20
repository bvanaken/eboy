package com.eboy.platform.telegram;

/**
 * Created by root1 on 20/11/16.
 */
public class Constants {

    public static final String TOKEN = "276165906:AAFyFqmodHM-ji8nhNu0XtYBrxbT57iGcu0";

    public static final String BOT_DOMAIN = "https://api.telegram.org/bot" + TOKEN + "/";

    private static final String GET_FILE = "https://api.telegram.org/bot" + TOKEN + "/getFile";

    public static String getFileUrl(String fileId) {
        return GET_FILE + "?file_id=" + fileId;
    }

    public static String getFilePath(String filePath) {
        return BOT_DOMAIN + filePath;
    }
}
