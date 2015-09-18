package com.trueplus.project.entity;

public class BannerEntity {
	private int mBannerID;
	private String mBannerName;
	private int mBannerImage;
	private String mBannerLink;

	public int getmBannerID() {
		return mBannerID;
	}

	public void setmBannerID(int mBannerID) {
		this.mBannerID = mBannerID;
	}

	public String getmBannerName() {
		return mBannerName;
	}

	public void setmBannerName(String mBannerName) {
		this.mBannerName = mBannerName;
	}

	public int getmBannerImage() {
		return mBannerImage;
	}

	public void setmBannerImage(int mBannerImage) {
		this.mBannerImage = mBannerImage;
	}

	public String getmBannerLink() {
		return mBannerLink;
	}

	public void setmBannerLink(String mBannerLink) {
		this.mBannerLink = mBannerLink;
	}

	public BannerEntity(int mBannerID, String mBannerName, int mBannerImage,
			String mBannerLink) {
		super();
		this.mBannerID = mBannerID;
		this.mBannerName = mBannerName;
		this.mBannerImage = mBannerImage;
		this.mBannerLink = mBannerLink;
	}
}
