package com.eboy.data.job;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;

import de.home.cb.bot.ItemInformationProvider;
import de.home.cb.common.TelebayFactory;
import de.home.cb.common.mongo.MongoManaged;
import de.home.cb.rest.model.mongo.User;
import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;

@Every("300s")
public class SearchJob extends Job {

	Logger LOGGER = Logger.getLogger(SearchJob.class.getName());
	private HashMap<Long, ItemInformationProvider> itemInformationProviderMapping;

	public SearchJob() {
		itemInformationProviderMapping = new HashMap<Long, ItemInformationProvider>();
	}

	private Executor executor = Executors.newFixedThreadPool(10);

	@Override
	public void doJob() {
		MongoManaged mongoManaged = TelebayFactory.getInstance().getMongoManaged();

		if (mongoManaged == null) {
			return;
		}

		Datastore dataStore = mongoManaged.getDataStore();
		List<User> users = dataStore.createQuery(User.class).asList();
		for (final User user : users) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(5000);
						updateUser(user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	private void updateUser(User user) throws IOException {
		String searchString = user.getSearchString();
		LOGGER.info(searchString);
		Long chatId = user.getChatId();

		ItemInformationProvider itemInformationProvider = null;

		if (itemInformationProviderMapping.containsKey(chatId)) {
			itemInformationProvider = itemInformationProviderMapping.get(chatId);
		} else {
			boolean debugging = TelebayFactory.getInstance().getConfig().isDebugging();
			itemInformationProvider = new ItemInformationProvider(debugging, searchString, chatId);
		}

		itemInformationProvider.getEbayUpdates();
	}

}
