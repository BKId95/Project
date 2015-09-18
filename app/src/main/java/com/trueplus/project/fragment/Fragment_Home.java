package com.trueplus.project.fragment;

import java.util.ArrayList;

import gridview.MyGridView;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.trueplus.project.R;
import com.trueplus.project.adapter.CategoryAdpater;
import com.trueplus.project.adapter.ViewPageAdapter;
import com.trueplus.project.entity.CategoryEntity;
import com.trueplus.project.entity.HomeEntity;
import com.trueplus.project.entity.ProductEntity;
import com.truepus.project.activity.MainActivity;

public class Fragment_Home extends Fragment {

	private HomeEntity mHomeEntity;

	public HomeEntity getmHomeEntity() {
		return mHomeEntity;
	}

	public void setmHomeEntity(HomeEntity mHomeEntity) {
		this.mHomeEntity = mHomeEntity;
	}

	private ViewPager viewPager;
	private EditText ed;
	private TextView tv_numCart;
	public static Boolean cart_Click = false;

	static public Fragment Instance() {
		// TODO Auto-generated constructor stub
		Fragment_Home myFragment = new Fragment_Home();
		return myFragment;
	}

	@Override
	public void onResume() {
		cart_Click = false;
		final InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, null);

		ActionBar mActionBar = getActivity().getActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayUseLogoEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		View actionBarView = mInflater.inflate(R.layout.actionbar, null);
		tv_numCart = (TextView) actionBarView
				.findViewById(R.id.textView_numCart);
		if (FragmentCart.list_cart == null)
			FragmentCart.list_cart = new ArrayList<ProductEntity>();
		tv_numCart.setText("" + FragmentCart.list_cart.size());
		IntentFilter filter = new IntentFilter(MainActivity.ACTION);
		BroadcastReceiver receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				tv_numCart.setText("" + FragmentCart.total());
			}

		};
		getActivity().registerReceiver(receiver, filter);
		ImageView iv_Cart = (ImageView) actionBarView
				.findViewById(R.id.imageView_cart);
		iv_Cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!cart_Click) {
					FragmentManager fm = getActivity()
							.getSupportFragmentManager();
					FragmentTransaction ft = fm.beginTransaction();
					FragmentCart fragment = new FragmentCart();
					ft.replace(R.id.container, fragment);
					ft.addToBackStack(null);
					ft.commit();
					cart_Click = true;
				}
			}
		});

		EditText ed_Search = (EditText) actionBarView
				.findViewById(R.id.editText_search);
		mActionBar.setCustomView(actionBarView);
		mActionBar.setDisplayShowCustomEnabled(true);
		final InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		ViewPageAdapter adapter = new ViewPageAdapter(getChildFragmentManager());
		adapter.setList_Banner(mHomeEntity.getList_banner());
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		viewPager.setAdapter(adapter);
		final CategoryAdpater adapter2 = new CategoryAdpater(getActivity(),
				mHomeEntity.getList_category());
		MyGridView gv = (MyGridView) view.findViewById(R.id.gridView_catergory);
		gv.setExpanded(true);
		gv.setAdapter(adapter2);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				CategoryEntity item = mHomeEntity.getList_category().get(
						position);
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				FragmentListProduct fragment = new FragmentListProduct();
				FragmentListProduct.offset = 0;
				ft.replace(R.id.container, fragment);
				ft.addToBackStack(null);
				ft.commit();

			}
		});
		return view;
	}

}
