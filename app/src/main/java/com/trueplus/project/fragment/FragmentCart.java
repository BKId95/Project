package com.trueplus.project.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.trueplus.project.R;
import com.trueplus.project.adapter.ListCartAdapter;
import com.trueplus.project.entity.ProductEntity;
import com.truepus.project.activity.MainActivity;

public class FragmentCart extends Fragment {

	public static ArrayList<ProductEntity> list_cart = null;
	public static ListCartAdapter adapter;
	public static ListView lv;
	public static TextView tv_total;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_cart, null);
		if (list_cart == null)
			list_cart = new ArrayList<ProductEntity>();
		lv = (ListView) view.findViewById(R.id.listView_cart);
		adapter = new ListCartAdapter(getActivity(), list_cart);
		lv.setAdapter(adapter);
		tv_total = (TextView) view.findViewById(R.id.textView_total_money);
		total_money();
		TextView tv_update = (TextView) view
				.findViewById(R.id.textView_cart_update);
		tv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				total_money();
				Intent intent = new Intent(MainActivity.ACTION);
				getActivity().sendBroadcast(intent);
			}
		});

		TextView tv_clear = (TextView) view
				.findViewById(R.id.textView_cart_clear);
		tv_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list_cart.clear();
				adapter.notifyDataSetChanged();
				lv.setAdapter(adapter);
				total_money();
				Intent intent = new Intent(MainActivity.ACTION);
				getActivity().sendBroadcast(intent);
			}
		});
		return view;
	}

	public static void total_money() {
		int s = 0;
		if (list_cart == null) {
			tv_total.setText("0");
			return;
		}

		for (int i = 0; i < list_cart.size(); i++) {
			s += list_cart.get(i).getNum()
					* Integer.parseInt(list_cart.get(i).getmProductPrice());
		}
		tv_total.setText("Total: $" + s);
	}

	public static int total() {
		int total = 0;
		for (int i = 0; i < list_cart.size(); i++) {
			total += list_cart.get(i).getNum();
		}
		return total;
	}
}
