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

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {
        //
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return name != null ? name.equals(tag.name) : tag.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
