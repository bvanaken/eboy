package com.eboy.data;

import com.google.common.eventbus.EventBus;

import de.home.cb.common.mongo.MongoManaged;
import de.home.cb.rest.TelebayConfig;

public class TelebayFactory {

	private static TelebayFactory telebayFactory;

	public static TelebayFactory getInstance() {
		if (telebayFactory == null) {
			telebayFactory = new TelebayFactory();
		}
		return telebayFactory;
	}

	private TelebayConfig config;
	private MongoManaged mongoManaged;

	private EventBus eventBus;

	public TelebayConfig getConfig() {
		return config;
	}

	public void setConfig(TelebayConfig config) {
		this.config = config;
	}

	public MongoManaged getMongoManaged() {
		return mongoManaged;
	}

	public void setMongoManaged(MongoManaged mongoManaged) {
		this.mongoManaged = mongoManaged;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}
