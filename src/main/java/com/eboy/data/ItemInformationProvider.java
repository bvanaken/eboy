package com.eboy.data;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.home.cb.bot.ItemDeepInformationProvider.Callback;
import de.home.cb.bot.model.EbayItem;
import de.home.cb.bot.model.PreviewEbayItem;
import de.home.cb.common.Constants;
import de.home.cb.common.TelebayFactory;
import de.home.cb.exception.TelebayException;
import de.home.cb.rest.TelebayConfig;
import de.home.cb.utils.UserAgentHelper;

public class ItemInformationProvider {

	private static final String LI_TAG = "li";
	private static final String RESULT_TABLE_ID = "srchrslt-adtable";

	Logger LOGGER = Logger.getLogger(ItemInformationProvider.class.getName());

	private ItemDeepInformationProvider deepInformationProvider;

	private boolean isDebugging;

	private PreviewEbayItem latestEbayItem;

	private String searchString;

	private ChatEngine chatEngine;
	private Long chatId;
	private TelebayConfig config;

	public ItemInformationProvider(boolean isDebugging, String searchString, Long chatId) {
		this.isDebugging = isDebugging;
		this.searchString = searchString;
		this.chatId = chatId;
		chatEngine = new ChatEngine();
		config = TelebayFactory.getInstance().getConfig();
	}

	public void getEbayUpdates() throws IOException {
		String randomUserAgent = UserAgentHelper.getRandomUserAgent();
		LOGGER.info(randomUserAgent);

		Document site = Jsoup.connect(Constants.SEARCH_HOST + searchString).timeout(config.getTimeout())
				.userAgent(randomUserAgent).get();

		Elements items = site.getElementById(RESULT_TABLE_ID).select(LI_TAG);

		Element latestItem = items.get(0);

		if (latestItem == null) {
			throw new TelebayException();
		}

		PreviewEbayItem previewEbayItem = new PreviewEbayItem(latestItem);

		if (latestEbayItem != null && latestEbayItem.equals(previewEbayItem)) {
			return;
		}

		latestEbayItem = previewEbayItem;

		deepInformationProvider = new ItemDeepInformationProvider();
		deepInformationProvider.getDeepInformation(previewEbayItem, new Callback() {

			@Override
			public void onSuccess(EbayItem item) {
				if (!isDebugging) {
					chatEngine.sendEbayItemUpdate(item, chatId);
				}
				LOGGER.info(item);
			}

			@Override
			public void onError(TelebayException exception) {
				LOGGER.error(exception.getMessage());
			}
		});
	}

}
