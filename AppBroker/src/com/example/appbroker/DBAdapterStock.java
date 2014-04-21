package com.example.appbroker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapterStock {
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private final Context context;

	public static final String KEY_ROWID = "id";
	public static final String KEY_KOD = "strKod";
	public static final String KEY_SERI = "strSeri";
	public static final String KEY_PIYASA = "strPiyasa";
	public static final String KEY_SON = "dblSon";
	public static final String KEY_OSON = "dblOncekiSon";
	public static final String KEY_SONA = "dblSonAdet";
	public static final String KEY_SAAT = "strSaat";
	public static final String KEY_EIA = "dblEnIyiAlis";
	public static final String KEY_EIS = "dblEnIyiSatis";
	public static final String KEY_ED1 = "dblEnDusuk1";
	public static final String KEY_ED2 = "dblEnDusuk2";
	public static final String KEY_EY1 = "dblEnYuksek1";
	public static final String KEY_EY2 = "dblEnYuksek2";
	public static final String KEY_GED = "dblGunEnDusuk";
	public static final String KEY_GEY = "dblGunEnYuksek";
	public static final String KEY_KAP1 = "dblKapanis1";
	public static final String KEY_KAP2 = "dblKapanis2";
	public static final String KEY_OK1 = "dblOncekiKapanis1";
	public static final String KEY_OK2 = "dblOncekiKapanis2";
	public static final String KEY_TH1 = "dblToplamHacim1";
	public static final String KEY_TH2 = "dblToplamHacim2";
	public static final String KEY_TA1 = "dblToplamAdet1";
	public static final String KEY_TA2 = "dblToplamAdet2";
	public static final String KEY_ORT1 = "dblOrtalama1";
	public static final String KEY_ORT2 = "dblOrtalama2";
	public static final String KEY_TAB = "dblTaban";
	public static final String KEY_TAV = "dblTavan";
	public static final String KEY_YD = "dblYuzdeDegisim";
	public static final String KEY_FS = "dblFarkSeans";
	public static final String KEY_YDG = "dblYuzdeDegisimGunluk";
	public static final String KEY_FG = "dblFarkGunluk";
	public static final String KEY_FA = "dblFiyatAdimi";
	public static final String KEY_GK = "strGrupKodu";
	public static final String KEY_AD = "strAd";
	public static final String KEY_END = "strEndeks";
	private static final String TAG = "DBAdapterStock"; // For Logcat

	private static final String DATABASE_NAME = "investmentDB";
	private static final String DATABASE_TABLE = "stocks";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table currency (id integer primary key autoincrement, "
			+ "strKod text not null,"
			+ "strSeri text not null,"
			+ "strPiyasa text not null, "
			+ "dblSon numeric not null, "
			+ "dblOncekiSon numeric not null, "
			+ "dblSonAdet numeric not null, "
			+ "strSaat text not null, "
			+ "dblEnIyiAlis numeric not null, "
			+ "dblEnIyiSatis numeric not null, "
			+ "dblEnDusuk1 numeric not null, "
			+ "dblEnDusuk2 numeric not null, "
			+ "dblGunEnDusuk numeric not null, "
			+ "dblGunEnYuksek numeric not null, "
			+ "dblKapanis1 numeric not null, "
			+ "dblKapanis2 numeric not null, "
			+ "dblOncekiKapanis1 numeric not null, "
			+ "dblOncekiKapanis2 numeric not null, "
			+ "dblToplamHacim1 numeric not null, "
			+ "dblToplamHacim2 numeric not null, "
			+ "dblToplamAdet1 numeric not null, "
			+ "dblToplamAdet2 numeric not null, "
			+ "dblOrtalama1 numeric not null, "
			+ "dblOrtalama2 numeric not null, "
			+ "dblTaban numeric not null, "
			+ "dblTavan numeric not null, "
			+ "dblYuzdeDegisim numeric not null, "
			+ "dblFarkSeans numeric not null, "
			+ "dblFiyatAdimi numeric not null, "
			+ "strGrupKodu text not null, "
			+ "strAd text not null, "
			+ "strEndeks text not null);";

	// Constructor
	public DBAdapterStock(Context context) {
		this.context = context;
		DBHelper = new DatabaseHelper(context);
	}

	// To create and upgrade a database in an Android application
	// SQLiteOpenHelper subclass is usually created
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// onCreate() is called by the framework, if the database does not
			// exist
			Log.d("Create", "Creating the database");

			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Sends a Warn log message
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");

			// Method to execute an SQL statement directly
			db.execSQL("DROP TABLE IF EXISTS stocks");
			onCreate(db);
		}
	}

	// Opens the database
	public DBAdapterStock open() throws SQLException {
		// Create and/or open a database that will be used for reading and
		// writing
		db = DBHelper.getWritableDatabase();

		// Use if you only want to read data from the database
		// db = DBHelper.getReadableDatabase();
		return this;
	}

	// Closes the database
	public void close() {
		// Closes the database
		DBHelper.close();
	}

	// Insert a contact into the database
	public long insertCurrencies(String strKod, String strSeri,
			String strPiyasa, String sell, double dblSon, double dblOncekiSon,
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
		// The class ContentValues allows to define key/values. The "key"
		// represents the
		// table column identifier and the "value" represents the content for
		// the table
		// record in this column. ContentValues can be used for inserts and
		// updates of database entries.
		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_KOD, strKod);
		initialValues.put(KEY_SERI, strSeri);
		initialValues.put(KEY_PIYASA, strPiyasa);
		initialValues.put(KEY_SON, sell);
		initialValues.put(KEY_OSON, dblSon);
		initialValues.put(KEY_SAAT, strSaat);
		initialValues.put(KEY_EIA, dblEnIyiAlis);
		initialValues.put(KEY_EIS, dblEnIyiSatis);
		initialValues.put(KEY_ED1, dblEnDusuk1);
		initialValues.put(KEY_ED2, dblEnDusuk2);
		initialValues.put(KEY_EY1, dblEnYuksek1);
		initialValues.put(KEY_EY2, dblEnYuksek2);
		initialValues.put(KEY_GED, dblGunEnDusuk);
		initialValues.put(KEY_GEY, dblGunEnYuksek);
		initialValues.put(KEY_KAP1, dblKapanis1);
		initialValues.put(KEY_KAP2, dblKapanis2);
		initialValues.put(KEY_OK1, dblOncekiKapanis1);
		initialValues.put(KEY_OK2, dblOncekiKapanis2);
		initialValues.put(KEY_TH1, dblToplamHacim1);
		initialValues.put(KEY_TH2, dblToplamHacim2);
		initialValues.put(KEY_TA1, dblToplamAdet1);
		initialValues.put(KEY_TA2, dblToplamAdet2);
		initialValues.put(KEY_ORT1, dblOrtalama1);
		initialValues.put(KEY_ORT2, dblOrtalama2);
		initialValues.put(KEY_TAB, dblTaban);
		initialValues.put(KEY_TAV, dblTavan);
		initialValues.put(KEY_YD, dblYuzdeDegisim);
		initialValues.put(KEY_FS, dblFarkSeans);
		initialValues.put(KEY_YDG, dblYuzdeDegisimGunluk);
		initialValues.put(KEY_FG, dblFarkGunluk);
		initialValues.put(KEY_FA, dblFiyatAdimi);
		initialValues.put(KEY_GK, strGrupKodu);
		initialValues.put(KEY_AD, strAd);
		initialValues.put(KEY_END, strEndeks);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// Deletes a particular contact
	public boolean deleteContact(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// Retrieves all the contacts
	public Cursor getAllCurrencies() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_KOD,
				KEY_AD, KEY_YDG, KEY_YD, KEY_SAAT }, null, null, null, null,
				null);
	}

	// Retrieves a particular contact
	public Cursor getStockHeader(String strKod) throws SQLException {

		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_AD, KEY_KOD, KEY_YD, KEY_YDG, }, KEY_KOD + "='"
				+ strKod + "'", null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// Retrieves a particular contact
	public Cursor getStockHeaderById(long rowId) throws SQLException {

		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_AD, KEY_KOD, KEY_SON, KEY_YDG, }, KEY_ROWID
				+ "='" + rowId + "'", null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	// Retrieves a particular contact
		public Cursor getStockDetail(String strKod) throws SQLException {

			Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
					 KEY_AD, KEY_KOD, KEY_SON, KEY_YDG, KEY_GED , KEY_GEY, KEY_EIA, KEY_EIS, KEY_TAB, KEY_TAV, KEY_SAAT}, KEY_KOD
					+ "='" + strKod + "'", null, null, null, null, null);

			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
		}

	// Retrieves a particular contact
	// public Cursor getInteresteds(String follow) throws SQLException {
	//
	// Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
	// KEY_ROWID, KEY_NAME, KEY_CODE, KEY_BUY, KEY_SELL, KEY_EBUY,
	// KEY_ESELL, KEY_WAY, KEY_DATE, KEY_INVEST, KEY_FLW }, KEY_FLW
	// + "='" + follow + "'", null, null, null, null, null);
	//
	// if (mCursor != null) {
	// mCursor.moveToFirst();
	// }
	// return mCursor;
	// }
	//
	// // Retrieves a particular contact
	// public Cursor getInvesteds(String invest) throws SQLException {
	//
	// Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
	// KEY_ROWID, KEY_NAME, KEY_CODE, KEY_BUY, KEY_SELL, KEY_EBUY,
	// KEY_ESELL, KEY_WAY, KEY_DATE, KEY_INVEST, KEY_FLW }, KEY_INVEST
	// + "='" + invest + "'", null, null, null, null, null);
	//
	// if (mCursor != null) {
	// mCursor.moveToFirst();
	// }
	// return mCursor;
	// }
	//
	// public Cursor getCurrencyExt(int rowId) throws SQLException {
	//
	// Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
	// KEY_ROWID, KEY_INVEST, KEY_FLW }, KEY_ROWID + "='" + rowId
	// + "'", null, null, null, null, null);
	//
	// if (mCursor != null) {
	// mCursor.moveToFirst();
	// }
	// return mCursor;
	// }

	// public Cursor getStudentsInfo(int id) throws SQLException {
	//
	// Cursor mCursor = db.query(true, DATABASE_TABLE,
	// new String[] { KEY_STD_ID, KEY_NAME, KEY_SNAME, KEY_MDTRM,
	// KEY_PRJCT, KEY_FNL }, KEY_ROWID + "='" + id + "'",
	// null, null, null, null, null);
	//
	// if (mCursor != null) {
	// mCursor.moveToFirst();
	// }
	// return mCursor;
	// }

	// Updates a contact
	public boolean updateStocks(long rowId, String strKod, String strSeri,
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
		// This methods returns the number of rows affected by the conducted
		// operation
		ContentValues args = new ContentValues();
		args.put(KEY_KOD, strKod);
		args.put(KEY_SERI, strSeri);
		args.put(KEY_PIYASA, strPiyasa);
		args.put(KEY_SON, dblSon);
		args.put(KEY_OSON, dblOncekiSon);
		args.put(KEY_SAAT, strSaat);
		args.put(KEY_EIA, dblEnIyiAlis);
		args.put(KEY_EIS, dblEnIyiSatis);
		args.put(KEY_ED1, dblEnDusuk1);
		args.put(KEY_ED2, dblEnDusuk2);
		args.put(KEY_EY1, dblEnYuksek1);
		args.put(KEY_EY2, dblEnYuksek2);
		args.put(KEY_GED, dblGunEnDusuk);
		args.put(KEY_GEY, dblGunEnYuksek);
		args.put(KEY_KAP1, dblKapanis1);
		args.put(KEY_KAP2, dblKapanis2);
		args.put(KEY_OK1, dblOncekiKapanis1);
		args.put(KEY_OK2, dblOncekiKapanis2);
		args.put(KEY_TH1, dblToplamHacim1);
		args.put(KEY_TH2, dblToplamHacim2);
		args.put(KEY_TA1, dblToplamAdet1);
		args.put(KEY_TA2, dblToplamAdet2);
		args.put(KEY_ORT1, dblOrtalama1);
		args.put(KEY_ORT2, dblOrtalama2);
		args.put(KEY_TAB, dblTaban);
		args.put(KEY_TAV, dblTavan);
		args.put(KEY_YD, dblYuzdeDegisim);
		args.put(KEY_FS, dblFarkSeans);
		args.put(KEY_YDG, dblYuzdeDegisimGunluk);
		args.put(KEY_FG, dblFarkGunluk);
		args.put(KEY_FA, dblFiyatAdimi);
		args.put(KEY_GK, strGrupKodu);
		args.put(KEY_AD, strAd);
		args.put(KEY_END, strEndeks);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}

}
