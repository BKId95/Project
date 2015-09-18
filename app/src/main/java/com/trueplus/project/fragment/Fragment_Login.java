package com.trueplus.project.fragment;

import java.util.ArrayList;

import javax.crypto.Mac;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.trueplus.project.R;
import com.trueplus.project.entity.CategoryEntity;
import com.trueplus.project.entity.HomeEntity;
import com.trueplus.project.entity.ProductImage;

public class Fragment_Login extends Fragment {
	private Button bt_signIn;
	private Button bt_fogotPass;
	private Button bt_create;
	private EditText ed_mail;
	private EditText ed_pass;
	private String mail;
	private String pass;
	private CheckBox cb;
	private HomeEntity mHomeEntity;

	public HomeEntity getmHomeEntity() {
		return mHomeEntity;
	}

	public void setmHomeEntity(HomeEntity mHomeEntity) {
		this.mHomeEntity = mHomeEntity;
	}

	static public Fragment Instance() {
		// TODO Auto-generated constructor stub
		Fragment_Home myFragment = new Fragment_Home();
		return myFragment;
	}

	@Override
	public void onPause() {
		if (cb.isChecked()) {
			SharedPreferences pre = getActivity().getSharedPreferences("login",
					Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = pre.edit();
			editor.putString("mail", ed_mail.getText().toString());
			editor.putString("pass", ed_pass.getText().toString());
			editor.putBoolean("check", true);
			editor.commit();
		}
		super.onPause();
	}

	@Override
	public void onResume() {
		SharedPreferences pre = getActivity().getSharedPreferences("login",
				Context.MODE_PRIVATE);
		ed_mail.setText(pre.getString("mail", ""));
		ed_pass.setText(pre.getString("pass", ""));
		cb.setChecked(pre.getBoolean("check", false));

		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_login, null);
		bt_signIn = (Button) view.findViewById(R.id.but_signIn);
		bt_fogotPass = (Button) view.findViewById(R.id.but_forgotPass);
		bt_create = (Button) view.findViewById(R.id.but_createAccount);
		ed_mail = (EditText) view.findViewById(R.id.editText_mail);
		ed_pass = (EditText) view.findViewById(R.id.editText_pass);
		cb = (CheckBox) view.findViewById(R.id.checkBox);
		bt_signIn.setEnabled(false);
		ed_mail.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (ed_mail.getText().toString().equals("") == false
						&& ed_pass.getText().toString().equals("") == false) {
					bt_signIn.setEnabled(true);
					bt_signIn.setBackgroundResource(R.drawable.demo2);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		ed_pass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if (ed_mail.getText().toString().equals("") == false
						&& ed_pass.getText().toString().equals("") == false) {
					bt_signIn.setEnabled(true);
					bt_signIn.setBackgroundResource(R.drawable.demo2);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		ed_pass.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				ed_pass.setText("");
				return false;
			}
		});
		bt_signIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mail = ed_mail.getText().toString();
				pass = ed_pass.getText().toString();
				if (!isValidEmail(mail)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Fail");
					builder.setMessage("Mail incorrect @@");
					builder.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
					AlertDialog dialog = builder.create();
					dialog.show();
					dialog.setCancelable(false);
				} else if (mail.equals("android@trueplus.vn") == true
						&& pass.equals("123456") == true) {
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
							0);
					FragmentManager fm = getActivity()
							.getSupportFragmentManager();
					FragmentTransaction ft = fm.beginTransaction();
					Fragment_Home fragment = new Fragment_Home();
					((Fragment_Home) fragment).setmHomeEntity(mHomeEntity);
					ft.replace(R.id.container, fragment);
					ft.addToBackStack(null);
					ft.commit();

				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Fail");
					builder.setMessage("Invalid login or password @@");
					builder.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
					AlertDialog dialog = builder.create();
					dialog.setCancelable(false);
					dialog.show();
				}
			}
		});
		return view;
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (TextUtils.isEmpty(target)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}

	}

}
