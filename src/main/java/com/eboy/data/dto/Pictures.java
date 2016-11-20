package com.eboy.data.dto;

import java.util.ArrayList;

public class Pictures {

    ArrayList<Picture> picture;

    public Pictures() {
    }

    public ArrayList<Picture> getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "Pictures{" +
                "picture=" + picture +
                '}';
    }
}
