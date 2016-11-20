package com.eboy.mv.model;

/**
 * Created by root1 on 20/11/16.
 */
public class Tag {
    public String name;
    public double confidence;
    public String hint;

    public Tag(String name, double confidence, String hint) {
        this.name = name;
        this.confidence = confidence;
        this.hint = hint;
    }

    public Tag() {
        //
    }
}
