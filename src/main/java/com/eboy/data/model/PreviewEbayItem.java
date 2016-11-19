package com.eboy.data.model;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.home.cb.common.Constants;

public class PreviewEbayItem {
	private static final String DATA_HREF_ATR = "data-href";
	private static final String DIV_TAG = "Div";
	private static final String P_TAG = "p";
	private static final String ADITEM_MAIN_KEY = "aditem-main";
	private static final String STRONG_TAG = "strong";
	private static final String ADITEM_DETAILS_KEY = "aditem-details";
	private static final String DATA_IMGSRC_KEY = "data-imgsrc";
	private static final String ADITEM_ADDON_KEY = "aditem-addon";
	private static final String ADITEM_KEY = "aditem";
	private static final String ADITEM_IMAGE_KEY = "aditem-image";
	private static final String SIMPLE_TAG_CLASS = "simpletag";

	private String imageHref;
	private String excerp;
	private String price;
	private String date;

	private String tags;

	private String deepInformationLink;

	public PreviewEbayItem() {
		// needed
	}

	public PreviewEbayItem(Element element) {
		Elements adItem = element.getElementsByClass(ADITEM_KEY);
		findImageHref(adItem.get(0));
		findDate(adItem.get(0));
		findPrice(adItem.get(0));
		findExcerp(adItem.get(0));
		findTags(adItem.get(0));
		findDeepLink(adItem.get(0));
	}

	private void findDeepLink(Element element) {
		Element adItemImageElem = element.getElementsByClass(ADITEM_IMAGE_KEY).get(0);
		Element divElem = adItemImageElem.getElementsByTag(DIV_TAG).get(0);
		this.deepInformationLink = Constants.SEARCH_HOST + divElem.attr(DATA_HREF_ATR);
	}

	private void findTags(Element element) {
		Elements elementsByClass = element.getElementsByClass(SIMPLE_TAG_CLASS);
		if (elementsByClass.isEmpty()) {
			this.tags = "none";
			return;
		}
		Element tagsElem = elementsByClass.get(0);
		if (tagsElem != null) {
			this.tags = tagsElem.html();
		}
	}

	private void findExcerp(Element element) {
		Element adItemMain = element.getElementsByClass(ADITEM_MAIN_KEY).get(0);
		this.excerp = adItemMain.getElementsByTag(P_TAG).get(0).html();
	}

	private void findPrice(Element element) {
		Elements adItemsDetails = element.getElementsByClass(ADITEM_DETAILS_KEY);
		this.price = adItemsDetails.get(0).getElementsByTag(STRONG_TAG).get(0).html();
	}

	private void findDate(Element element) {
		Elements adItemAddon = element.getElementsByClass(ADITEM_ADDON_KEY);
		this.date = adItemAddon.html();
	}

	private void findImageHref(Element element) {
		Elements adItemImage = element.getElementsByClass(ADITEM_IMAGE_KEY);
		Element imageDiv = adItemImage.get(0).getElementsByTag("div").get(0);
		imageHref = imageDiv.attr(DATA_IMGSRC_KEY);
		// System.out.println(imageDiv.attr("data-href"));
	}

	public String getImageHref() {
		return imageHref;
	}

	public String getExcerp() {
		return excerp;
	}

	public String getPrice() {
		return price;
	}

	public String getDate() {
		return date;
	}

	public String getTags() {
		return tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((excerp == null) ? 0 : excerp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreviewEbayItem other = (PreviewEbayItem) obj;
		if (excerp == null) {
			if (other.excerp != null)
				return false;
		} else if (!excerp.equals(other.excerp))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "PreviewEbayItem [imageHref=" + imageHref + ", excerp=" + excerp + ", price=" + price + ", date=" + date
				+ ", tags=" + tags + ", deepInformationLink=" + deepInformationLink + "]";
	}

	public String getDeepInformationLink() {
		return deepInformationLink;
	}

}
