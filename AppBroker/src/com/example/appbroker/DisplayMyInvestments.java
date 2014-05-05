
package com.example.appbroker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import android.widget.ViewFlipper;

public class DisplayMyInvestments extends Activity {

    public static ArrayList<Currency> currency_list = new ArrayList<Currency>();
    public static ArrayList<StocksHeader> stock_list = new ArrayList<StocksHeader>();
    public static ArrayList<Stocks> stock_list_object = new ArrayList<Stocks>();
    public static ArrayList<String> spinner_list = new ArrayList<String>();
    public static Spinner spinner;
    public static ListView list_curr;
    public static ListView list_stock;
    ViewFlipper viewFlipper;
    Stocks sObject;
    // public static TextView currency;
    EditText value;
    Button invest;
    double curr;
    String cur_string;
    ImageButton remove;
    public Context ctx = this;
    ImageView ustbar, listbar, doviz_menu, piyasa_menu, buttonback;
    TextView tv;
    private static DBAdapter db;
    private static DBAdapterStock dbStock;
    public static ArrayAdapter<String> spinnerAdapter;
    private boolean list_check = false;

    ArrayList<Currency> investCurrencyList = new ArrayList<Currency>();
    MyInvestListAdapter myInvestListAdapter;
    MyInvestStockListAdapter myInvestStockListAdapter;

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
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        db = new DBAdapter(this);
        dbStock = new DBAdapterStock(this);
        ustbar = (ImageView) findViewById(R.id.myinvest_ustbar);
        listbar = (ImageView) findViewById(R.id.myinvest_listbar);
        // currency = (TextView) findViewById(R.id.textView_MyInvest);
        value = (EditText) findViewById(R.id.editText_MyInvest);
        spinner = (Spinner) findViewById(R.id.spinner_MyInvest);
        list_curr = (ListView) findViewById(R.id.listView_MyInvest);
        list_stock = (ListView) findViewById(R.id.listView_MyInvest_Stock);
        remove = (ImageButton) findViewById(R.id.remove_my_invest);
        doviz_menu = (ImageView) findViewById(R.id.doviz_menu);
        piyasa_menu = (ImageView) findViewById(R.id.piyasa_menu);
        buttonback = (ImageView) findViewById(R.id.button_back);
        tv = (TextView) findViewById(R.id.my_invest_textView1);
        // Fill stock list
        getDataFromDatabaseStock();
        myInvestStockListAdapter = new MyInvestStockListAdapter(
                getApplicationContext(), stock_list, DisplayMyInvestments.this);
        list_stock.setAdapter(myInvestStockListAdapter);

        // Fill currency list
        createObj();
        myInvestListAdapter = new MyInvestListAdapter(getApplicationContext(),
                currency_list, DisplayMyInvestments.this);
        list_curr.setAdapter(myInvestListAdapter);

        list_curr.setItemsCanFocus(true);
        if (!spinner_list.isEmpty()) {
            spinnerAdapter = new ArrayAdapter<String>(this,
                    R.layout.spinner_item_text, spinner_list);
            spinner.setAdapter(spinnerAdapter);
            cur_string = spinner_list.get(0).toString();
        } else {

            // currency.setText("");
        }
        list_curr.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                Currency tempCurrency = currency_list.get(position);
                int spinnerPosition = myInvestListAdapter
                        .getPosition(tempCurrency);
                spinner.setSelection(spinnerPosition + stock_list.size());

            }
        });
        list_stock.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                StocksHeader tempStock = stock_list.get(position);
                int spinnerPosition = myInvestStockListAdapter
                        .getPosition(tempStock);
                spinner.setSelection(spinnerPosition);

            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
                    long arg3) {
                // TODO Auto-generated method stub
                try {

                    cur_string = spinner.getSelectedItem().toString();

                    // currency.setText(cur_string);
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
                if (spinner_list.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            "You should select an investment item first", 0)
                            .show();
                } else if (value.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "You should enter an amount of currency", 0).show();
                } else {
                    curr = Double.parseDouble(value.getText().toString());
                    String lastValueCurrency = "" + curr;
                    String selectedItem = spinner.getSelectedItem().toString();
                    int length = selectedItem.length();
                    if (length == 3) {
                        System.out.println("currency");
                        for (int i = 0; i < currency_list.size(); i++) {
                            if (currency_list.get(i).getCode()
                                    .equals(selectedItem)) {
                                //
                                curr = curr * currency_list.get(i).getSell();
                                lastValueCurrency += "-" + currency_list.get(i).getSell() + "-"
                                        + curr;
                                if (currency_list.get(i).getFirstInvest()
                                        .equals("0")) {
                                    updateCurrencyFirstInvestDateDB(selectedItem);
                                } else {
                                    updateCurrencyLastInvestDateDB(selectedItem);
                                }
                                System.out.println(lastValueCurrency);
                                updateCurrencyLastValueDB(selectedItem,lastValueCurrency);
                            }

                        }
                    } else {

                        System.out.println("stock");
                        for (int i = 0; i < stock_list_object.size(); i++) {
                            System.out.println("Selected =" + i + selectedItem);
                            String lastValue = "" + curr;
                            if (stock_list_object.get(i).getStrKod()
                                    .equals(selectedItem)) {

                                //
                                System.out.println("Selected =" + selectedItem + "Current:" + curr);
                                curr = curr * stock_list_object.get(i).dblGunEnYuksek;
                                lastValue += "-" + stock_list_object.get(i).dblGunEnYuksek + "-"
                                        + curr;
                                System.out.println("En yüksek : "
                                        + stock_list_object.get(i).dblGunEnYuksek);
                                if (stock_list_object.get(i).getFirstInvest()
                                        .equals("0")) {
                                    updateStocksFirstInvestDateDB(selectedItem);
                                } else {
                                    updateStocksLastInvestDateDB(selectedItem);
                                }
                                System.out.println(lastValue);
                                updateStocksLastValueDB(selectedItem, lastValue);
                            }

                        }
                    }

                    makeAndShowDialogBox(curr, cur_string);

                }
            }
        });
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
        ScreenSupport.getLayoutParams(listbar, ctx, 720, 30, 0, 600, 720, 1280);
        ScreenSupport.getLayoutParamsWrapContent(tv, ctx, 20, 110, 720, 1280);
        ScreenSupport.getLayoutParams(invest, ctx, 250, 250, 450, 190, 720,
                1280);
        ScreenSupport.getLayoutParams(spinner, ctx, 300, 140, 20, 170, 720,
                1280);
        ScreenSupport.getLayoutParams(value, ctx, 300, 100, 20, 320, 720, 1280);
        ScreenSupport.getLayoutParams(doviz_menu, ctx, 360, 130, 0, 470, 720,
                1280);
        ScreenSupport.getLayoutParams(piyasa_menu, ctx, 360, 130, 360, 470,
                720, 1280);
        ScreenSupport.getLayoutParams(buttonback, ctx, 720, 130, 0, 470, 720,
                1280);
        // ScreenSupport.getLayoutParamsWrapContent(currency, ctx, 275, 340,
        // 720,
        // 1280);

    }

    private void makeAndShowDialogBox(double val, String s) {

        String msg2 = String.format("Your investment is  %.2f TL ", curr);

        AlertDialog.Builder myDialogBox = new AlertDialog.Builder(this);

        // set message, title, and icon
        myDialogBox.setTitle("Investment");
        myDialogBox.setMessage(msg2);
        myDialogBox.setIcon(R.drawable.investmoney);

        // Set three option buttons
        myDialogBox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
//                        Toast.makeText(getApplicationContext(),
//                                "Investment is Approved", 0).show();

                    }
                });
        myDialogBox.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
//                        Toast.makeText(getApplicationContext(),
//                                "Investment is Canceled", 0).show();
                    }
                });

        myDialogBox.create();
        myDialogBox.show();
    }

    public void createObj() {
        Currency c;

        db.open();

        Cursor cr = db.getInvesteds();

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
                String inv = cr.getString(9);
                String flw = cr.getString(10);
                String first = cr.getString(11);
                String last = cr.getString(12);
                String value = cr.getString(13);

                c = new Currency(nm, cd, by, sl, eb, es, "", wy, dt, inv, flw,
                        first, last, value);
                System.out.println(cd + " " + first);
                System.out.println(cd + " " + last);
                // Adds Temp Currency object to arraylist
                currency_list.add(c);
                spinner_list.add(cd);
            } while (cr.moveToNext());
        }

        db.close();

    }

    public void getDataFromDatabaseStock() {
        sObject = new Stocks();
        dbStock.open();
        Cursor cr = dbStock.getStockInvest();
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
                String firstInvest = cr.getString(13);
                String lastInvest = cr.getString(14);
                String value = cr.getString(15);
                System.out.println(code + " " + firstInvest);

                sObject = new Stocks(code, daylow, dayhigh, last,
                        lowest, highest, changePerDay, name, time, invest,
                        follow, firstInvest, lastInvest, value);
                stock_list_object.add(sObject);
                // stock_list_object.add(new Stocks());
                stock_list
                        .add(new StocksHeader(last, changePerDay, name, code));
                spinner_list.add(code);

                System.out.println(code + " First " + firstInvest);
                System.out.println(code + " Last " + lastInvest);

            } while (cr.moveToNext());
            System.out.println(stock_list_object.size());

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

    public static void removeDb(String stockKod) {
        dbStock.open();
        dbStock.updateStocksInvest(stockKod, "no");
        dbStock.close();
    }

    public static void updateDB(String code, String invest) {
        db.open();
        db.updateCurrencyInvest(code, invest);
        db.close();
    }

    public static void updateCurrencyFirstInvestDateDB(String code) {

        db.open();
        db.updateCurrencyFirstInvested(code, calculateDate());
        db.close();
    }

    public static void updateCurrencyLastInvestDateDB(String code) {

        db.open();
        db.updateCurrencyLastInvested(code, calculateDate());
        db.close();
    }

    public static void updateStocksFirstInvestDateDB(String code) {

        dbStock.open();
        dbStock.updateStocksFirstInvested(code, calculateDate());
        dbStock.close();
    }

    public static void updateStocksLastInvestDateDB(String code) {

        dbStock.open();
        dbStock.updateStocksLastInvested(code, calculateDate());
        dbStock.close();
    }

    public static void updateStocksLastValueDB(String code, String lastValue) {

        dbStock.open();
        dbStock.updateStocksLastValue(code, lastValue);
        dbStock.close();
    }

    public static void updateCurrencyLastValueDB(String code, String lastValue) {

        db.open();
        db.updateCurrencyLastValue(code, lastValue);
        db.close();
    }

    public static String calculateDate() {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        String tempDate = formatter.format(calendar.getTime());
        System.out.println(tempDate);
        return tempDate;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            spinner_list.clear();
            currency_list.clear();
            stock_list.clear();
            stock_list_object.clear();
            finish();
            Intent intent = new Intent(getApplicationContext(),
                    MainScreen.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
