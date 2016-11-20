package com.eboy.platform.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by root1 on 20/11/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TelegramFile {
    @JsonProperty("file_id")
    private String fileId;
    @JsonProperty("file_size")
    private int fileSize;

    private int width;
    private int height;
}
