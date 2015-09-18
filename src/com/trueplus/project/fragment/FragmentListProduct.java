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
import android.content.Context;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trueplus.project.R;
import com.trueplus.project.adapter.ListProductAdapter;
import com.trueplus.project.entity.ProductEntity;

public class FragmentListProduct extends Fragment {
	private ArrayList<ProductEntity> list_product = new ArrayList<ProductEntity>();

	static public Fragment Instance() {
		// TODO Auto-generated constructor stub
		FragmentListProduct myFragment = new FragmentListProduct();
		return myFragment;
	}

	private ListView lv;
	static int offset = 0;

	@Override
	public void onResume() {
		Fragment_Home.cart_Click = false;
		list_product.clear();
		load(offset);
		super.onResume();
	}
	
	protected void load(int offset) {

		NetWorkAsyncTask nw = (NetWorkAsyncTask) new NetWorkAsyncTask()
				.execute("http://dev-vn.magestore.com/simicart/1800/index.php/connector/catalog/get_all_products/data"
						+ "/%7B%22limit%22:%228%22,%22category_id%22:%22-1%22,%22height%22:%22300%22,%22offset%22:%22"
						+ offset + "%22,%22width%22:%22300%22%7D");
		nw.setView(getView());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_categoryt, null);
		lv = (ListView) view.findViewById(R.id.listView_listProduct);
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view,
					int scrollState) {

				if (scrollState == SCROLL_STATE_IDLE) {
					if (lv.getLastVisiblePosition() + 1 == list_product
							.size() && offset <= 69) {
						offset += 8;
						load(offset);
					}
					
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
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

	protected boolean checkInternetConnect(Context context) {
		ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null)
			return false;
		if (!i.isConnected())
			return false;
		if (!i.isAvailable())
			return false;
		return true;
	}

	protected ArrayList<ProductEntity> parserJSON(String result) {
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
			list_product.add(list.get(i));
		}
		// Log.e("SizE", "" + list_product.size());
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

		if (json.has("product_rate")) {
			product.setmProductRate(json.getString("product_rate"));
		}
		return product;
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

				ListProductAdapter adapter = new ListProductAdapter(
						getActivity(), list_product);
				lv.setAdapter(adapter);
				if (list_product.size() > 8) {
					lv.setSelection(list_product.size() - 8);
				}
				TextView tv = (TextView) view
						.findViewById(R.id.textView_product);
				tv.setText(list_product.size() + " Prodducts");
				lv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						FragmentManager fm = getActivity()
								.getSupportFragmentManager();
						FragmentTransaction ft = fm.beginTransaction();
						FragmentProduct fragment = new FragmentProduct();
						((FragmentProduct) fragment).setProduct_ID(list_product
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
