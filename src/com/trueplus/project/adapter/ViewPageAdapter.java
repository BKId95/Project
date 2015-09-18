package com.trueplus.project.adapter;

import java.util.ArrayList;

import com.trueplus.project.entity.BannerEntity;
import com.trueplus.project.entity.ProductImage;
import com.trueplus.project.fragment.FragmentBanner;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ViewPageAdapter extends FragmentPagerAdapter {
	ArrayList<BannerEntity> list_Banner;
	
	public ArrayList<BannerEntity> getList_Banner() {
		return list_Banner;
	}

	public void setList_Banner(ArrayList<BannerEntity> list_Banner) {
		this.list_Banner = list_Banner;
	}

	public ViewPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		FragmentBanner fragment = new FragmentBanner(list_Banner.get(arg0));
			return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_Banner.size();
	}

}
