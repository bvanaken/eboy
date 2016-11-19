package com.eboy.platform.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageEntity {

	private String type;
	private String offset;
	private Integer length;
	private String url;
	private Sender user;

	public MessageEntity() {
		// jersey
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Sender getUser() {
		return user;
	}

	public void setUser(Sender user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "MessageEntity{" +
				"type='" + type + '\'' +
				", offset='" + offset + '\'' +
				", length=" + length +
				", url='" + url + '\'' +
				", user=" + user +
				'}';
	}
}
