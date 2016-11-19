
package com.eboy.platform.facebook.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Member {

    @JsonProperty("id")
    private String id;

    public Member() {
    }

    private Member(String id) {
        this.id = id;
    }

    public static Member create(String id){
        return new Member(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                '}';
    }
}
