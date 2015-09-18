package com.truepus.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.trueplus.project.R;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Thread thread = new Thread() {
			public void run() {
				try {
					int waited = 0;
					while (waited < 3000) {
						sleep(100);
						waited += 100;
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				} finally {
					finish();
					Intent intent = new Intent(SplashActivity.this,
							MainActivity.class);
					SplashActivity.this.startActivity(intent);
					SplashActivity.this.finish();

				}
			}
		};
		thread.start();

	}
}
