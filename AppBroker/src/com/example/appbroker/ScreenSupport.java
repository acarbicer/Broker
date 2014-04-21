package com.example.appbroker;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 
 *
 * 
 */

public class ScreenSupport {

	public static void getScreenSizeToSharedPref(WindowManager windowManager,
			Context ctx) {

		Display display = windowManager.getDefaultDisplay();
		double DeviceHeight = display.getHeight();
		double DeviceWidth = display.getWidth();
		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);
		SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();

		mPrefsEditor.putInt("width", (int) DeviceWidth);
		mPrefsEditor.putInt("height", (int) DeviceHeight);
		mPrefsEditor.commit();
	}

	public static void checkSharedPrefs(Context ctx) {
		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);
		System.out.println("------SHARED PREF CHECK-----");
		System.out.println(mSharedPrefs.getInt("width", 0));
		System.out.println(mSharedPrefs.getInt("height", 0));
		System.out.println("----------------------------");
	}

	public static void getLayoutParamsWrapContent(View v, Context ctx,
			int soldanMargin, int usttenMargin, int tasarimWidth,
			int tasarimHeight) {

		// checkSharedPrefs(ctx);
		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);

		int ScreenHeight = (int) (mSharedPrefs.getInt("height", 0));
		int ScreenWidth = (int) (mSharedPrefs.getInt("width", 0));

		RelativeLayout.LayoutParams vLayoutParams = (LayoutParams) v
				.getLayoutParams();

		vLayoutParams.width = LayoutParams.WRAP_CONTENT;
		vLayoutParams.height = LayoutParams.WRAP_CONTENT;

		vLayoutParams.setMargins(
				(int) ((ScreenWidth * soldanMargin) / tasarimWidth),
				(int) ((ScreenHeight * usttenMargin) / tasarimHeight), 0, 0);
		v.setLayoutParams(vLayoutParams);

	}

	public static void getLayoutParamsCustomWidthWrapContent(View v,
			Context ctx, int soldanMargin, int usttenMargin, int tasarimWidth,
			int tasarimHeight) {

		// checkSharedPrefs(ctx);
		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);

		int ScreenHeight = (int) (mSharedPrefs.getInt("height", 0));
		int ScreenWidth = (int) (mSharedPrefs.getInt("width", 0));

		RelativeLayout.LayoutParams vLayoutParams = (LayoutParams) v
				.getLayoutParams();

		vLayoutParams.width = LayoutParams.WRAP_CONTENT;
		vLayoutParams.height = LayoutParams.WRAP_CONTENT;

		vLayoutParams.setMargins(
				(int) ((ScreenWidth * soldanMargin) / tasarimWidth),
				(int) ((ScreenHeight * usttenMargin) / tasarimHeight), 0, 0);
		v.setLayoutParams(vLayoutParams);

	}

	public static void getLayoutParamsHeightValueWidthWrap(View v, Context ctx,
			int resimHeight, int soldanMargin, int usttenMargin,
			int tasarimWidth, int tasarimHeight) {

		// checkSharedPrefs(ctx);
		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);

		int ScreenHeight = (int) (mSharedPrefs.getInt("height", 0));
		int ScreenWidth = (int) (mSharedPrefs.getInt("width", 0));

		RelativeLayout.LayoutParams vLayoutParams = (LayoutParams) v
				.getLayoutParams();

		vLayoutParams.width = LayoutParams.WRAP_CONTENT;
		vLayoutParams.height = (int) ((ScreenHeight * resimHeight) / tasarimHeight);

		vLayoutParams.setMargins(
				(int) ((ScreenWidth * soldanMargin) / tasarimWidth),
				(int) ((ScreenHeight * usttenMargin) / tasarimHeight), 0, 0);
		v.setLayoutParams(vLayoutParams);

	}

	public static void setSizes(View v, Context ctx, int resimHeight,
			int resimWidth, int tasarimWidth, int tasarimHeight) {

		// checkSharedPrefs(ctx);
		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);

		int ScreenHeight = (int) (mSharedPrefs.getInt("height", 0));
		int ScreenWidth = (int) (mSharedPrefs.getInt("width", 0));

		RelativeLayout.LayoutParams vLayoutParams = (LayoutParams) v
				.getLayoutParams();

		vLayoutParams.width = (int) ((ScreenWidth * resimWidth) / tasarimWidth);
		vLayoutParams.height = (int) ((ScreenHeight * resimHeight) / tasarimHeight);

		v.setLayoutParams(vLayoutParams);

	}

	public static void getLayoutParamsListViewFullWidth(View v, Context ctx,
			int soldanMargin, int usttenMargin, int tasarimWidth,
			int tasarimHeight) {

		// checkSharedPrefs(ctx);
		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);

		int ScreenHeight = (int) (mSharedPrefs.getInt("height", 0));
		int ScreenWidth = (int) (mSharedPrefs.getInt("width", 0));

		RelativeLayout.LayoutParams vLayoutParams = (LayoutParams) v
				.getLayoutParams();

		vLayoutParams.width = LayoutParams.MATCH_PARENT;
		vLayoutParams.height = LayoutParams.WRAP_CONTENT;

		vLayoutParams.setMargins(
				(int) ((ScreenWidth * soldanMargin) / tasarimWidth),
				(int) ((ScreenHeight * usttenMargin) / tasarimHeight), 0, 0);
		v.setLayoutParams(vLayoutParams);

	}

	public static void getLayoutParams(View v, Context ctx, int resimWidth,
			int resimHeight, int soldanMargin, int usttenMargin,
			int tasarimWidth, int tasarimHeight) {

		// checkSharedPrefs(ctx);
		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);

		int ScreenHeight = (int) (mSharedPrefs.getInt("height", 0));
		int ScreenWidth = (int) (mSharedPrefs.getInt("width", 0));

		RelativeLayout.LayoutParams vLayoutParams = (LayoutParams) v
				.getLayoutParams();

		vLayoutParams.width = (int) ((ScreenWidth * resimWidth) / tasarimWidth);
		vLayoutParams.height = (int) ((ScreenHeight * resimHeight) / tasarimHeight);

		vLayoutParams.setMargins(
				(int) ((ScreenWidth * soldanMargin) / tasarimWidth),
				(int) ((ScreenHeight * usttenMargin) / tasarimHeight), 0, 0);
		v.setLayoutParams(vLayoutParams);

	}

	public static void getLayoutParamsCenterHorizontalCustomWidthHeight(View v,
			Context ctx, int resimWidth, int resimHeight, int usttenMargin,
			int tasarimWidth, int tasarimHeight) {

		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);

		int ScreenHeight = (int) (mSharedPrefs.getInt("height", 0));
		int ScreenWidth = (int) (mSharedPrefs.getInt("width", 0));

		RelativeLayout.LayoutParams vLayoutParams = (LayoutParams) v
				.getLayoutParams();

		vLayoutParams.width = (int) ((ScreenWidth * resimWidth) / tasarimWidth);
		vLayoutParams.height = (int) ((ScreenHeight * resimHeight) / tasarimHeight);
		vLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		vLayoutParams.setMargins(0,
				(int) ((ScreenHeight * usttenMargin) / tasarimHeight), 0, 0);
		v.setLayoutParams(vLayoutParams);
	}

	public static void getLayoutParamsCenterHorizontalWrapContent(View v,
			Context ctx, int usttenMargin, int tasarimWidth, int tasarimHeight) {

		SharedPreferences mSharedPrefs = ctx.getSharedPreferences(
				"uygulamaVerileri", Context.MODE_PRIVATE);

		int ScreenHeight = (int) (mSharedPrefs.getInt("height", 0));

		RelativeLayout.LayoutParams vLayoutParams = (LayoutParams) v
				.getLayoutParams();

		vLayoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
		vLayoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
		vLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		vLayoutParams.setMargins(0,
				(int) ((ScreenHeight * usttenMargin) / tasarimHeight), 0, 0);
		v.setLayoutParams(vLayoutParams);
	}

}