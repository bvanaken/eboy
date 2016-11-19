package com.eboy.data.job;

import org.mongodb.morphia.Datastore;

import com.mongodb.DBCollection;

import de.home.cb.common.TelebayFactory;
import de.home.cb.common.mongo.MongoManaged;
import de.home.cb.rest.model.mongo.TelegramUpdate;
import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;

@Every("500s")
public class ClearUpdatesJob extends Job {

	@Override
	public void doJob() {
		MongoManaged mongoManaged = TelebayFactory.getInstance().getMongoManaged();
		if (mongoManaged == null) {
			return;
		}

		Datastore dataStore = mongoManaged.getDataStore();
		DBCollection telegramUpdateCollection = dataStore.getCollection(TelegramUpdate.class);

		if (telegramUpdateCollection == null) {
			return;
		}

		telegramUpdateCollection.drop();
	}

}
