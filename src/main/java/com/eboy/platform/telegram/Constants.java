package com.eboy.platform.telegram;

/**
 * Created by root1 on 20/11/16.
 */
public class Constants {

    public static final String TOKEN = "276165906:AAFyFqmodHM-ji8nhNu0XtYBrxbT57iGcu0";

    public static final String DOMAIN = "https://api.telegram.org/";

    public static final String BOT_TOKEN_URL = "https://api.telegram.org/bot" + TOKEN + "/";

    private static final String GET_FILE = "https://api.telegram.org/bot" + TOKEN + "/getFile";

    private static final String DOWNLOAD_FILE_URL = DOMAIN + "file/bot" + TOKEN + "/";

    public static String getFileUrl(String fileId) {
        return GET_FILE + "?file_id=" + fileId;
    }

    public static String getFilePath(String filePath) {
        return DOWNLOAD_FILE_URL + filePath;
    }
}
