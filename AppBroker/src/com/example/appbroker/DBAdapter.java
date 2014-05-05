package com.example.appbroker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private final Context context;

	public static final String KEY_ROWID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_CODE = "code";
	public static final String KEY_BUY = "buy";
	public static final String KEY_SELL = "sell";
	public static final String KEY_EBUY = "ebuy";
	public static final String KEY_ESELL = "esell";
	public static final String KEY_WAY = "way";
	public static final String KEY_DATE = "date";
	public static final String KEY_INVEST = "invest";
	public static final String KEY_FLW = "follow";
	public static final String KEY_FRS = "first_invest";
	public static final String KEY_LST = "last_invest";
	public static final String KEY_LVAL = "lastValue";
	private static final String TAG = "DBAdapter"; // For Logcat

	private static final String DATABASE_NAME = "investmentDB";
	private static final String DATABASE_TABLE = "currency";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table currency (id integer primary key autoincrement, "
						+ "name text not null, code text not null, buy numeric not null, sell numeric not null, " +
						"ebuy numeric not null, esell numeric not null, way text not null, " +
						"date text not null, invest text not null, follow text not null, first_invest text not null," +
						" last_invest text not null, lastValue text not null);";

	// Constructor
	public DBAdapter(Context context) {
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
			db.execSQL("DROP TABLE IF EXISTS currency");
			onCreate(db);
		}
	}

	// Opens the database
	public DBAdapter open() throws SQLException {
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
	public long insertCurrencies(String name, String code, String buy,
			String sell, String ebuy, String esell, String way, String date,
			String invest, String follow,String first_invest, String last_invest,String lastValue) {
		// The class ContentValues allows to define key/values. The "key"
		// represents the
		// table column identifier and the "value" represents the content for
		// the table
		// record in this column. ContentValues can be used for inserts and
		// updates of database entries.
		ContentValues initialValues = new ContentValues();

		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_CODE, code);
		initialValues.put(KEY_BUY, buy);
		initialValues.put(KEY_SELL, sell);
		initialValues.put(KEY_EBUY, ebuy);
		initialValues.put(KEY_ESELL, esell);
		initialValues.put(KEY_WAY, way);
		initialValues.put(KEY_DATE, date);
		initialValues.put(KEY_INVEST, invest);
		initialValues.put(KEY_FLW, follow);
		initialValues.put(KEY_FRS, first_invest);
		initialValues.put(KEY_LST, last_invest);
		initialValues.put(KEY_LVAL, lastValue);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// Deletes a particular contact
	public boolean deleteContact(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// Retrieves all the contacts
	public Cursor getAllCurrencies() throws SQLException{
		return db.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_NAME,
				KEY_CODE, KEY_BUY, KEY_SELL, KEY_EBUY, KEY_ESELL, KEY_WAY,
				KEY_DATE, KEY_INVEST, KEY_FLW}, null, null, null, null, null);
	}

	// Retrieves a particular contact
	public Cursor getCurrency(String code) throws SQLException {

		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_CODE, KEY_BUY, KEY_SELL, KEY_EBUY,
				KEY_ESELL, KEY_WAY, KEY_DATE, KEY_INVEST, KEY_FLW , KEY_FRS, KEY_LST, KEY_LVAL}, KEY_CODE
				+ "='" + code + "'", null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// Retrieves a particular contact
	public Cursor getInteresteds(String follow) throws SQLException {

		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_CODE, KEY_BUY, KEY_SELL, KEY_EBUY,
				KEY_ESELL, KEY_WAY, KEY_DATE, KEY_INVEST, KEY_FLW , KEY_FRS, KEY_LST}, KEY_FLW
				+ "='" + follow + "'", null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// Retrieves a particular contact
	public Cursor getInvesteds() throws SQLException {

		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_NAME, KEY_CODE, KEY_BUY, KEY_SELL, KEY_EBUY,
				KEY_ESELL, KEY_WAY, KEY_DATE, KEY_INVEST, KEY_FLW , KEY_FRS, KEY_LST, KEY_LVAL}, KEY_INVEST
				+ "='Y'", null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	// Retrieves a particular contact
	
	public Cursor getCurrencyExt(int rowId) throws SQLException {

		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_INVEST, KEY_FLW }, KEY_ROWID + "='" + rowId
				+ "'", null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

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
	public boolean updateCurrency(long rowId, String name, String code,
			String buy, String sell, String ebuy, String esell, String way,
			String date, String invest, String follow) {
		// This methods returns the number of rows affected by the conducted
		// operation
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);
		args.put(KEY_CODE, code);
		args.put(KEY_BUY, buy);
		args.put(KEY_SELL, sell);
		args.put(KEY_EBUY, ebuy);
		args.put(KEY_ESELL, esell);
		args.put(KEY_WAY, way);
		args.put(KEY_DATE, date);
		args.put(KEY_INVEST, invest);
		args.put(KEY_FLW, follow);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	public boolean updateCurrencyStart(long rowId, String name, String code,
			String buy, String sell, String ebuy, String esell, String way,
			String date) {
		// This methods returns the number of rows affected by the conducted
		// operation
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);
		args.put(KEY_CODE, code);
		args.put(KEY_BUY, buy);
		args.put(KEY_SELL, sell);
		args.put(KEY_EBUY, ebuy);
		args.put(KEY_ESELL, esell);
		args.put(KEY_WAY, way);
		args.put(KEY_DATE, date);
		
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	public boolean updateCurrencyInvest(String code,String invest) {
		// This methods returns the number of rows affected by the conducted
		// operation
		
		ContentValues args = new ContentValues();
		args.put(KEY_INVEST, invest);
		return db.update(DATABASE_TABLE, args, KEY_CODE + "='" + code +"'", null) > 0;
	}
	public boolean updateCurrencyInterested(String code,String follow) {
		// This methods returns the number of rows affected by the conducted
		// operation
		
		ContentValues args = new ContentValues();
		args.put(KEY_FLW, follow);
		return db.update(DATABASE_TABLE, args, KEY_CODE + "='" + code +"'", null) > 0;
	}
	public boolean updateCurrencyFirstInvested(String code,String date) {
		// This methods returns the number of rows affected by the conducted
		// operation
		
		ContentValues args = new ContentValues();
		args.put(KEY_FRS, date);
		return db.update(DATABASE_TABLE, args, KEY_CODE + "='" + code +"'", null) > 0;
	}
	public boolean updateCurrencyLastInvested(String code,String date) {
		// This methods returns the number of rows affected by the conducted
		// operation
		
		ContentValues args = new ContentValues();
		args.put(KEY_LST, date);
		return db.update(DATABASE_TABLE, args, KEY_CODE + "='" + code +"'", null) > 0;
	}
	public boolean updateCurrencyLastValue(String code,String lastValue) {
        // This methods returns the number of rows affected by the conducted
        // operation
        
        ContentValues args = new ContentValues();
        args.put(KEY_LVAL, lastValue);
        return db.update(DATABASE_TABLE, args, KEY_CODE + "='" + code +"'", null) > 0;
    }
}
