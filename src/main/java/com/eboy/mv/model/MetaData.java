package com.eboy.mv.model;

/**
 * Created by root1 on 20/11/16.
 */
public class MetaData {
    public Integer width;
    public Integer height;
    public String format;

    public MetaData(Integer width, Integer height, String format) {
        this.width = width;
        this.height = height;
        this.format = format;
    }

    public MetaData() {
        //
    }
}
