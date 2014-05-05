package com.example.appbroker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class DisplayMyInteresteds extends Activity {

	public static ArrayList<Currency> currency_list = new ArrayList<Currency>();
	public static ArrayList<StocksHeader> stock_list = new ArrayList<StocksHeader>();
	public static ListView list_curr;
	public static ListView list_stock;
	ViewFlipper viewFlipper;
	Stocks sObject;
	double curr;
	String cur_string;
	public Context ctx = this;
	public ImageView ustbar, listbar, doviz_menu, piyasa_menu, buttonback;
	private static DBAdapter db;
	private static DBAdapterStock dbStock;
	private boolean list_check = false;
	MyInterestedtListAdapter myInterestedtListAdapter;
	MyInterestedStockListAdapter myInterestedStockListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_display_my_interesteds);

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
		viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
		db = new DBAdapter(this);
		dbStock = new DBAdapterStock(this);
		cur_string = "USD";
		// currency = (TextView) findViewById(R.id.textView_MyInvest);
		ustbar = (ImageView) findViewById(R.id.myinterested_ustbar);
		listbar = (ImageView) findViewById(R.id.myinterested_listbar);
		list_curr = (ListView) findViewById(R.id.listView_myinterested_currency);
		list_stock = (ListView) findViewById(R.id.listView_myinterested_stock);
		doviz_menu = (ImageView) findViewById(R.id.doviz_menu);
		piyasa_menu = (ImageView) findViewById(R.id.piyasa_menu);
		buttonback = (ImageView) findViewById(R.id.button_back_interested);

		// Fill currency list
		getDataFromDatabaseStock();
		myInterestedStockListAdapter = new MyInterestedStockListAdapter(
				getApplicationContext(), stock_list, DisplayMyInteresteds.this);
		list_stock.setAdapter(myInterestedStockListAdapter);

		// Fill currency list
		createObj();
		myInterestedtListAdapter = new MyInterestedtListAdapter(
				getApplicationContext(), currency_list,
				DisplayMyInteresteds.this);
		list_curr.setAdapter(myInterestedtListAdapter);

		list_curr.setItemsCanFocus(true);

		//
		doviz_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (list_check == true) {
					viewFlipper.setInAnimation(AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.in_from_left));
					viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.out_to_right));
					viewFlipper.showPrevious();
					list_check = false;
					buttonback
							.setBackgroundResource(R.drawable.currency_pressed);
					
				}
				
			}
		});

		piyasa_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (list_check == false) {
					viewFlipper.setInAnimation(AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.in_from_right));
					viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.out_to_left));
					viewFlipper.showNext();

					list_check = true;
					buttonback.setBackgroundResource(R.drawable.stocks_pressed);
					
				}
				
			}
		});

		ScreenSupport.getLayoutParams(ustbar, ctx, 720, 100, 0, 0, 720, 1280);
		ScreenSupport.getLayoutParams(listbar, ctx, 720, 30, 0, 230, 720, 1280);
		ScreenSupport.getLayoutParams(doviz_menu, ctx, 360, 130, 0, 100,
				720, 1280);
		ScreenSupport.getLayoutParams(piyasa_menu, ctx, 360, 130, 360, 100,
				720, 1280);
		ScreenSupport.getLayoutParams(buttonback, ctx, 720, 130, 0, 100,
				720, 1280);
	}

	public void createObj() {
		Currency c;

		db.open();

		Cursor cr = db.getInteresteds("Y");

		if (cr.moveToFirst()) {
			do {
				c = new Currency();
				String id = cr.getString(0);// not used
				String nm = cr.getString(1);
				String cd = cr.getString(2);
				Double by = Double.parseDouble(cr.getString(3));
				Double sl = Double.parseDouble(cr.getString(4));
				Double eb = Double.parseDouble(cr.getString(5));
				Double es = Double.parseDouble(cr.getString(6));
				String wy = cr.getString(7);
				String dt = cr.getString(8);

				c.setCode(cd);
				c.setName(nm);
				c.setBuy(by);
				c.setSell(sl);
				c.setEbuy(eb);
				c.setEsell(es);
				c.setWay(wy);
				c.setDate(dt);
				c.setInvest(cr.getString(9));
				c.setFollow(cr.getString(10));
				// Adds Temp Currency object to arraylist
				currency_list.add(c);

			} while (cr.moveToNext());
		}

		db.close();

	}
	public void getDataFromDatabaseStock() {
		sObject = new Stocks();
		dbStock.open();
		Cursor cr = dbStock.getStockInterest();
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

				stock_list.add(new StocksHeader(last, changePerDay, name, code));
				System.out.println("Size = " + stock_list.size());

			} while (cr.moveToNext());
			System.out.println(sObject.getStrAd() + "  " + sObject.getStrKod()
					+ "  " + sObject.getDblSon() + "  "
					+ sObject.getDblYuzdeDegisimGunluk());

		}
		dbStock.close();

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

	public static void updateDB(String code, String invest) {
		db.open();
		db.updateCurrencyInterested(code, invest);
		db.close();
	}
	public static void  removeDb(String stockKod){
		dbStock.open();
		dbStock.updateStocksFollow(stockKod,"no" );
		dbStock.close();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		    stock_list.clear();
			currency_list.clear();
			finish();
			Intent intent = new Intent(getApplicationContext(),
					MainScreen.class);
			startActivity(intent);

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
