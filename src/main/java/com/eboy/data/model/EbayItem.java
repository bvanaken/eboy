package com.eboy.data.model;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EbayItem {

	private static final String SRC_ATR = "src";
	private static final String IMG_TAG = "img";
	private static final String AD_IMAGE_CLASS = "ad-image";
	private static final String VIEWAD_IMAGE_ID = "viewad-image";
	private static final String CONTENT_KEY = "content";
	private static final String PRICE_VALUE = "price";
	private static final String OFFER_DETAILS_ATR_VALUE = "offerDetails";
	private static final String ITEMPROP_ATR_KEY = "itemprop";

	private static final String VIEWAD_LOCALITY_ID = "viewad-locality";

	private static final String VIEWAD_CNTR_NUM_ID = "viewad-cntr-num";

	private static final String VIEWAD_DESCRIPTION_TEXT_ID = "viewad-description-text";
	private static final String CURRENCY_VALUE = "currency";

	private PreviewEbayItem previewEbayItem;

	private String description;
	private List<String> pictures;
	private String address;
	private String clicks;
	private String price;
	private String currency;
	private String locality;

	public EbayItem(final PreviewEbayItem previewEbayItem, Element mainElement) {
		this.previewEbayItem = previewEbayItem;

		pictures = new LinkedList<String>();

		findDescription(mainElement);

		Element offerDetailsElem = mainElement.getElementsByAttributeValue(ITEMPROP_ATR_KEY, OFFER_DETAILS_ATR_VALUE)
				.get(0);
		findClicks(offerDetailsElem);
		findLocality(offerDetailsElem);
		findPriceAndCurrency(offerDetailsElem);

		findPictures(mainElement);
	}

	private void findPictures(Element mainElement) {
		Element imageContainer = mainElement.getElementById(VIEWAD_IMAGE_ID);
		Elements imageDivs = imageContainer.getElementsByClass(AD_IMAGE_CLASS);
		for (Element element : imageDivs) {
			Element imageElem = element.getElementsByTag(IMG_TAG).get(0);
			if (imageElem == null) {
				continue;
			}
			pictures.add(imageElem.attr(SRC_ATR));

		}

	}

	private void findPriceAndCurrency(Element offerDetailsElem) {
		Element priceProp = offerDetailsElem.getElementsByAttributeValue(ITEMPROP_ATR_KEY, PRICE_VALUE).get(0);
		this.price = priceProp.attr(CONTENT_KEY);

		Element currencyProp = offerDetailsElem.getElementsByAttributeValue(ITEMPROP_ATR_KEY, CURRENCY_VALUE).get(0);
		this.currency = currencyProp.attr(CONTENT_KEY);
	}

	private void findLocality(Element offerDetailsElem) {
		Element localityElem = offerDetailsElem.getElementById(VIEWAD_LOCALITY_ID);
		if (localityElem != null) {
			this.locality = localityElem.html();
		}
	}

	private void findClicks(Element mainElement) {
		Element clicksElem = mainElement.getElementById(VIEWAD_CNTR_NUM_ID);
		this.clicks = clicksElem.html();
	}

	private void findDescription(Element mainElement) {
		Element descriptionElem = mainElement.getElementById(VIEWAD_DESCRIPTION_TEXT_ID);
		this.description = descriptionElem.html();
	}

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public PreviewEbayItem getPreviewEbayItem() {
		return previewEbayItem;
	}

	public void setPreviewEbayItem(PreviewEbayItem previewEbayItem) {
		this.previewEbayItem = previewEbayItem;
	}

	public String getClicks() {
		return clicks;
	}

	public String getPrice() {
		return price;
	}

	public String getCurrency() {
		return currency;
	}

	public String getLocality() {
		return locality;
	}

	public List<String> getPictures() {
		return pictures;
	}

	@Override
	public String toString() {
		return "EbayItem [previewEbayItem=" + previewEbayItem + ", description=" + description + ", pictures="
				+ pictures + ", address=" + address + ", clicks=" + clicks + ", price=" + price + ", currency="
				+ currency + ", locality=" + locality + "]";
	}
}
