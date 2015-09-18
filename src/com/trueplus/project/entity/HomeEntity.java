package com.trueplus.project.entity;

import java.util.ArrayList;

public class HomeEntity {
	private ArrayList<BannerEntity> list_banner;
	private ArrayList<CategoryEntity> list_category;

	public HomeEntity(ArrayList<BannerEntity> list_banner,
			ArrayList<CategoryEntity> list_category) {
		super();
		this.list_banner = list_banner;
		this.list_category = list_category;
	}

	public ArrayList<BannerEntity> getList_banner() {
		return list_banner;
	}

	public void setList_banner(ArrayList<BannerEntity> list_banner) {
		this.list_banner = list_banner;
	}

	public ArrayList<CategoryEntity> getList_category() {
		return list_category;
	}

	public void setList_category(ArrayList<CategoryEntity> list_category) {
		this.list_category = list_category;
	}

	public HomeEntity() {
		super();
	}
}
