package com.eboy.platform.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Update {

	@JsonProperty("update_id")
	private Integer updateId;

	private Message message;

	@JsonProperty("edit_message")
	private Message editMessage;

	public Update() {
		// jersey
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Message getEditMessage() {
		return editMessage;
	}

	public void setEditMessage(Message editMessage) {
		this.editMessage = editMessage;
	}

	@Override
	public String toString() {
		return "Update{" +
				"updateId=" + updateId +
				", message=" + message +
				", editMessage=" + editMessage +
				'}';
	}
}
