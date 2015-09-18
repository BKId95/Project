package com.trueplus.project.entity;

public class ProductImage {
	private int mProductImageID;
	private String mProductImage;


	public ProductImage(int mProductImageID, String mProductImage) {
		super();
		this.mProductImageID = mProductImageID;
		this.mProductImage = mProductImage;
	}

	public int getmProductImageID() {
		return mProductImageID;
	}

	public void setmProductImageID(int mProductImageID) {
		this.mProductImageID = mProductImageID;
	}

	public String getmProductImage() {
		return mProductImage;
	}

	public void setmProductImage(String mProductImage) {
		this.mProductImage = mProductImage;
	}
}
