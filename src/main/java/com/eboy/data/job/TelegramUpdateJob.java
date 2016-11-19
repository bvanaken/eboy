package com.eboy.data.job;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import de.home.cb.bot.event.UpdatesParsedEvent;
import de.home.cb.common.Constants;
import de.home.cb.common.TelebayFactory;
import de.home.cb.telegram.model.Updates;
import de.spinscale.dropwizard.jobs.Job;
import de.spinscale.dropwizard.jobs.annotations.Every;

@Every("20s")
public class TelegramUpdateJob extends Job {
	Logger LOGGER = Logger.getLogger(TelegramUpdateJob.class.getName());

	private OkHttpClient client;
	private ObjectMapper mapper;

	public TelegramUpdateJob() {
		client = new OkHttpClient();
		mapper = new ObjectMapper();
	}

	@Override
	public void doJob() {
		Request request = new Request.Builder().url(Constants.TELEGRAM_UPDATES_URL).build();
		client.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Response response) throws IOException {
				ResponseBody responseBody = response.body();
				if (responseBody == null) {
					return;
				}

				String json = responseBody.string();
				Updates parseUpdates = parseUpdates(json);
				if (parseUpdates == null) {
					return;
				}
				LOGGER.info("updates parsed");
				TelebayFactory.getInstance().getEventBus().post(new UpdatesParsedEvent(parseUpdates));
			}

			@Override
			public void onFailure(Request request, IOException e) {
				System.err.println("request updates failed");
			}
		});
	}

	private Updates parseUpdates(String json) {
		try {
			Updates updates = mapper.readValue(json, Updates.class);
			return updates;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
