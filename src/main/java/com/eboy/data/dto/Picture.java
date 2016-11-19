package com.eboy.data.dto;

import java.util.List;

public class Picture {

    List<Link> links;

    public Picture() {
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getTeaserUrl() {

        for (Link link : links) {
            if (link.getRel().equals("teaser")) {
                return link.getHref();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "links=" + links +
                '}';
    }
}
