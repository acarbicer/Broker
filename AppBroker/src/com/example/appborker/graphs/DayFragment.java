package com.example.appborker.graphs;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.appbroker.DisplayMyInvestmentsGraphic;
import com.example.appbroker.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class DayFragment extends Fragment {

	public static String imgUrlStart = "http://app2.denizyatirim.com/gifs/deniznew/hissetic/";
	public static String imgUrlEnd = "E.gif";
	public static ImageView imageView;
	public static ImageLoader imageLoader =  ImageLoader.getInstance();
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {

			return null;
		}

		View view = inflater.inflate(R.layout.graph_day, container, false);
		imageView = (ImageView) view.findViewById(R.id.imageday);
		// imageView.setImageResource(imageResourceId);
		createImageLink(DisplayMyInvestmentsGraphic.selectedStockCode);

		return view;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}
	public static String createImageLink(String str){
		
		imageLoader.displayImage(imgUrlStart + str + imgUrlEnd, imageView);
		return "";
	}
}