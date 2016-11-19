package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ad {

    @JsonProperty
    Long id;

    @JsonProperty
    Price price;

    @JsonProperty(value = "ad-type")
    Field type;

    @JsonProperty
    Field title;

    @JsonProperty(value = "ad-address")
    Address address;

    @JsonProperty(value = "ad-status")
    Field status;

    @JsonProperty(value = "start-date-time")
    Field dateTime;

    @JsonProperty(value = "link")
    List<Link> links;

    public Ad() {
    }

    public Long getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public Field getType() {
        return type;
    }

    public Field getTitle() {
        return title;
    }

    public Address getAddress() {
        return address;
    }

    public Field getStatus() {
        return status;
    }

    public Field getDateTime() {
        return dateTime;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getPublicUrl() {

        for (Link link : links) {
            if (link.getRel().equals("self-public-website")) {
                return link.getHref();
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", price=" + price +
                ", type=" + type +
                ", title=" + title +
                ", address=" + address +
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", links=" + links +
                '}';
    }
}
