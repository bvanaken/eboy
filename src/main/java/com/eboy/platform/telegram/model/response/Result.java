package com.eboy.platform.telegram.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty("file_id")
    public String fileId;

    @JsonProperty("file_size")
    public String fileSize;

    @JsonProperty("file_path")
    public String filePath;

}
