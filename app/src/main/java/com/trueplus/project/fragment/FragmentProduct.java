package com.trueplus.project.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sileria.android.view.HorzListView;
import com.trueplus.project.R;
import com.trueplus.project.adapter.ListProductAdapter;
import com.trueplus.project.adapter.RelatedProductAdapter;
import com.trueplus.project.adapter.ViewPagerAdapterProduct;
import com.trueplus.project.entity.ProductEntity;
import com.trueplus.project.entity.ProductImage;
import com.truepus.project.activity.MainActivity;

public class FragmentProduct extends Fragment {
	private ProductEntity item = new ProductEntity();
	ArrayList<ProductEntity> list_productRelated = new ArrayList<ProductEntity>();
	HorzListView lv;

	public ProductEntity getItem() {
		return item;
	}

	public void setItem(ProductEntity item) {
		this.item = item;
	}

	private String product_ID;

	public String getProduct_ID() {
		return product_ID;
	}

	public void setProduct_ID(String product_ID) {
		this.product_ID = product_ID;
	}

	@Override
	public void onResume() {
		Fragment_Home.cart_Click = false;
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_product, null);
		NetWorkAsyncTask nw = (NetWorkAsyncTask) new NetWorkAsyncTask()
				.execute("http://dev-vn.magestore.com/simicart/1800/index.php/"
						+ "connector/catalog/get_product_detail/data/%7B%22product_id%22:%22"
						+ product_ID + "%22%7D");
		nw.setView(view);
		return view;
	}

	protected HttpResponse makeRequest(String url)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);
		return httpClient.execute(httpPost);

	}

	static InputStream is = null;

	protected String processHTTPResponse(HttpResponse response)
			throws ParseException, IOException {
		String content = "";
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode < 400) {
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						// Log.i("Tag", line);
						sb.append(line + "\n");
					}
					is.close();
					content = sb.toString();
				} catch (Exception e) {
					Log.e("Buffer Error",
							"Error converting result " + e.toString());
				}

			} else {

			}
		}
		return content;
	}

	protected void parserJSON(String result) {
		ProductEntity product = new ProductEntity();
		try {
			JSONObject json = new JSONObject(result);

			String status = null;
			if (json.has("status")) {
				status = json.getString("status");

				Log.e("Status", status);

			}

			JSONObject json_product;
			if (json.has("data")) {
				json_product = json.getJSONArray("data").getJSONObject(0);

				if (json_product.has("product_id")) {
					product.setmProductID(json_product.getString("product_id"));
				}
				if (json_product.has("product_name")) {
					product.setmProductName(json_product
							.getString("product_name"));
				}
				if (json_product.has("product_price")) {
					product.setmProductPrice(json_product
							.getString("product_price"));
				}

				JSONArray arr = null;
				if (json_product.has("product_images")) {
					arr = json_product.getJSONArray("product_images");
					ArrayList<ProductImage> ImageArr = new ArrayList<ProductImage>();
					for (int i = 0; i < arr.length(); i++) {
						ProductImage image = new ProductImage(i,
								arr.getString(i));
						ImageArr.add(image);
					}
					product.setList_image(ImageArr);
				}
				if (json_product.has("product_short_description")) {
					product.setmProductDesciption(json_product
							.getString("product_short_description"));
				}

			}
		} catch (JSONException e) {

		}
		this.setItem(product);
	}

	protected class NetWorkAsyncTask extends AsyncTask<String, Void, String> {
		View view;

		public View getView() {
			return view;
		}

		public void setView(View view) {
			this.view = view;
		}

		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(getActivity());
			pd.setMessage("Loading");
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (pd != null) {
				pd.dismiss();
			}
			if (null != result) {
				parserJSON(result);
				// Log.i("name", item.getmProductName());
				TextView tv_name = (TextView) view
						.findViewById(R.id.textView_ProductName);
				ViewPager vp = (ViewPager) view
						.findViewById(R.id.viewPager_Product);
				TextView tv_money = (TextView) view
						.findViewById(R.id.textView_productMoney);
				TextView tv_des = (TextView) view
						.findViewById(R.id.textView_product_description);
				TextView tv_rel = (TextView) view
						.findViewById(R.id.textView_related_product);
				Button bt = (Button) view.findViewById(R.id.button_andtocart);
				lv = (HorzListView) view.findViewById(R.id.horizontal_lv);
				tv_name = (TextView) view
						.findViewById(R.id.textView_ProductName);
				tv_name.setText(item.getmProductName());
				ViewPagerAdapterProduct adapter2 = new ViewPagerAdapterProduct(
						getChildFragmentManager());
				adapter2.setList_image(item.getList_image());
				vp.setAdapter(adapter2);
				tv_money.setText("Price: $" + item.getmProductPrice());
				if (item.getmProductDesciption().length() > 34)
					tv_des.setText("Dessription:\n"
							+ item.getmProductDesciption().substring(0, 34)
							+ "...");
				else
					tv_des.setText("Dessription:\n"
							+ item.getmProductDesciption());
				bt.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int i;
						Toast.makeText(getActivity(), "Added to cart",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MainActivity.ACTION);
						getActivity().sendBroadcast(intent);
						if (FragmentCart.list_cart == null)
							FragmentCart.list_cart = new ArrayList<ProductEntity>();
						for (i = 0; i < FragmentCart.list_cart.size(); i++) {
							if (FragmentCart.list_cart.get(i).getmProductID()
									.equals(item.getmProductID())) {
								FragmentCart.list_cart.get(i)
										.setNum(FragmentCart.list_cart.get(i)
												.getNum() + 1);
								break;
							}
						}
						if (i == FragmentCart.list_cart.size())
							FragmentCart.list_cart.add(item);
					}
				});

				NetWorkAsyncTaskRelated nw = (NetWorkAsyncTaskRelated) new NetWorkAsyncTaskRelated()
						.execute("http://dev-vn.magestore.com/simicart/1800/index.php/connector/catalog/get_related_products/data/%7B%22hieght%22:%22300%22,%22width%22:%22300%22,%22"
								+ "product_id%22:%22"
								+ product_ID
								+ "%22,%22limit%22:%2215%22%7D");
				nw.setView(view);
			} else
				Log.e("fail", "@@");
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			HttpResponse response = null;
			try {
				response = makeRequest(url);

			} catch (IOException e) {
				return null;
			}
			if (null != response) {
				String content = null;
				try {
					content = processHTTPResponse(response);
					return content;
				} catch (IOException e) {
					return null;
				}
			}
			return null;
		}

	}

	protected ArrayList<ProductEntity> parserJSONRelated(String result) {
		ArrayList<ProductEntity> list = new ArrayList<ProductEntity>();
		try {

			JSONObject json = new JSONObject(result);
			String status = null;
			if (json.has("status")) {
				status = json.getString("status");
				Log.e("Status", status);

			}

			JSONArray arr = null;
			if (json.has("data")) {
				arr = json.getJSONArray("data");
				if (null != arr && arr.length() > 0) {
					for (int i = 0; i < arr.length(); i++) {
						JSONObject json_i = arr.getJSONObject(i);
						ProductEntity product = parseJSONI(json_i);
						list.add(product);

					}
				}
			}
		} catch (JSONException e) {
		}
		for (int i = 0; i < list.size(); i++) {
			list_productRelated.add(list.get(i));
		}
		return list;
	}

	protected ProductEntity parseJSONI(JSONObject json) throws JSONException {
		ProductEntity product = new ProductEntity();
		if (json.has("product_id")) {
			product.setmProductID(json.getString("product_id"));
			// Log.e("ID", product.getmProductID());
		}
		if (json.has("product_name")) {
			product.setmProductName(json.getString("product_name"));
		}
		if (json.has("product_price")) {
			product.setmProductPrice((json.getString("product_price")));
		}
		if (json.has("product_image")) {
			product.setImage((json.getString("product_image")));
		}
		if (json.has("stock_status")) {
			product.setmProductStock(json.getString("stock_status"));
		}

		return product;
	}

	protected class NetWorkAsyncTaskRelated extends
			AsyncTask<String, Void, String> {

		View view;

		public View getView() {
			return view;
		}

		public void setView(View view) {
			this.view = view;
		}

		@Override
		protected void onPostExecute(String result) {
			if (null != result) {
				parserJSONRelated(result);
				RelatedProductAdapter adapter = new RelatedProductAdapter(
						getActivity(), list_productRelated);

				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						FragmentManager fm = getActivity()
								.getSupportFragmentManager();
						fm.popBackStack();
						FragmentTransaction ft = fm.beginTransaction();
						FragmentProduct fragment = new FragmentProduct();
						((FragmentProduct) fragment)
								.setProduct_ID(list_productRelated
										.get(position).getmProductID());
						ft.replace(R.id.container, fragment);
						ft.addToBackStack(null);
						ft.commit();
					}
				});
			}
			super.onPostExecute(result);
		}

		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			HttpResponse response = null;
			try {
				response = makeRequest(url);

			} catch (IOException e) {
				return null;
			}
			if (null != response) {
				String content = null;
				try {
					content = processHTTPResponse(response);
					return content;
				} catch (IOException e) {
					return null;
				}
			}
			return null;
		}

	}

}
