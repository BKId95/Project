package com.trueplus.project.entity;

import java.util.ArrayList;

public class ProductEntity {
	private String mProductID;
	private String mProductName;
	private String mProductStock;
	private String mProductSymboy;
	private String mProductPrice;
	private String mProductDesciption;
	private String mProductRate;
	private ArrayList<ProductImage> list_image;
	private ArrayList<ProductEntity> list_productRelated;
	private String image;
	private int num = 1;
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getmProductRate() {
		return mProductRate;
	}

	public void setmProductRate(String mProductRate) {
		this.mProductRate = mProductRate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getmProductID() {
		return mProductID;
	}

	public void setmProductID(String mProductID) {
		this.mProductID = mProductID;
	}

	public String getmProductName() {
		return mProductName;
	}

	public void setmProductName(String mProductName) {
		this.mProductName = mProductName;
	}

	public String getmProductStock() {
		return mProductStock;
	}

	public void setmProductStock(String string) {
		this.mProductStock = string;
	}

	public String getmProductSymboy() {
		return mProductSymboy;
	}

	public void setmProductSymboy(String mProductSymboy) {
		this.mProductSymboy = mProductSymboy;
	}

	public String getmProductPrice() {
		return mProductPrice;
	}

	public void setmProductPrice(String mProductPrice) {
		this.mProductPrice = mProductPrice;
	}

	public String getmProductDesciption() {
		return mProductDesciption;
	}

	public void setmProductDesciption(String mProductDesciption) {
		this.mProductDesciption = mProductDesciption;
	}

	public ArrayList<ProductImage> getList_image() {
		return list_image;
	}

	public void setList_image(ArrayList<ProductImage> list_image) {
		this.list_image = list_image;
	}

	public ArrayList<ProductEntity> getList_productRelated() {
		return list_productRelated;
	}

	public void setList_productRelated(
			ArrayList<ProductEntity> list_productRelated) {
		this.list_productRelated = list_productRelated;
	}

//	public ProductEntity(String mProductID, String mProductName,
//			String mProductStock, String mProductSymboy, String mProductPrice,
//			String mProductDesciption, ArrayList<ProductImage> list_image,
//			ArrayList<ProductEntity> list_productRelated) {
//		super();
//		this.mProductID = mProductID;
//		this.mProductName = mProductName;
//		this.mProductStock = mProductStock;
//		this.mProductSymboy = mProductSymboy;
//		this.mProductPrice = mProductPrice;
//		this.mProductDesciption = mProductDesciption;
//		this.list_image = list_image;
//		this.list_productRelated = list_productRelated;
//	}
//
//	public ProductEntity() {
//		// TODO Auto-generated constructor stub
//		super();
//	}
}
