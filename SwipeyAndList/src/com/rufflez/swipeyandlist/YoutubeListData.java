package com.rufflez.swipeyandlist;

public class YoutubeListData {
	private String mTextData;
	private String mTurl;

	public void setTextData(String text) {
		mTextData = text;
	}
	public void setThumbnailUrl(String text) {
		mTurl = text;
	}

	public String getTextData() {
		return mTextData;
	}
	public String getThumbnailUrl() {
		return mTurl;
	}
}
