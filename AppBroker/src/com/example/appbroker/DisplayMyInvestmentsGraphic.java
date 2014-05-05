
package com.example.appbroker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.w3c.dom.Element;

import com.example.appborker.graphs.DayFragment;
import com.example.appborker.graphs.MonthFragment;
import com.example.appborker.graphs.PagerAdapter;
import com.example.appborker.graphs.WeekFragment;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

public class DisplayMyInvestmentsGraphic extends FragmentActivity implements
        TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    Context ctx = this;
    ImageView ustbar, asd, changeButton, doviz_menu, piyasa_menu,
            currencyGraph;
    public static String graphUrlHeader = "http://www.exchange-rates.org/Chart.aspx?iso_code=TRY&base_iso_code=";
    public static String graphUrlEnd = "&mode=G&filter=30";
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, DisplayMyInvestmentsGraphic.TabInfo>();
    private PagerAdapter mPagerAdapter;
    public static ImageLoader imageLoader, imageLoaderCurrency;
    private static DBAdapterStock dbStock;
    private static DBAdapter db;
    TextView textName, textFirst, textLast, textHigh, textLastValue, textLastAmount,
            textLastInvestment, textEstimation;
    private Spinner investItemSpinner, investCurrencySpinner;
    Stocks sObject;
    Currency cObject;
    public static String selectedStockCode = "";
    public static String selectedCurrencyCode = "";
    ArrayList<String> investItemList = new ArrayList<String>();
    ArrayList<String> investCurrencyItemList = new ArrayList<String>();
    ArrayList<Stocks> investStocksList = new ArrayList<Stocks>();
    ArrayList<Currency> investCurrencyList = new ArrayList<Currency>();
    private boolean list_check = false;

    /**
	 * 
	 */
    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }

    class TabFactory implements TabContentFactory {

        private final Context mContext;

        /**
         * @param context
         */
        public TabFactory(Context context) {
            mContext = context;
        }

        /**
         * (non-Javadoc)
         * 
         * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
         */
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }

    }

    /**
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_display_my_investments_graphic);
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

        // Imageloader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                ctx).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);

        ustbar = (ImageView) findViewById(R.id.disp_graphic_ustbar);
        asd = (ImageView) findViewById(R.id.image);
        changeButton = (ImageView) findViewById(R.id.buttonbackgraph);
        currencyGraph = (ImageView) findViewById(R.id.currencyGraph);
        dbStock = new DBAdapterStock(this);
        db = new DBAdapter(this);
        investItemSpinner = (Spinner) findViewById(R.id.spinnerinvestgraphics);
        investCurrencySpinner = (Spinner) findViewById(R.id.spinnerinvestcurrencygraphics);
        doviz_menu = (ImageView) findViewById(R.id.doviz_menu);
        piyasa_menu = (ImageView) findViewById(R.id.piyasa_menu);
        textName = (TextView) findViewById(R.id.graphicscurrencyname);
        textFirst = (TextView) findViewById(R.id.graphicscurrencyfirst);
        textLast = (TextView) findViewById(R.id.graphicscurrencylast);
        textLastValue = (TextView) findViewById(R.id.graphicscurrencylastvalue);
        textLastAmount = (TextView) findViewById(R.id.graphicscurrencylastamount);
        textLastInvestment = (TextView) findViewById(R.id.graphicscurrencylastinvestment);
        textEstimation = (TextView) findViewById(R.id.graphicscurrencyestimation);
        // Initialise the TabHost
        this.initialiseTabHost(savedInstanceState);
        this.intialiseViewPager();

        getDataFromDatabase();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_text, investItemList);
        investItemSpinner.setAdapter(listAdapter);

        // Sets the grafic for first selected item
        if (!selectedStockCode.equals("")) {
            selectedStockCode = investItemSpinner.getSelectedItem().toString();
        }
        investItemSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int position, long id) {
                        // TODO Auto-generated method stub
                        selectedStockCode = parent.getSelectedItem().toString();

                        DayFragment.createImageLink(selectedStockCode);
                        WeekFragment.createImageLink(selectedStockCode);
                        MonthFragment.createImageLink(selectedStockCode);

                        for (Stocks s : investStocksList) {
                            if (s.getStrKod() != null && list_check == true
                                    && s.getStrKod().contains(selectedStockCode)) {
                                textName.setText("Name: " + s.getStrAd());
                                textFirst.setText("First and Last Invest: " + s.getFirstInvest()
                                        + "-" + s.getLastInvest());
                                if (!s.getFirstInvest().equals("0")) {
                                    textLastValue.setVisibility(View.VISIBLE);
                                    textLastAmount.setVisibility(View.VISIBLE);
                                    textLastInvestment.setVisibility(View.VISIBLE);
                                    textEstimation.setVisibility(View.VISIBLE);
                                    String[] str = s.getLastValue().toString().split("-");
                                    textLastValue.setText("Last Value of Investment: : " + str[0]);
                                    textLastAmount
                                            .setText("Last Amount of Investment: : " + str[1]);
                                    textLastInvestment.setText(String.format(
                                            "Your Last Investment in TL: %.2f",
                                            Double.parseDouble(str[2])));
                                    textEstimation
                                            .setText(String.format(
                                                    "Today's Estimation with same amount: %.2f",
                                                    s.dblGunEnYuksek * Double.parseDouble(str[1])));
                                } else {
                                    textFirst.setText("First and Last Invest: NEVER");
                                    textLastValue.setVisibility(View.INVISIBLE);
                                    textLastAmount.setVisibility(View.INVISIBLE);
                                    textLastInvestment.setVisibility(View.INVISIBLE);
                                    textEstimation.setVisibility(View.INVISIBLE);
                                }
                            }
                            // something here
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });

        ArrayAdapter<String> listCurrincyAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item_text,
                investCurrencyItemList);
        investCurrencySpinner.setAdapter(listCurrincyAdapter);

        // Sets the grafic for first selected item
        if (!selectedCurrencyCode.equals("")) {
            selectedCurrencyCode = investCurrencySpinner.getSelectedItem()
                    .toString();
        }
        investCurrencySpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int position, long id) {
                        // TODO Auto-generated method stub
                        selectedCurrencyCode = parent.getSelectedItem()
                                .toString();
                        String url = graphUrlHeader + "USD" + graphUrlEnd;
                        if (!selectedCurrencyCode.equals("")) {
                            new DownloadImageTask(currencyGraph)
                                    .execute("http://www.exchange-rates.org/Chart.aspx?iso_code=TRY&base_iso_code="
                                            + selectedCurrencyCode
                                            + "&mode=G&filter=30");
                            System.out.println(url);
                            for (Currency c : investCurrencyList) {
                                if (c.getName() != null && list_check == false
                                        && c.getCode().contains(selectedCurrencyCode)) {
                                    textName.setText("Name: " + c.getName());
                                    textFirst.setText("First and Last Invest: "
                                            + c.getFirstInvest() + "-" + c.getLastInvest());
                                    System.out.println("getFirstInvest:" + c.getLastInvest());
                                    if (!c.getFirstInvest().equals("0")) {
                                        textLastValue.setVisibility(View.VISIBLE);
                                        textLastAmount.setVisibility(View.VISIBLE);
                                        textLastInvestment.setVisibility(View.VISIBLE);
                                        textEstimation.setVisibility(View.VISIBLE);
                                        String[] str = c.getLastValue().toString().split("-");
                                        textLastValue.setText("Last Value of Investment: "
                                                + str[0]);
                                        textLastAmount.setText("Last Amount of Investment: "
                                                + str[1]);
                                        textLastInvestment.setText(String.format(
                                                "Your Last Investment in TL: %.2f",
                                                Double.parseDouble(str[2])));
                                        textEstimation
                                                .setText(String
                                                        .format("Today's Estimation with same amount: %.2f",
                                                                c.getSell()
                                                                        * Double.parseDouble(str[1])))
                                        ;
                                    } else {
                                        textFirst.setText("First and Last Invest: NEVER");
                                        textLastValue.setVisibility(View.INVISIBLE);
                                        textLastAmount.setVisibility(View.INVISIBLE);
                                        textLastInvestment.setVisibility(View.INVISIBLE);
                                        textEstimation.setVisibility(View.INVISIBLE);
                                    }
                                }
                                // something here
                            }

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });

        mTabHost.setVisibility(View.INVISIBLE);
        investItemSpinner.setVisibility(View.INVISIBLE);

        doviz_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (list_check == true) {

                    list_check = false;
                    changeButton
                            .setBackgroundResource(R.drawable.currency_pressed);
                    mTabHost.setVisibility(View.INVISIBLE);
                    investItemSpinner.setVisibility(View.INVISIBLE);
                    investCurrencySpinner.setVisibility(View.VISIBLE);
                    currencyGraph.setVisibility(View.VISIBLE);
                }
                for (Currency c : investCurrencyList) {
                    if (c.getName() != null && list_check == false
                            && c.getCode().contains(selectedCurrencyCode)) {
                        textName.setText("Name: " + c.getName());
                        textFirst.setText("First and Last Invest: "
                                + c.getFirstInvest() + "-" + c.getLastInvest());
                        System.out.println("getFirstInvest:" + c.getLastInvest());
                        if (!c.getFirstInvest().equals("0")) {
                            textLastValue.setVisibility(View.VISIBLE);
                            textLastAmount.setVisibility(View.VISIBLE);
                            textLastInvestment.setVisibility(View.VISIBLE);
                            textEstimation.setVisibility(View.VISIBLE);
                            String[] str = c.getLastValue().toString().split("-");
                            textLastValue.setText("Last Value of Investment: "
                                    + str[0]);
                            textLastAmount.setText("Last Amount of Investment: "
                                    + str[1]);
                            textLastInvestment.setText(String.format(
                                    "Your Last Investment in TL: %.2f",
                                    Double.parseDouble(str[2])));
                            textEstimation
                                    .setText(String
                                            .format("Today's Estimation with same amount: %.2f",
                                                    c.getSell()
                                                            * Double.parseDouble(str[1])))
                            ;
                        } else {
                            textFirst.setText("First and Last Invest: NEVER");
                            textLastValue.setVisibility(View.INVISIBLE);
                            textLastAmount.setVisibility(View.INVISIBLE);
                            textLastInvestment.setVisibility(View.INVISIBLE);
                            textEstimation.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });

        piyasa_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (list_check == false) {

                    list_check = true;
                    changeButton
                            .setBackgroundResource(R.drawable.stocks_pressed);
                    mTabHost.setVisibility(View.VISIBLE);
                    investItemSpinner.setVisibility(View.VISIBLE);
                    investCurrencySpinner.setVisibility(View.INVISIBLE);
                    currencyGraph.setVisibility(View.INVISIBLE);
                }
                for (Stocks s : investStocksList) {
                    if (s.getStrKod() != null && list_check == true
                            && s.getStrKod().contains(selectedStockCode)) {
                        textName.setText("Name: " + s.getStrAd());
                        textFirst.setText("First and Last Invest: " + s.getFirstInvest()
                                + "-" + s.getLastInvest());
                        if (!s.getFirstInvest().equals("0")) {
                            textLastValue.setVisibility(View.VISIBLE);
                            textLastAmount.setVisibility(View.VISIBLE);
                            textLastInvestment.setVisibility(View.VISIBLE);
                            textEstimation.setVisibility(View.VISIBLE);
                            String[] str = s.getLastValue().toString().split("-");
                            textLastValue.setText("Last Value of Investment: : " + str[0]);
                            textLastAmount
                                    .setText("Last Amount of Investment: : " + str[1]);
                            textLastInvestment.setText(String.format(
                                    "Your Last Investment in TL: %.2f",
                                    Double.parseDouble(str[2])));
                            textEstimation
                                    .setText(String.format(
                                            "Today's Estimation with same amount: %.2f",
                                            s.dblGunEnYuksek * Double.parseDouble(str[1])));
                        } else {
                            textFirst.setText("First and Last Invest: NEVER");
                            textLastValue.setVisibility(View.INVISIBLE);
                            textLastAmount.setVisibility(View.INVISIBLE);
                            textLastInvestment.setVisibility(View.INVISIBLE);
                            textEstimation.setVisibility(View.INVISIBLE);
                        }
                    }
                    // something here
                }
            }
        });
        ScreenSupport.getLayoutParams(changeButton, ctx, 720, 130, 0, 100, 720,
                1280);
        ScreenSupport.getLayoutParams(doviz_menu, ctx, 360, 130, 0, 100, 720,
                1280);
        ScreenSupport.getLayoutParams(piyasa_menu, ctx, 360, 130, 360, 100,
                720, 1280);
        ScreenSupport.getLayoutParams(ustbar, ctx, 720, 100, 0, 0, 720, 1280);
        ScreenSupport.getLayoutParams(investItemSpinner, ctx, 720, 140, 0, 230,
                720, 1280);
        ScreenSupport.getLayoutParams(investCurrencySpinner, ctx, 720, 140, 0,
                230, 720, 1280);
        ScreenSupport.getLayoutParams(textName, ctx, 720, 60, 0,
                380, 720, 1280);
        ScreenSupport.getLayoutParams(textFirst, ctx, 720, 60, 0,
                440, 720, 1280);

        ScreenSupport.getLayoutParams(textLastValue, ctx, 720, 60, 0,
                500, 720, 1280);
        ScreenSupport.getLayoutParams(textLastAmount, ctx, 720, 60, 0,
                560, 720, 1280);
        ScreenSupport.getLayoutParams(textLastInvestment, ctx, 720, 60, 0,
                620, 720, 1280);
        ScreenSupport.getLayoutParams(textEstimation, ctx, 720, 60, 0,
                680, 720, 1280);
        ScreenSupport.getLayoutParams(mTabHost, ctx, 720, 500, 0, 625, 720,
                1280);
        ScreenSupport.getLayoutParams(currencyGraph, ctx, 720, 435, 0, 800,
                720, 1280);

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

    /**
     * (non-Javadoc)
     * 
     * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
     */
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab
                                                                // selected
        super.onSaveInstanceState(outState);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mTabHost.setCurrentTabByTag(savedInstanceState
                    .getString("tab")); // set the tab as per the saved state
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Initialise ViewPager
     */
    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, DayFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, WeekFragment.class.getName()));
        fragments
                .add(Fragment.instantiate(this, MonthFragment.class.getName()));
        this.mPagerAdapter = new PagerAdapter(
                super.getSupportFragmentManager(), fragments);
        //
        this.mViewPager = (ViewPager) super.findViewById(R.id.tabviewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    /**
     * Initialise the Tab Host
     */
    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        DisplayMyInvestmentsGraphic.AddTab(this, this.mTabHost, this.mTabHost
                .newTabSpec("Tab1").setIndicator("Daily"),
                (tabInfo = new TabInfo("Tab1", DayFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        DisplayMyInvestmentsGraphic.AddTab(this, this.mTabHost, this.mTabHost
                .newTabSpec("Tab2").setIndicator("Weekly"),
                (tabInfo = new TabInfo("Tab2", WeekFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        DisplayMyInvestmentsGraphic.AddTab(this, this.mTabHost, this.mTabHost
                .newTabSpec("Tab3").setIndicator("3 Months"),
                (tabInfo = new TabInfo("Tab3", WeekFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        // this.onTabChanged("Tab1");
        //
        mTabHost.setOnTabChangedListener(this);
    }

    /**
     * Add Tab content to the Tabhost
     * 
     * @param activity
     * @param tabHost
     * @param tabSpec
     * @param clss
     * @param args
     */
    private static void AddTab(DisplayMyInvestmentsGraphic activity,
            TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    /**
     * (non-Javadoc)
     * 
     * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
     */
    public void onTabChanged(String tag) {
        // TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
     * (int, float, int)
     */
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see
     * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
     * (int)
     */
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#
     * onPageScrollStateChanged(int)
     */
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }

    public void getDataFromDatabase() {
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
                System.out.println(changePerDay);

                investItemList.add(code);
                investStocksList.add(new Stocks(code, daylow, dayhigh, last, lowest, highest,
                        changePerDay, name, time, invest, follow, firstInvest, lastInvest, value));
                System.out.println("Size = " + investItemList.size());

            } while (cr.moveToNext());
            System.out.println(sObject.getStrAd() + "  " + sObject.getStrKod()
                    + "  " + sObject.getDblSon() + "  "
                    + sObject.getDblYuzdeDegisimGunluk());

        }
        dbStock.close();

        cObject = new Currency();
        db.open();
        Cursor cCr = db.getInvesteds();
        if (cCr.moveToFirst()) {
            do {

                String id = cCr.getString(0);// not used
                String nm = cCr.getString(1);
                String cd = cCr.getString(2);
                Double by = Double.parseDouble(cCr.getString(3));
                Double sl = Double.parseDouble(cCr.getString(4));
                Double eb = Double.parseDouble(cCr.getString(5));
                Double es = Double.parseDouble(cCr.getString(6));
                String wy = cCr.getString(7);
                String dt = cCr.getString(8);
                String inv = cCr.getString(9);
                String flw = cCr.getString(10);
                String first = cCr.getString(11);
                String last = cCr.getString(12);
                String value = cCr.getString(13);

                investCurrencyItemList.add(cd);
                investCurrencyList.add(new Currency(nm, cd, by, sl, eb, es, "",
                        wy, dt, inv, flw, first, last, value));
                System.out.println("Size = " + investItemList.size());

            } while (cCr.moveToNext());
            // System.out.println(sObject.getStrAd() + "  " +
            // sObject.getStrKod()
            // + "  " + cObject.getDblSon() + "  "
            // + cObject.getDblYuzdeDegisimGunluk());

        }
        db.close();

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent intent = new Intent(getApplicationContext(),
                    MainScreen.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
