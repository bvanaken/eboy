
package com.eboy.platform.facebook.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entry {

    @JsonProperty("id")
    private String id;
    @JsonProperty("time")
    private Long time;
    @JsonProperty("messaging")
    private List<Event> events = new ArrayList<Event>();

    public Entry() {
    }

    public Entry(String id, Long time, List<Event> events) {
        this.id = id;
        this.time = time;
        this.events = events;
    }

    public String getId() {
        return id;
    }

    public Long getTime() {
        return time;
    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", events=" + events +
                '}';
    }
}
