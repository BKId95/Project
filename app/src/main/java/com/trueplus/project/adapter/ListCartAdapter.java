package com.trueplus.project.adapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.trueplus.project.R;
import com.trueplus.project.entity.ProductEntity;
import com.trueplus.project.fragment.FragmentCart;
import com.truepus.project.activity.MainActivity;

public class ListCartAdapter extends BaseAdapter {

	ArrayList<ProductEntity> list;
	Context context;
	LayoutInflater mInflater;

	public ListCartAdapter(Context context, ArrayList<ProductEntity> list) {
		super();
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.cart_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.imageView_cart_item);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.textView_cart_item_name);
			holder.tv_money = (TextView) convertView
					.findViewById(R.id.textView_cart_item_money);
			holder.tv_delete = (TextView) convertView
					.findViewById(R.id.textView_cart_item_delete);
			holder.ed_num = (EditText) convertView.findViewById(R.id.editText1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ProductEntity item = list.get(position);
		new DownloadImageTask(holder.image).execute(item.getList_image().get(0)
				.getmProductImage());
		holder.tv_name.setText(item.getmProductName());
		holder.tv_money.setText("$" + item.getmProductPrice());
		holder.ed_num.setText(item.getNum() + "");
		holder.tv_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentCart.list_cart.remove(position);
				FragmentCart.adapter.notifyDataSetChanged();
				FragmentCart.lv.setAdapter(FragmentCart.adapter);
				Intent intent = new Intent(MainActivity.ACTION);
				context.sendBroadcast(intent);
				FragmentCart.total_money();
			}
		});
		holder.ed_num.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (holder.ed_num.getText().toString().equals("") == true)
					holder.ed_num.setText("0");
				else
					item.setNum(Integer.parseInt(holder.ed_num.getText()
							.toString()));
			}
		});
		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView tv_name;
		TextView tv_money;
		TextView tv_delete;
		EditText ed_num;
	}

	public Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

}
