package com.trueplus.project.adapter;

import java.util.ArrayList;

import com.trueplus.project.R;
import com.trueplus.project.entity.CategoryEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class CategoryAdpater extends BaseAdapter {
	Context context;
	ArrayList<CategoryEntity> list_category;
	LayoutInflater inflater;

	public CategoryAdpater(Context context,
			ArrayList<CategoryEntity> list_category) {
		super();
		this.context = context;
		this.list_category = list_category;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_category.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_category.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView im;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.category_item, null);
			im = (ImageView) convertView.findViewById(R.id.imageView_category);
			convertView.setTag(im);
		} else {
			im = (ImageView) convertView.getTag();
		}
		im.setImageResource(list_category.get(position).getmCategoryImage());
		return convertView;
	}

}
