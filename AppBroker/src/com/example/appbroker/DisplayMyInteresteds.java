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

public class DisplayMyInteresteds extends Activity {
	
	public static ArrayList<Currency> currency_list = new ArrayList<Currency>();
	public static ArrayList<Stocks> stocks_list = new ArrayList<Stocks>();
	public static Spinner spinner;
	public static ListView list_curr;
	private static DBAdapter db;
	//TextView currency;
	
	
	double curr;
	String cur_string;
	ImageView ustbar,listbar;
	public Context ctx=this;

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

		db = new DBAdapter(this);
		
		
		
		
		cur_string = "USD";
		//currency = (TextView) findViewById(R.id.textView_MyInvest);
		ustbar = (ImageView)findViewById(R.id.myinterested_ustbar);
		listbar = (ImageView)findViewById(R.id.myinterested_listbar);
		list_curr = (ListView) findViewById(R.id.listView_MyInvest);
	
		

		createObj();
		list_curr.setAdapter(new MyInterestedtListAdapter(getApplicationContext(),
				currency_list, DisplayMyInteresteds.this));

		list_curr.setItemsCanFocus(true);
		
		
		ScreenSupport.getLayoutParams(ustbar, ctx, 720, 100, 0, 0, 720, 1280);
		ScreenSupport.getLayoutParams(listbar, ctx, 720, 30, 0, 200, 720, 1280);
		
	}
	
	
	public void createObj() {
		Currency c;

		db.open();

		Cursor cr = db.getInteresteds("Y");

		if (cr.moveToFirst()) {
			do {
				c = new Currency();
				String id = cr.getString(0);//not used
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

	public static void updateDB(long rowId, String invest) {
		db.open();
		db.updateCurrencyInterested(rowId, invest);
		db.close();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

				currency_list.clear();
				finish();
				Intent intent = new Intent(getApplicationContext(),MainScreen.class);
				startActivity(intent);
			

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	

}
