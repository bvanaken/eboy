package com.eboy.data;

import com.eboy.data.exception.TelebayException;
import com.eboy.data.model.EbayItem;
import com.eboy.data.model.PreviewEbayItem;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ItemDeepInformationProvider {

	private static final String VIEWAD_MAIN_ID = "viewad-main";
	Logger LOGGER = Logger.getLogger(ItemDeepInformationProvider.class.getName());
	private TimerTask task;
	private Timer timer;
	private TelebayConfig config;

	public ItemDeepInformationProvider() {
		config = TelebayFactory.getInstance().getConfig();
	}

	public interface Callback {
		void onError(TelebayException exception);

		void onSuccess(EbayItem item);
	}

	public void getDeepInformation(final PreviewEbayItem previewEbayItem, final Callback callback) {
		task = new TimerTask() {

			@Override
			public void run() {
				try {
					Document site = Jsoup.connect(previewEbayItem.getDeepInformationLink()).timeout(config.getTimeout())
							.userAgent(UserAgentHelper.getRandomUserAgent()).get();
					Element mainElement = site.getElementById(VIEWAD_MAIN_ID);
					LOGGER.info(mainElement);
					EbayItem ebayItem = new EbayItem(previewEbayItem, mainElement);
					callback.onSuccess(ebayItem);
				} catch (IOException e) {
					e.printStackTrace();
					callback.onError(new TelebayException());
				}
			}
		};

		timer = new Timer();
		timer.schedule(task, 5000);
	}

	public void cancelProvider() {
		if (timer != null) {
			timer.cancel();
		}
	}

}
