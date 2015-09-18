package com.trueplus.project.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.trueplus.project.R;
import com.trueplus.project.entity.BannerEntity;

public class FragmentBanner extends Fragment {
	public FragmentBanner(BannerEntity banner) {
		super();
		this.banner = banner;
	}

	private BannerEntity banner;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_banner, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageView_category);
		imageView.setImageResource(banner.getmBannerImage());
		imageView.setOnClickListener(new OnClickListener(
				) {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(banner.getmBannerLink()));
				startActivity(intent);
			}
		});
		return view;
	}

}
