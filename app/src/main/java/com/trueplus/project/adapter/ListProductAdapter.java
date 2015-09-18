package com.trueplus.project.adapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.trueplus.project.R;
import com.trueplus.project.entity.ProductEntity;

public class ListProductAdapter extends BaseAdapter {

	Context context;
	ArrayList<ProductEntity> list_product;
	LayoutInflater inflater;

	public ListProductAdapter(Context context,
			ArrayList<ProductEntity> list_product) {
		super();
		this.context = context;
		this.list_product = list_product;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_product.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list_product.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_product_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.imageView_product_item);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.textView_Productname);
			holder.tv_money = (TextView) convertView
					.findViewById(R.id.textView_product_money);
			holder.tv_ofs = (TextView) convertView
					.findViewById(R.id.textView_outofstock_item);
			holder.rb = (RatingBar) convertView.findViewById(R.id.myRatingBar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ProductEntity item = list_product.get(position);
		new DownloadImageTask(holder.image).execute(item.getImage());
		if (item.getmProductName().length() > 28) {
			holder.tv_name.setText(item.getmProductName().substring(0, 28)
					+ "...");
		} else {
			holder.tv_name.setText(item.getmProductName());
		}
		holder.tv_money.setText("Price: $" + item.getmProductPrice());
		if (item.getmProductStock().equals(true)) {
			holder.tv_ofs.setText("Out of stock");
		} else {
			holder.tv_ofs.setText("In Stock");
		}
		holder.rb.setRating(Float.parseFloat(item.getmProductRate()));
		return convertView;
	}

	static class ViewHolder {
		ImageView image;
		TextView tv_name;
		TextView tv_money;
		TextView tv_ofs;
		RatingBar rb;

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
