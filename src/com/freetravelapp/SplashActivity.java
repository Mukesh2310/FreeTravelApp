package com.freetravelapp;

import java.util.Timer;
import java.util.TimerTask;

import com.freetravelapp.findyourride.FindYourRideActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * 
 * @author Mukesh Thakur
 *
 */
public class SplashActivity extends Activity {

	private boolean mIsBackButtonPressed;
	private static final int SPLASH_DURATION = 5000; //6 seconds
	private Handler myhandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		myhandler = new Handler();
		// run a thread to start the home screen
		myhandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
				if (!mIsBackButtonPressed) {
					// start the home activity 
					Intent i = new Intent(SplashActivity.this, FindYourRideActivity.class);
					startActivity(i);
					finish();
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				}
			}
		}, SPLASH_DURATION); 
	}//End of OnCreate

	//handle back button press
	@Override
	public void onBackPressed() {
		mIsBackButtonPressed = true;
		super.onBackPressed();
	}

}//End of Class
