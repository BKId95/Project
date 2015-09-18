package com.truepus.project.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.trueplus.project.R;
import com.trueplus.project.entity.BannerEntity;
import com.trueplus.project.entity.CategoryEntity;
import com.trueplus.project.entity.HomeEntity;
import com.trueplus.project.entity.ProductEntity;
import com.trueplus.project.fragment.Fragment_Login;

public class MainActivity extends FragmentActivity {
	HomeEntity mHomeEntity;
	private ArrayList<BannerEntity> list_banner = new ArrayList<BannerEntity>();
	private ArrayList<CategoryEntity> list_category = new ArrayList<CategoryEntity>();
	private ArrayList<ProductEntity> list_product = new ArrayList<ProductEntity>();
	private TextView tv_numCart;
	public static final String ACTION = "com.project.NUM_CART";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);


		list_banner.add(new BannerEntity(1, "New Arrivals",
				R.drawable.banner_1, "http://google.com"));
		list_banner.add(new BannerEntity(2, "Store creditpromo",
				R.drawable.banner_2, "http://simicart.com"));
		list_banner.add(new BannerEntity(3, "Switch", R.drawable.banner_3,
				"http://google.com"));
		list_banner.add(new BannerEntity(4, "Joggers", R.drawable.banner_4,
				"http://simicart.com"));

		list_category.add(new CategoryEntity(1, "JOG GERS",
				R.drawable.category_1, null));
		list_category.add(new CategoryEntity(2, "PRO LIFIC",
				R.drawable.category_2, null));
		list_category.add(new CategoryEntity(3, "LONG TEES",
				R.drawable.category_3, null));
		list_category.add(new CategoryEntity(4, "DEN IM",
				R.drawable.category_4, null));
		list_category.add(new CategoryEntity(5, "V BRAND",
				R.drawable.category_5, null));
		list_category.add(new CategoryEntity(6, "TEAM SNAPBACK",
				R.drawable.category_6, null));

		mHomeEntity = new HomeEntity();
		mHomeEntity.setList_banner(list_banner);
		mHomeEntity.setList_category(list_category);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment_Login fragment = new Fragment_Login();
		((Fragment_Login) fragment).setmHomeEntity(mHomeEntity);
		ft.add(R.id.container, fragment);
		ft.commit();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		FragmentManager fm = getSupportFragmentManager();

		if (fm.getBackStackEntryCount() == 1) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("CLOSE APPLICATION");
			builder.setMessage("are you want to exit?");
			builder.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
						}

					});
			builder.setPositiveButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

			AlertDialog dialog = builder.create();
			dialog.show();
		} else {
			// fm.popBackStack();
			
			super.onBackPressed();
		}

	}

}