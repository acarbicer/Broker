package com.example.appbroker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class MainScreen extends Activity {
	Context ctx = this;
	
	Intent intent;
	ImageView ustbar;
	Button dispAllInv,dispMyInv,dispMyInt,dispGraph;
	private long timer = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_screen);
		ScreenSupport.getScreenSizeToSharedPref(getWindowManager(), ctx);
		
		if(!DisplayAllInvestments.stocks_header_list.isEmpty()){
			DisplayAllInvestments.stocks_header_list.clear();
		}
		
		ustbar = (ImageView)findViewById(R.id.main_ustbar);
		dispAllInv = (Button)findViewById(R.id.dispAllInv);
		dispMyInv = (Button)findViewById(R.id.dispMyInv);
		dispMyInt = (Button)findViewById(R.id.dispMyInt);
		dispGraph = (Button)findViewById(R.id.dispGraph);
		
		dispAllInv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if (SystemClock.elapsedRealtime() - timer < 1000){
			            return;
			        }
				 timer = SystemClock.elapsedRealtime();
				intent = new Intent(getApplicationContext(),DisplayAllInvestments.class);
				startActivity(intent);
				finish();
				
			}
		});
		
		dispMyInv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if (SystemClock.elapsedRealtime() - timer < 1000){
			            return;
			        }
				 timer = SystemClock.elapsedRealtime();
				intent = new Intent(getApplicationContext(),DisplayMyInvestments.class);
				startActivity(intent);
				finish();
				
			}
		});
		
		dispMyInt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if (SystemClock.elapsedRealtime() - timer < 1000){
			            return;
			        }
				 timer = SystemClock.elapsedRealtime();
				intent = new Intent(getApplicationContext(),DisplayMyInteresteds.class);
				startActivity(intent);
				finish();
				
			}
		});
		
		dispGraph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 if (SystemClock.elapsedRealtime() - timer < 1000){
			            return;
			        }
				 timer = SystemClock.elapsedRealtime();
				intent = new Intent(getApplicationContext(),DisplayMyInvestmentsGraphic.class);
				startActivity(intent);
				finish();
				
			}
		});
		
		
		ScreenSupport.getLayoutParams(ustbar, ctx, 720, 100, 0, 0, 720, 1280);
		ScreenSupport.getLayoutParams(dispAllInv, ctx, 315, 440, 30, 200, 720, 1280);
		ScreenSupport.getLayoutParams(dispMyInv, ctx, 315, 440, 375, 200, 720, 1280);
		ScreenSupport.getLayoutParams(dispMyInt, ctx, 315, 440, 30, 720, 720, 1280);
		ScreenSupport.getLayoutParams(dispGraph, ctx, 315, 440, 375, 720, 720, 1280);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			android.os.Process.killProcess(android.os.Process.myPid());

		}
		return super.onKeyDown(keyCode, event);
	}

}
