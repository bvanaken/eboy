package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Ad {

    @JsonProperty
    Long id;

    @JsonProperty
    Price price;

    @JsonProperty(value = "ad-type")
    Field type;

    @JsonProperty
    Field title;

    @JsonProperty
    Field description;

    @JsonProperty(value = "ad-address")
    Address address;

    @JsonProperty(value = "ad-status")
    Field status;

    @JsonProperty(value = "start-date-time")
    Field dateTime;

    @JsonProperty(value = "link")
    ArrayList<Link> links;

    @JsonProperty(value = "pictures")
    Pictures pictures;

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

    public Field getDescription() {
        return description;
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

    public Date getDateTime() {

        String dateString = (String) dateTime.getValue();

        if (dateString.length() == 28) {
            dateString = dateString.substring(0, dateString.length() - 9);
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            return dateFormatter.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public Pictures getPictures() {
        return pictures;
    }

    @JsonIgnore
    public String getPublicPicture() {

        if (pictures != null && pictures.getPicture() != null && !pictures.getPicture().isEmpty()) {

            Picture pic = pictures.getPicture().get(0);

            for (Link link : pic.getLinks()) {
                if (link.getRel().equals("teaser")) {
                    return link.getHref();
                }
            }

        }
        return null;
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
                ", description=" + description +
                ", address=" + address +
                ", status=" + status +
                ", dateTime=" + dateTime +
                ", links=" + links +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void setType(Field type) {
        this.type = type;
    }

    public void setTitle(Field title) {
        this.title = title;
    }

    public void setDescription(Field description) {
        this.description = description;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setStatus(Field status) {
        this.status = status;
    }

    public void setDateTime(Field dateTime) {
        this.dateTime = dateTime;
    }

    public void setLinks(ArrayList<Link> links) {
        this.links = links;
    }
}
