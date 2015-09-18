package com.trueplus.project.adapter;

import java.util.ArrayList;

import com.trueplus.project.entity.ProductImage;
import com.trueplus.project.fragment.FragmentProductImage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapterProduct extends FragmentPagerAdapter{
	
	ArrayList<ProductImage> list_image;
	
	public ArrayList<ProductImage> getList_image() {
		return list_image;
	}

	public void setList_image(ArrayList<ProductImage> list_image) {
		this.list_image = list_image;
	}

	public ViewPagerAdapterProduct(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		FragmentProductImage fragment = new FragmentProductImage();
		fragment.setImage(list_image.get(arg0));
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_image.size();
	}

}
