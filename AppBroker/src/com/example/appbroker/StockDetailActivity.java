package com.example.appbroker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class StockDetailActivity extends Activity {
	TextView ad, kod, deger, degisim, alis, satis, taban, tavan, dusuk, yuksek,alisNum, satisNum, tabanNum, tavanNum, dusukNum, yuksekNum,
			tarih,tarihText,degisimText;
	ImageView ustbar;
	Context ctx = this;
	private static DBAdapterStock dbStock;
	public Stocks sObject;;
	public String stockKod;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_stock_detail);
		try {
			String destPath = "/data/data/" + getPackageName()
					+ "/databases/investmentDB";
			File destPathFile = new File("/data/data/" + getPackageName()
					+ "/databases/");

			if (!destPathFile.exists()) {
				destPathFile.mkdirs();
				CopyDB(getBaseContext().getAssets().open("investmentDB"),
						new FileOutputStream(destPath));
			} else {

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbStock = new DBAdapterStock(this);
		createViews();
		Intent intent = getIntent();
		stockKod = intent.getExtras().getString("kod");
		getDataFromDatabase();
		screenSupportMethod();

	}

	public void createViews() {
		ustbar = (ImageView) findViewById(R.id.ustbar_stock_detail);

		ad = (TextView) findViewById(R.id.ad_text_stock_detail);
		kod = (TextView) findViewById(R.id.kod_text_stock_detail);
		deger = (TextView) findViewById(R.id.deger_text_stock_detail);
		degisim = (TextView) findViewById(R.id.degisim_text_stock_detail);
		alis = (TextView) findViewById(R.id.eniyialis_text_stock_detail);
		satis = (TextView) findViewById(R.id.eniyisatis_text_stock_detail);
		taban = (TextView) findViewById(R.id.taban_text_stock_detail);
		tavan = (TextView) findViewById(R.id.tavan_text_stock_detail);
		dusuk = (TextView) findViewById(R.id.gununendusuk_text_stock_detail);
		yuksek = (TextView) findViewById(R.id.gununenyuksek_text_stock_detail);
		alisNum = (TextView) findViewById(R.id.eniyialis_num_stock_detail);
		satisNum = (TextView) findViewById(R.id.eniyisatis_num_stock_detail);
		tabanNum = (TextView) findViewById(R.id.taban_num_stock_detail);
		tavanNum = (TextView) findViewById(R.id.tavan_num_stock_detail);
		dusukNum = (TextView) findViewById(R.id.gununendusuk_num_stock_detail);
		yuksekNum = (TextView) findViewById(R.id.gununenyuksek_num_stock_detail);
		tarih = (TextView) findViewById(R.id.tarih_text_stock_detail);
		degisimText = (TextView) findViewById(R.id.degisim_stock_detail);
		tarihText = (TextView) findViewById(R.id.saat_stock_detail);

	}

	public void screenSupportMethod() {
		ScreenSupport.getLayoutParams(ustbar, ctx, 720, 100, 0, 0, 720, 1280);

		ScreenSupport.getLayoutParams(ad, ctx, 720, 100, 0, 0, 720, 1280);
		ScreenSupport.getLayoutParams(kod, ctx, 240, 60, 240, 130, 720, 1280);
		ScreenSupport.getLayoutParams(deger, ctx, 240, 120, 240, 210, 720, 1280);
		
		ScreenSupport.getLayoutParams(degisimText, ctx, 240, 40, 0, 300, 720,1280);
		ScreenSupport.getLayoutParams(tarihText, ctx, 240, 40, 480, 300, 720, 1280);
		
		ScreenSupport.getLayoutParams(degisim, ctx, 240, 60, 0, 340, 720,1280);
		ScreenSupport.getLayoutParams(tarih, ctx, 240, 60, 480, 340, 720, 1280);
		
		ScreenSupport.getLayoutParams(alis, ctx, 250, 40, 370, 520, 720, 1280);
		ScreenSupport.getLayoutParams(satis, ctx, 250, 40, 370, 580, 720, 1280);
		ScreenSupport.getLayoutParams(tavan, ctx, 250, 40, 370, 640, 720, 1280);
		
		ScreenSupport.getLayoutParams(alisNum, ctx, 100, 40, 620, 520, 720, 1280);
		ScreenSupport.getLayoutParams(satisNum, ctx, 100, 40, 620, 580, 720, 1280);
		ScreenSupport.getLayoutParams(tavanNum, ctx, 100, 40, 620, 640, 720, 1280);

		ScreenSupport.getLayoutParams(dusuk, ctx, 250, 40, 10, 520, 720, 1280);
		ScreenSupport.getLayoutParams(yuksek, ctx, 250, 40, 10, 580, 720, 1280);
		ScreenSupport.getLayoutParams(taban, ctx, 250, 40, 10, 640, 720, 1280);
		
		ScreenSupport.getLayoutParams(dusukNum, ctx, 100, 40, 260, 520, 720, 1280);
		ScreenSupport.getLayoutParams(yuksekNum, ctx, 100, 40, 260, 580, 720, 1280);
		ScreenSupport.getLayoutParams(tabanNum, ctx, 100, 40, 260, 640, 720, 1280);

		 

	}

	public void CopyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// Copy 1K bytes at a time
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}

	public void getDataFromDatabase(){
		sObject = new Stocks();
		dbStock.open();
		Cursor cr = dbStock.getStockDetail(stockKod);
		if (cr.moveToFirst()) {
			do {
				
				String name = cr.getString(0);
				String code = cr.getString(1);
				Double last = cr.getDouble(2);
				Double changePerDay = cr.getDouble(3);
				Double daylow = cr.getDouble(4);
				Double dayhigh = cr.getDouble(5);
				Double sell = cr.getDouble(6);
				Double buy = cr.getDouble(7);
				Double lowest = cr.getDouble(8);
				Double highest = cr.getDouble(9);
				String time = cr.getString(10);
				
				
				System.out.println(changePerDay);
				
				ad.setText(name);
				kod.setText(code);
				deger.setText(new Double(last).toString());
				degisim.setText(new Double(changePerDay).toString()+"\u0025");
				alisNum.setText(new Double(buy).toString());
				satisNum.setText(new Double(sell).toString());
				tabanNum.setText(new Double(lowest).toString());
				tavanNum.setText(new Double(highest).toString());
				dusukNum.setText(new Double(daylow).toString());
				yuksekNum.setText(new Double(dayhigh).toString());
				tarih.setText(time);
				if (changePerDay > 0) {
					degisim.setTextColor(Color.parseColor("#16CC26"));
				} else if (changePerDay < 0) {
					degisim.setTextColor(Color.parseColor("#E81010"));
				} else {
					degisim.setTextColor(Color.parseColor("#EDA41C"));
				}
				
			} while (cr.moveToNext());
			System.out.println(sObject.getStrAd() + "  "
					+ sObject.getStrKod() + "  " + sObject.getDblSon()
					+ "  " + sObject.getDblYuzdeDegisimGunluk());
			
		}
		dbStock.close();
		
	}
}
