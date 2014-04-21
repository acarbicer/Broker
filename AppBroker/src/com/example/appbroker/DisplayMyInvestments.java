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
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMyInvestments extends Activity {

	public static ArrayList<Currency> currency_list = new ArrayList<Currency>();
	public static ArrayList<String> spinner_list = new ArrayList<String>();
	public static Spinner spinner;
	public static ListView list_curr;
	//public static TextView currency;
	EditText value;
	Button invest;
	double curr;
	String cur_string;
	ImageButton remove;
	public Context ctx = this;
	ImageView ustbar, listbar;
	TextView tv;
	private static DBAdapter db;
	public static ArrayAdapter<String> spinnerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_display_my_investments);

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

		ustbar = (ImageView) findViewById(R.id.myinvest_ustbar);
		listbar = (ImageView) findViewById(R.id.myinvest_listbar);
		//currency = (TextView) findViewById(R.id.textView_MyInvest);
		value = (EditText) findViewById(R.id.editText_MyInvest);
		spinner = (Spinner) findViewById(R.id.spinner_MyInvest);
		list_curr = (ListView) findViewById(R.id.listView_MyInvest);
		remove = (ImageButton) findViewById(R.id.remove_my_invest);
		tv = (TextView) findViewById(R.id.my_invest_textView1);
		createObj();
		list_curr.setAdapter(new MyInvestListAdapter(getApplicationContext(),
				currency_list, DisplayMyInvestments.this));

		list_curr.setItemsCanFocus(true);
		if(!spinner_list.isEmpty()){
			spinnerAdapter = new ArrayAdapter<String>(this,
				R.layout.spinner_item, spinner_list);
		spinner.setAdapter(spinnerAdapter);
		cur_string = spinner_list.get(0).toString();
		}else{
			
			//currency.setText("");
		}
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				try {
					
					cur_string = spinner.getSelectedItem().toString();

					//currency.setText(cur_string);
				} catch (NullPointerException e) {
					// TODO: handle exception
				}
				

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		invest = (Button) findViewById(R.id.button_MyInvest);
		invest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (value.getText().toString().equals("")) {
					Toast.makeText(getApplicationContext(),
							"You should enter an amount of currency", 0).show();
				} else {
					curr = Double.parseDouble(value.getText().toString());

					makeAndShowDialogBox(curr, cur_string);
				}
			}
		});

		ScreenSupport.getLayoutParams(ustbar, ctx, 720, 100, 0, 0, 720, 1280);
		ScreenSupport.getLayoutParams(listbar, ctx, 720, 30, 0, 500, 720, 1280);
		ScreenSupport.getLayoutParamsWrapContent(tv, ctx, 20, 110, 720, 1280);
		ScreenSupport.getLayoutParams(invest, ctx, 250, 250, 450, 190, 720,
				1280);
		ScreenSupport.getLayoutParams(spinner, ctx, 250, 120, 20, 170, 720,
				1280);
		ScreenSupport.getLayoutParams(value, ctx, 250, 100, 20, 320, 720, 1280);
//		ScreenSupport.getLayoutParamsWrapContent(currency, ctx, 275, 340, 720,
//				1280);

	}

	private void makeAndShowDialogBox(double val, String s) {

		String msg2 = "You will invest " + curr + " " + s;

		AlertDialog.Builder myDialogBox = new AlertDialog.Builder(this);

		// set message, title, and icon
		myDialogBox.setTitle("Investment");
		myDialogBox.setMessage(msg2);
		myDialogBox.setIcon(R.drawable.invest);

		// Set three option buttons
		myDialogBox.setPositiveButton("APPROVE",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
						Toast.makeText(getApplicationContext(),
								"Investment is Approved", 0).show();
					}
				});
		myDialogBox.setNegativeButton("CANCEL",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
						Toast.makeText(getApplicationContext(),
								"Investment is Canceled", 0).show();
					}
				});

		myDialogBox.create();
		myDialogBox.show();
	}

	public void createObj() {
		Currency c;

		db.open();

		Cursor cr = db.getInvesteds("Y");

		if (cr.moveToFirst()) {
			do {
				c = new Currency();
				String id = cr.getString(0);
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
				spinner_list.add(c.getCode().toString());
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
		db.updateCurrencyInvest(rowId, invest);
		db.close();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			spinner_list.clear();
			currency_list.clear();
			finish();
			Intent intent = new Intent(getApplicationContext(),MainScreen.class);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
