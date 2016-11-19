package com.eboy.data;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.home.cb.common.mongo.Mongo;
import io.dropwizard.Configuration;

public class TelebayConfig extends Configuration {

	public static final int ONE_SECOND = 60000;

	@NotEmpty
	private String template;

	@NotEmpty
	private String defaultName = "EbayKleinanzeigenBot";

	private boolean debugging = true;

	@NotEmpty
	private String search;

	@NotEmpty
	private String chatId;

	private Integer minuteCount;

	private Integer timeout;

	@JsonProperty
	private Mongo mongo;

	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}

	@JsonProperty
	public void setDefaultName(String name) {
		this.defaultName = name;
	}

	@JsonProperty
	public boolean isDebugging() {
		return debugging;
	}

	@JsonProperty
	public void setDebugging(boolean debugging) {
		this.debugging = debugging;
	}

	@JsonProperty
	public String getSearch() {
		return search;
	}

	@JsonProperty
	public void setSearch(String search) {
		this.search = search;
	}

	@JsonProperty
	public String getChatId() {
		return chatId;
	}

	@JsonProperty
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	@JsonProperty
	public Integer getMinuteCount() {
		return minuteCount;
	}

	@JsonProperty
	public void setMinuteCount(Integer minuteCount) {
		this.minuteCount = minuteCount;
	}

	@JsonProperty
	public Integer getTimeout() {
		return timeout;
	}

	@JsonProperty
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getPeriod() {
		return ONE_SECOND * minuteCount;
	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

}
