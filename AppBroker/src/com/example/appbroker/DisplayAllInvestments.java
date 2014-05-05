
package com.example.appbroker;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayAllInvestments extends Activity {
    private static DBAdapter db;
    private static DBAdapterStock dbStock;
    public static ArrayList<Currency> currency_list = new ArrayList<Currency>();
    public static ArrayList<StocksHeader> stocks_header_list = new ArrayList<StocksHeader>();
    public static ArrayList<Stocks> stocks_list = new ArrayList<Stocks>();
    public ArrayList<String> spinner_list = new ArrayList<String>();
    public ArrayAdapter<StocksHeader> stockAdapter;
    Spinner spinner;
    ListView list_curr;
    ListView list_stock;
    ImageView ustbar, listbar;
    TextView borsa;
    Context ctx = this;
    ConnectivityManager conMgr;
    private ProgressDialog pd;
    CurrencyListAdapter cAdapter;
    Button dispAllInv_button, dispMyInv_button, dispMyInt, dispGraph;

    // flags for asynctask
    boolean blMyAsyncTask;
    boolean cancelTask;

    public static Object packageName;
    // All static variables
    static final String URL = "http://www.owebtools.com/xmltcmbdoviz.php";
    public GetStockData getStockData = new GetStockData();
    // refresh
    public ProgressBar refreshBar;
    public Button refreshButton;

    // XML node names

    static final String NODE_DVZ = "doviz";
    static final String NODE_CODE = "kod";
    static final String NODE_NAME = "parabirimi";
    static final String NODE_BUY = "alis";
    static final String NODE_SELL = "satis";
    static final String NODE_EBUY = "ealis";
    static final String NODE_ESELL = "esatis";
    static final String NODE_LCODE = "lirakod";
    static final String NODE_WAY = "yon";
    static final String NODE_DATE = "zaman";

    // Json stock variables
    JSONParser jsonparser = new JSONParser();
    JSONObject jobj = null;

    public static String[] stock_codes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_display_all_investments);
        stocks_header_list.clear();
        currency_list.clear();
        spinner_list.clear();
        packageName = getPackageName();
        stock_codes = getResources().getStringArray(R.array.all_stock_array);
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
        stockAdapter = new StocksListAdapter(getApplicationContext(),
                stocks_header_list);
        // Database Adapters
        db = new DBAdapter(this);
        dbStock = new DBAdapterStock(this);

        ustbar = (ImageView) findViewById(R.id.allinvest_ustbar);
        listbar = (ImageView) findViewById(R.id.allinvest_list_ustbar);
        spinner = (Spinner) findViewById(R.id.spinner1);
        list_curr = (ListView) findViewById(R.id.listView1);
        list_stock = (ListView) findViewById(R.id.listView2);
        borsa = (TextView) findViewById(R.id.imgviewBorsa);

        // refresh items
        refreshBar = (ProgressBar) findViewById(R.id.allinvest_progress);
        refreshButton = (Button) findViewById(R.id.allinvest_refresh);

        refreshButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                GetStockData getStockData = new GetStockData();
                getStockData.execute();
                refreshBar.setVisibility(View.VISIBLE);
                refreshButton.setVisibility(View.GONE);
            }
        });
        // Initializes the progress dialog object
        pd = new ProgressDialog(ctx);

        checkConnectivity();
        // Gets the network info and Checks if there is a connection
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        // If the device is not connected a network, makes a toast message and
        // finishes the activity before getting error/null pointer
        if (i == null || !i.isConnected() || !i.isAvailable()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Not connected a network. All Data will be dilplayed from local database.",
                    0).show();
            showStocksFromDatabaseNoNetwork();
            // finish();
        }
        // If the device is connected a network, starts the progress of
        // downloading and parsing XML File
        else {
            GetXMLTask task = new GetXMLTask(this);
            task.execute(new String[] {
                URL
            });
            showStocksFromDatabase();

        }
        spinner_list.add("Display All Currency Items");
        spinner_list.add("Display All Stock Certificates");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item_text, spinner_list);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
                    long arg3) {
                // If the First item of spinner is selected makes the currency
                // list visible
                if (pos == 0) {
                    list_curr.setVisibility(View.VISIBLE);
                    list_stock.setVisibility(View.INVISIBLE);
                }
                // If the Second item of spinner is selected makes the stocks
                // list visible
                else if (pos == 1) {
                    list_stock.setVisibility(View.VISIBLE);
                    list_curr.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        list_stock.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                // TODO Auto-generated method stub
                String kod = ((TextView) view.findViewById(R.id.code_stocks))
                        .getText().toString();
                Intent i = new Intent(ctx, StockDetailActivity.class);
                i.putExtra("kod", kod);
                startActivity(i);

            }
        });

        ScreenSupport.getLayoutParams(ustbar, ctx, 720, 100, 0, 0, 720, 1280);
        ScreenSupport
                .getLayoutParams(spinner, ctx, 720, 120, 0, 110, 720, 1280);
        ScreenSupport.getLayoutParams(listbar, ctx, 720, 30, 0, 250, 720, 1280);
        ScreenSupport.getLayoutParams(refreshBar, ctx, 100, 100, 620, 0, 720,
                1280);
        ScreenSupport.getLayoutParams(refreshButton, ctx, 100, 100, 620, 0,
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_all_investments, menu);
        return true;
    }

    // private inner class extending AsyncTask
    private class GetXMLTask extends AsyncTask<String, Void, String> {
        private Activity context;

        public GetXMLTask(Activity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... urls) {
            // Creates null string for XML File
            String xml = null;
            // Fills the null string with XML file from URL
            for (String url : urls) {
                xml = getXmlFromUrl(url);
            }
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            pd.dismiss();
            // Creates the XML parser object and necessery other objects.
            XMLDOMParser parser = new XMLDOMParser();
            InputStream stream = new ByteArrayInputStream(xml.getBytes());
            Document doc = parser.getDocument(stream);

            NodeList nodeList = doc.getElementsByTagName(NODE_DVZ);

            // Creates the null Temp Currency obj, and fills the obj in order to
            // pasing XML progress.
            Currency c = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                // Initializes the temp Currency object
                c = new Currency();
                // Creates element objects
                Element e = (Element) nodeList.item(i);
                // Sets each node elements to Currency object
                c.setCode(parser.getValue(e, NODE_CODE));
                c.setName(parser.getValue(e, NODE_NAME));
                c.setBuy(Double.parseDouble(parser.getValue(e, NODE_BUY)));
                c.setSell(Double.parseDouble(parser.getValue(e, NODE_SELL)));
                c.setEbuy(Double.parseDouble(parser.getValue(e, NODE_EBUY)));
                c.setEsell(Double.parseDouble(parser.getValue(e, NODE_ESELL)));
                c.setTl(parser.getValue(e, NODE_LCODE));
                c.setWay(parser.getValue(e, NODE_WAY));
                c.setDate(parser.getValue(e, NODE_DATE));
                // c.setInvest("Y");
                // c.setFollow("Y");
                
                updateDBCurrency(i + 1, c.getName(), c.getCode(),
                        "" + c.getBuy(), "" + c.getSell(), "" + c.getEbuy(), ""
                                + c.getEsell(), c.getWay(), c.getDate());

                db.open();

                String scode = c.getCode();
                Cursor cr = db.getCurrency(scode);

                if (cr.moveToFirst()) {
                    do {
                        String id = cr.getString(0);
                        String nm = cr.getString(1);
                        String cd = cr.getString(2);
                        Double by = Double.parseDouble(cr.getString(3));
                        Double sl = Double.parseDouble(cr.getString(4));
                        Double eb = Double.parseDouble(cr.getString(5));
                        Double es = Double.parseDouble(cr.getString(6));
                        String wy = cr.getString(7);
                        String dt = cr.getString(8);
                        c.setInvest(cr.getString(9));
                        c.setFollow(cr.getString(10));

                    } while (cr.moveToNext());
                }

                db.close();

                // Adds Temp Currency object to arraylist
                currency_list.add(c);
                System.out.println("Sýra : " + c.getCode() + "  " + i);

            }
            // Sets the Listview Adapter at the end of parsing XML process
            cAdapter = new CurrencyListAdapter(ctx, currency_list);
            cAdapter.notifyDataSetChanged();
            list_curr.setAdapter(cAdapter);
            // .notifyDataSetChanged()
            // list_stock.setAdapter(new StocksListAdapter(ctx, stocks_list));

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // At the begining of asyncTask progress, starts the progress dialog
            pd.setCanceledOnTouchOutside(true);
            pd.setCancelable(true);
            pd.setMessage("Yükleniyor...");
            pd.show();

        }

        /*
         * uses HttpURLConnection to make Http request from Android to download
         * the XML file
         */
        private String getXmlFromUrl(String urlString) {
            StringBuffer output = new StringBuffer("");

            InputStream stream = null;
            URL url;
            try {
                url = new URL(urlString);
                URLConnection connection = url.openConnection();

                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(stream));
                    String s = "";
                    while ((s = buffer.readLine()) != null)
                        output.append(s);
                }
            } catch (MalformedURLException e) {
                Log.e("Error", "Unable to parse XML File", e);
            } catch (IOException e) {
                Log.e("Error", "IO Exception", e);
            }

            return output.toString();

            // For applications targeting Froyo(Android 2.2) and previous
            // versions
            /*
             * String xml = null; try { DefaultHttpClient httpClient = new
             * DefaultHttpClient(); HttpGet httpGet = new HttpGet(url);
             * HttpResponse httpResponse = httpClient.execute(httpGet);
             * HttpEntity httpEntity = httpResponse.getEntity(); xml =
             * EntityUtils.toString(httpEntity); } catch
             * (UnsupportedEncodingException e) { e.printStackTrace(); } catch
             * (ClientProtocolException e) { e.printStackTrace(); } catch
             * (IOException e) { e.printStackTrace(); } return xml;
             */
        }
    }

    // Stock data JSON parse with asynctask
    class GetStockData extends AsyncTask<String, String, String> {
        StocksHeader sh;
        Stocks s;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // At the begining of asyncTask progress, starts the progress dialog
            blMyAsyncTask = true;
            refreshBar.setPressed(true);
        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub

            try {
                dbStock.open();
                for (int i = 0; i < 308; i++) {
                    if (cancelTask == false) {
                        jobj = jsonparser
                                .makeHttpRequest("http://api.piyasa.com/json/?kaynak=hisse_guncel_detay_"
                                        + stock_codes[i]);
                        if (isCancelled()) {
                            dbStock.close();
                            cancelTask = true;
                            break;
                        }
                        s = new Stocks();
                        s.setStrKod(jobj.getString("strKod"));
                        s.setStrAd(jobj.getString("strSeri"));
                        s.setStrPiyasa(jobj.getString("strPiyasa"));
                        s.setDblSon(jobj.getDouble("dblSon"));
                        s.setDblOncekiSon(jobj.getDouble("dblOncekiSon"));
                        s.setDblSonAdet(jobj.getDouble("dblSonAdet"));
                        s.setStrSaat(jobj.getString("strSaat"));
                        s.setDblEnIyiAlis(jobj.getDouble("dblEnIyiAlis"));
                        s.setDblEnIyiSatis(jobj.getDouble("dblEnIyiSatis"));
                        s.setDblEnDusuk1(jobj.getDouble("dblEnDusuk1"));
                        s.setDblEnDusuk2(jobj.getDouble("dblEnDusuk2"));
                        s.setDblEnYuksek1(jobj.getDouble("dblEnYuksek1"));
                        s.setDblEnYuksek2(jobj.getDouble("dblEnYuksek2"));
                        s.setDblGunEnDusuk(jobj.getDouble("dblGunEnDusuk"));
                        s.setDblGunEnYuksek(jobj.getDouble("dblGunEnYuksek"));
                        s.setDblKapanis1(jobj.getDouble("dblKapanis1"));
                        s.setDblKapanis2(jobj.getDouble("dblKapanis2"));
                        s.setDblOncekiKapanis1(jobj
                                .getDouble("dblOncekiKapanis1"));
                        s.setDblOncekiKapanis2(jobj
                                .getDouble("dblOncekiKapanis2"));
                        s.setDblToplamHacim1(jobj.getDouble("dblToplamHacim1"));
                        s.setDblToplamHacim2(jobj.getDouble("dblToplamHacim2"));
                        s.setDblToplamAdet1(jobj.getDouble("dblToplamAdet1"));
                        s.setDblToplamAdet2(jobj.getDouble("dblToplamAdet2"));
                        s.setDblOrtalama1(jobj.getDouble("dblOrtalama1"));
                        s.setDblOrtalama2(jobj.getDouble("dblOrtalama2"));
                        s.setDblTaban(jobj.getDouble("dblTaban"));
                        s.setDblTavan(jobj.getDouble("dblTavan"));
                        s.setDblYuzdeDegisim(jobj.getDouble("dblYuzdeDegisim"));
                        s.setDblFarkSeans(jobj.getDouble("dblFarkSeans"));
                        s.setDblYuzdeDegisimGunluk(jobj
                                .getDouble("dblYuzdeDegisimGunluk"));
                        s.setDblFarkGunluk(jobj.getDouble("dblFarkGunluk"));
                        s.setDblFiyatAdimi(jobj.getDouble("dblFiyatAdimi"));
                        s.setStrGrupKodu(jobj.getString("strGrupKodu"));
                        s.setStrAd(jobj.getString("strAd"));
                        s.setStrEndeks(jobj.getString("strEndeks"));

                        sh = new StocksHeader();
                        sh.setStrKod(s.getStrKod());
                        sh.setStrAd(s.getStrAd());
                        sh.setDblSon(s.getDblSon());
                        sh.setDblYuzdeDegisimGunluk(s
                                .getDblYuzdeDegisimGunluk());

                        if (!stocks_header_list.isEmpty()) {
                            stocks_header_list.set(i, sh);
                        }
                        stocks_list.add(s);

                        dbStock.updateStocks(i + 1, s.getStrKod(),
                                s.getStrSeri(), s.getStrPiyasa(),
                                s.getDblSon(), s.getDblOncekiSon(),
                                s.getDblSonAdet(), s.getStrSaat(),
                                s.getDblEnIyiAlis(), s.getDblEnIyiSatis(),
                                s.getDblEnDusuk1(), s.getDblEnDusuk2(),
                                s.getDblEnYuksek1(), s.getDblEnYuksek2(),
                                s.getDblGunEnDusuk(), s.getDblGunEnYuksek(),
                                s.getDblKapanis1(), s.getDblKapanis2(),
                                s.getDblOncekiKapanis1(),
                                s.getDblOncekiKapanis2(),
                                s.getDblToplamHacim1(), s.getDblToplamHacim2(),
                                s.getDblToplamAdet1(), s.getDblToplamAdet2(),
                                s.getDblOrtalama1(), s.getDblOrtalama2(),
                                s.getDblTaban(), s.getDblTavan(),
                                s.getDblYuzdeDegisim(), s.getDblFarkSeans(),
                                s.getDblYuzdeDegisimGunluk(),
                                s.getDblFarkGunluk(), s.getDblFiyatAdimi(),
                                s.getStrGrupKodu(), s.getStrAd(),
                                s.getStrEndeks());
                        System.out.println(s.getStrKod() + " UPDATED.");

                    }
                }
                dbStock.close();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return "a";
        }

        protected void onPostExecute(String ab) {
            // Finishes the progress dialog after downloading XML file
            // pd.dismiss();
            blMyAsyncTask = false;
            refreshBar.setVisibility(View.GONE);
            refreshButton.setVisibility(View.VISIBLE);
            
        }

    }

    // Checks if the device is connected a notwork
    public void checkConnectivity() {

        conMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    public static void updateDBCurrency(int row, String name, String code,
            String buy, String sell, String ebuy, String esell, String way,
            String date) {
        db.open();
        if (code.equals("AUD")) {
            name = "Australian dollar";
        } else if (code.equals("CAD")) {
            name = "Canadian dollar";
        } else if (code.equals("CHF")) {
            name = "Swiss franc";
        } else if (code.equals("DKK")) {
            name = "Danish krone";
        } else if (code.equals("EUR")) {
            name = "Euro";
        } else if (code.equals("GBP")) {
            name = "Pound sterling";
        } else if (code.equals("JPY")) {
            name = "Japanese Yen";
        } else if (code.equals("KWD")) {
            name = "Kuwaiti dinar";
        } else if (code.equals("NOK")) {
            name = "Norwegian krone";
        } else if (code.equals("SEK")) {
            name = "Swedish krona";
        } else if (code.equals("SAR")) {
            name = "Saudi riyal";
        } else if (code.equals("USD")) {
            name = "United States dollar";
        }
        db.updateCurrencyStart(row, name, code, buy, sell, ebuy, esell, way,
                date);
        db.close();
    }

    public static void updateDB(int row, String name, String code, String buy,
            String sell, String ebuy, String esell, String way, String date,
            String invest, String follow) {
        if (code.equals("AUD")) {
            name = "Australian dollar";
        } else if (code.equals("CAD")) {
            name = "Canadian dollar";
        } else if (code.equals("CHF")) {
            name = "Swiss franc";
        } else if (code.equals("DKK")) {
            name = "Danish krone";
        } else if (code.equals("EUR")) {
            name = "Euro";
        } else if (code.equals("GBP")) {
            name = "Pound sterling";
        } else if (code.equals("JPY")) {
            name = "Japanese Yen";
        } else if (code.equals("KWD")) {
            name = "Kuwaiti dinar";
        } else if (code.equals("NOK")) {
            name = "Norwegian krone";
        } else if (code.equals("SEK")) {
            name = "Swedish krona";
        } else if (code.equals("SAR")) {
            name = "Saudi riyal";
        } else if (code.equals("USD")) {
            name = "United States dollar";
        }
        db.open();
        db.updateCurrency(row, name, code, buy, sell, ebuy, esell, way, date,
                invest, follow);
        db.close();
    }

    public static void updateDBStock(long rowId, String strKod, String strSeri,
            String strPiyasa, double dblSon, double dblOncekiSon,
            double dblSonAdet, String strSaat, double dblEnIyiAlis,
            double dblEnIyiSatis, double dblEnDusuk1, double dblEnDusuk2,
            double dblEnYuksek1, double dblEnYuksek2, double dblGunEnDusuk,
            double dblGunEnYuksek, double dblKapanis1, double dblKapanis2,
            double dblOncekiKapanis1, double dblOncekiKapanis2,
            double dblToplamHacim1, double dblToplamHacim2,
            double dblToplamAdet1, double dblToplamAdet2, double dblOrtalama1,
            double dblOrtalama2, double dblTaban, double dblTavan,
            double dblYuzdeDegisim, double dblFarkSeans,
            double dblYuzdeDegisimGunluk, double dblFarkGunluk,
            double dblFiyatAdimi, String strGrupKodu, String strAd,
            String strEndeks) {
        dbStock.open();
        dbStock.updateStocks(rowId, strKod, strSeri, strPiyasa, dblSon,
                dblOncekiSon, dblSonAdet, strSaat, dblEnIyiAlis, dblEnIyiSatis,
                dblEnDusuk1, dblEnDusuk2, dblEnYuksek1, dblEnYuksek2,
                dblGunEnDusuk, dblGunEnYuksek, dblKapanis1, dblKapanis2,
                dblOncekiKapanis1, dblOncekiKapanis2, dblToplamHacim1,
                dblToplamHacim2, dblToplamAdet1, dblToplamAdet2, dblOrtalama1,
                dblOrtalama2, dblTaban, dblTavan, dblYuzdeDegisim,
                dblFarkSeans, dblYuzdeDegisimGunluk, dblFarkGunluk,
                dblFiyatAdimi, strGrupKodu, strAd, strEndeks);
        dbStock.close();
    }

    public void showStocksFromDatabase() {

        dbStock.open();

        StocksHeader sHeader = null;

        for (int i = 0; i < 308; i++) {
            Cursor cr = dbStock.getStockHeaderById(i + 1);
            if (cr.moveToFirst()) {
                do {
                    sHeader = new StocksHeader();
                    String ad = cr.getString(1);
                    String kod = cr.getString(2);
                    Double son = cr.getDouble(3);
                    Double yuzdeDegisimGunluk = cr.getDouble(4);
                    sHeader.setStrAd(ad);
                    sHeader.setStrKod(kod);
                    sHeader.setDblSon(son);
                    sHeader.setDblYuzdeDegisimGunluk(yuzdeDegisimGunluk);

                    stocks_header_list.add(sHeader);
                } while (cr.moveToNext());
                System.out.println(sHeader.getStrAd() + "  "
                        + sHeader.getStrKod() + "  " + sHeader.getDblSon()
                        + "  " + sHeader.getDblYuzdeDegisimGunluk());
            }
        }

        list_stock.setAdapter(stockAdapter);
        GetStockData getStockData = new GetStockData();
        getStockData.execute();
        dbStock.close();
    }

    public void showStocksFromDatabaseNoNetwork() {
        // Gets currency database items and adds to listview
        db.open();
        Cursor cr = db.getAllCurrencies();
        Currency c;
        if (cr.moveToFirst()) {
            do {
                String id = cr.getString(0);
                String nm = cr.getString(1);
                String cd = cr.getString(2);
                Double by = Double.parseDouble(cr.getString(3));
                Double sl = Double.parseDouble(cr.getString(4));
                Double eb = Double.parseDouble(cr.getString(5));
                Double es = Double.parseDouble(cr.getString(6));
                String wy = cr.getString(7);
                String dt = cr.getString(8);
                if (cd.equals("AUD")) {
                    nm = "Australian dollar";
                } else if (cd.equals("CAD")) {
                    nm = "Canadian dollar";
                } else if (cd.equals("CHF")) {
                    nm = "Swiss franc";
                } else if (cd.equals("DKK")) {
                    nm = "Danish krone";
                } else if (cd.equals("EUR")) {
                    nm = "Euro";
                } else if (cd.equals("GBP")) {
                    nm = "Pound sterling";
                } else if (cd.equals("JPY")) {
                    nm = "Japanese Yen";
                } else if (cd.equals("KWD")) {
                    nm = "Kuwaiti dinar";
                } else if (cd.equals("NOK")) {
                    nm = "Norwegian krone";
                } else if (cd.equals("SEK")) {
                    nm = "Swedish krona";
                } else if (cd.equals("SAR")) {
                    nm = "Saudi riyal";
                } else if (cd.equals("USD")) {
                    nm = "United States dollar";
                }
                c = new Currency(nm, cd, by, sl, eb, es, "", wy, dt, cr.getString(9),
                        cr.getString(10));
                currency_list.add(c);
            } while (cr.moveToNext());
        }
        list_curr.setAdapter(new CurrencyListAdapter(ctx, currency_list));
        db.close();

        // Adds Temp Currency object to arraylist

        refreshBar.setVisibility(View.GONE);
        // //Gets stocks database items and adds to listview
        dbStock.open();
        StocksHeader sHeader = null;
        for (int i = 0; i < 308; i++) {
            Cursor crs = dbStock.getStockHeaderById(i + 1);
            if (cr.moveToFirst()) {
                do {
                    sHeader = new StocksHeader();
                    String ad = crs.getString(1);
                    String kod = crs.getString(2);
                    Double son = crs.getDouble(3);
                    Double yuzdeDegisimGunluk = crs.getDouble(4);
                    sHeader.setStrAd(ad);
                    sHeader.setStrKod(kod);
                    sHeader.setDblSon(son);
                    sHeader.setDblYuzdeDegisimGunluk(yuzdeDegisimGunluk);

                    stocks_header_list.add(sHeader);
                } while (crs.moveToNext());
                System.out.println(sHeader.getStrAd() + "  "
                        + sHeader.getStrKod() + "  " + sHeader.getDblSon()
                        + "  " + sHeader.getDblYuzdeDegisimGunluk());
            }
        }
        stockAdapter.notifyDataSetChanged();
        list_stock.setAdapter(stockAdapter);

        dbStock.close();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            blMyAsyncTask = false;
            cancelTask = true;
            getStockData.cancel(true);
            currency_list.clear();
            spinner_list.clear();
            // stocks_header_list.clear();
            stocks_list.clear();
            finish();

            Intent intent = new Intent(getApplicationContext(),
                    MainScreen.class);
            startActivity(intent);

        }
        return super.onKeyDown(keyCode, event);
    }

    public void onPause() {
        super.onPause();
        if (blMyAsyncTask == true) {
            getStockData.cancel(true);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (blMyAsyncTask == true) {
            getStockData.cancel(true);
            blMyAsyncTask = false;
            cancelTask = true;
            currency_list.clear();
            spinner_list.clear();
            // stocks_header_list.clear();
            stocks_list.clear();
        }

    }
}
