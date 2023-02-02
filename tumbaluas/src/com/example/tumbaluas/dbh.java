package com.example.tumbaluas;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class dbh {
    private static final String ROW_KODE = "kode_buku";
    private static final String ROW_JDL = "judul_buku";
    private static final String ROW_PENG = "pengarang";
    private static final String NAMA_DB = "perpustakaan";
    private static final String NAMA_TABEL = "buku";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "create table " + NAMA_TABEL
            + " (" + ROW_KODE + " text PRIMARY KEY ," + ROW_JDL
            + " text," + ROW_PENG + " text)";

    private final Context context;
    private static DatabaseOpenHelper dbHelper;
    private static SQLiteDatabase db;
    public dbh(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseOpenHelper(ctx);
        db = dbHelper.getWritableDatabase();
    }
    public static class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context) {
            super(context, NAMA_DB, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS " + NAMA_DB);
            onCreate(db);
        }

        public static synchronized DatabaseOpenHelper getInstance(Context context) {
            if (dbHelper == null) {
            	dbHelper = new DatabaseOpenHelper(context);
            }
            return dbHelper;
        }


        public void open() {
            db = getWritableDatabase();
        }

        public void close() {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }
    public void close() {
        dbHelper.close();
    }

    public void addRow(String kode, String judul, String pengarang) {

        ContentValues values = new ContentValues();
        values.put(ROW_KODE, kode);
        values.put(ROW_JDL, judul);
        values.put(ROW_PENG, pengarang);

        try {
            db.insert(NAMA_TABEL, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilSemuaBaris() {
        ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
        Cursor cur;
        try {
            cur = db.query(NAMA_TABEL, new String[] { ROW_KODE, ROW_JDL,
                    ROW_PENG }, null, null, null, null, null);
            cur.moveToFirst();
            if (!cur.isAfterLast()) {
                do {
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(cur.getString(0));
                    dataList.add(cur.getString(1));
                    dataList.add(cur.getString(2));
                    dataArray.add(dataList);
                } while (cur.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DEBE ERROR", e.toString());
            Toast.makeText(context, "gagal ambil semua baris:" + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }
        return dataArray;
    }
    public SQLiteDatabase open() throws SQLException {
        return this.getWritableDatabase();
    }
    public void deleteBaris(String kode) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	if (db != null) {
    	    try {
    	        db.delete(NAMA_TABEL, ROW_KODE + " = ?", new String[]{kode});
    	    } catch (Exception e) {
    	        Log.e("Error", e.toString());
    	    }
    	} else {
    	    Log.e("Error", "Database is null");
    	}
    }
	public SQLiteDatabase getWritableDatabase() {
		return dbHelper.getWritableDatabase();
	}
	
    
}