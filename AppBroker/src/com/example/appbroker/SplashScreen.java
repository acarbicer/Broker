package com.example.appbroker;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class SplashScreen extends Activity {
	private ImageView backgr;
	private Intent intent;

	// Splash screen timer (3 seconds)
	private static int SPLASH_TIME_OUT = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash_screen);
		
		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show your application logo or company logo
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your application's main activity which is a ListView
				intent = new Intent(SplashScreen.this, MainScreen.class);
				
				// Close this activity
				finish();
				
				startActivity(intent);				
			}
		}, SPLASH_TIME_OUT);
	}

	

}
