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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class StockDetailActivity extends Activity {
	TextView ad, kod, deger, degisim, alis, satis, taban, tavan, dusuk, yuksek,
			alisNum, satisNum, tabanNum, tavanNum, dusukNum, yuksekNum, tarih,
			tarihText, degisimText;
	ImageView ustbar, followButton,investButton;
	boolean followCheck,investCheck;
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

		followButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Checks if the stock is following or not, and sets the following button image
				if (followCheck) {
					followButton.setBackgroundResource(R.drawable.follow);
					dbStock.open();
					dbStock.updateStocksFollow(stockKod,"no" );
					dbStock.close();
					System.out.println("no");
					followCheck=false;
				} else {
					followButton.setBackgroundResource(R.drawable.following);
					dbStock.open();
					dbStock.updateStocksFollow(stockKod,"yes" );
					dbStock.close();
					followCheck=true;
					System.out.println("yes");
				}
			}
		});
		investButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Checks if the stock is following or not, and sets the following button image
				if (investCheck) {
					investButton.setBackgroundResource(R.drawable.invest);
					dbStock.open();
					dbStock.updateStocksInvest(stockKod,"no" );
					dbStock.close();
					System.out.println("no");
					investCheck=false;
				} else {
					investButton.setBackgroundResource(R.drawable.investing);
					dbStock.open();
					dbStock.updateStocksInvest(stockKod,"yes" );
					dbStock.close();
					investCheck=true;
					System.out.println("yes");
				}
			}
		});
	}

	public void createViews() {
		ustbar = (ImageView) findViewById(R.id.ustbar_stock_detail);
		followButton = (ImageView) findViewById(R.id.followbutton);
		investButton = (ImageView) findViewById(R.id.investbutton);
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
		ScreenSupport.getLayoutParams(kod, ctx, 240, 60, 240, 200, 720, 1280);
		ScreenSupport
				.getLayoutParams(deger, ctx, 240, 120, 240, 280, 720, 1280);

		ScreenSupport.getLayoutParams(degisimText, ctx, 320, 80, 0, 370, 720,
				1280);
		ScreenSupport.getLayoutParams(tarihText, ctx, 320, 80, 360, 370, 720,
				1280);

		ScreenSupport.getLayoutParams(degisim, ctx, 320, 80, 0, 440, 720, 1280);
		ScreenSupport.getLayoutParams(tarih, ctx, 320, 80, 360, 440, 720, 1280);

		ScreenSupport.getLayoutParams(alis, ctx, 250, 80, 370, 630, 720, 1280);
		ScreenSupport.getLayoutParams(satis, ctx, 250, 80, 370, 690, 720, 1280);
		ScreenSupport.getLayoutParams(tavan, ctx, 250, 80, 370, 750, 720, 1280);

		ScreenSupport.getLayoutParams(alisNum, ctx, 100, 80, 620, 630, 720,
				1280);
		ScreenSupport.getLayoutParams(satisNum, ctx, 100, 80, 620, 690, 720,
				1280);
		ScreenSupport.getLayoutParams(tavanNum, ctx, 100, 80, 620, 750, 720,
				1280);

		ScreenSupport.getLayoutParams(dusuk, ctx, 250, 80, 10, 630, 720, 1280);
		ScreenSupport.getLayoutParams(yuksek, ctx, 250, 80, 10, 690, 720, 1280);
		ScreenSupport.getLayoutParams(taban, ctx, 250, 80, 10, 750, 720, 1280);

		ScreenSupport.getLayoutParams(dusukNum, ctx, 100, 80, 260, 630, 720,
				1280);
		ScreenSupport.getLayoutParams(yuksekNum, ctx, 100, 80, 260, 690, 720,
				1280);
		ScreenSupport.getLayoutParams(tabanNum, ctx, 100, 80, 260, 750, 720,
				1280);
		
		ScreenSupport.getLayoutParams(followButton, ctx, 240, 80, 460, 125, 720,
				1280);
		ScreenSupport.getLayoutParams(investButton, ctx, 240, 80, 20, 125, 720,
				1280);

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

	public void getDataFromDatabase() {
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
				String follow = cr.getString(11);
				String invest = cr.getString(12);

				System.out.println(changePerDay);

				ad.setText(name);
				kod.setText(code);
				deger.setText(new Double(last).toString());
				degisim.setText(new Double(changePerDay).toString() + "\u0025");
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
				
				if(follow.equals("yes")){
					followCheck=true;
					followButton.setBackgroundResource(R.drawable.following);
				}else{
					followCheck=false;
					followButton.setBackgroundResource(R.drawable.follow);
				}
				
				if(invest.equals("yes")){
					investCheck=true;
					investButton.setBackgroundResource(R.drawable.investing);
				}else{
					investCheck=false;
					investButton.setBackgroundResource(R.drawable.invest);
				}

			} while (cr.moveToNext());
			System.out.println(sObject.getStrAd() + "  " + sObject.getStrKod()
					+ "  " + sObject.getDblSon() + "  "
					+ sObject.getDblYuzdeDegisimGunluk());

		}
		dbStock.close();

	}
	
}
