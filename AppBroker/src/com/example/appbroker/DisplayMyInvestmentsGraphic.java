package com.example.appbroker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;

public class DisplayMyInvestmentsGraphic extends Activity {
	Context ctx=this;
	ImageView ustbar,piechart,bar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_display_my_investments_graphic);
		
		ustbar = (ImageView)findViewById(R.id.disp_graphic_ustbar);
		piechart = (ImageView)findViewById(R.id.imagepie);
		bar = (ImageView)findViewById(R.id.imagebar);
		
		ScreenSupport.getLayoutParams(ustbar, ctx, 720, 100, 0, 0, 720, 1280);
		ScreenSupport.getLayoutParams(piechart, ctx, 320, 320, 20, 200, 720, 1280);
		ScreenSupport.getLayoutParams(bar, ctx, 320, 320, 380, 200, 720, 1280);
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			Intent intent = new Intent(getApplicationContext(),MainScreen.class);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
