package com.rilevamento;

/**
 * Created by my on 22/04/17.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBController extends SQLiteOpenHelper {
    private static final String tablename = "rilevamento";  // nome tabella
    private static final String databasename = "rilinfo"; // Dtabasename

    private static final String id = "ID";  // auto generated ID colum
    private static final String competitor = "competitor";  // column name
    private static final String data = "data"; // data rilevamento
    private static final String ean = "ean"; // column name
    private static final String marca = "marca"; // marca
    private static final String modello = "modello"; // modello
    private static final String productgroup  = "productgroup "; // gruppo prodotto
    private static final String prezzo = "prezzo"; // prezzo rilevato
    private static final String asin = "asin";  // asin
    private static final String prezzoamazon = "prezzoamazon"; // prezzo Amazon

    private static final int versioncode = 1; //versioncode of the database

    public DBController(Context context) {
        super(context, databasename, null, versioncode);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +  competitor + " TEXT, " + data + " TEXT, " + ean + " TEXT, " +
                marca + " TEXT, " + modello + " TEXT, " +  productgroup  + " TEXT, " + prezzo + " TEXT, "
                + asin + " TEXT, " + prezzoamazon+ " TEXT "+ ")";
        /*
        query =
                "create table rilevamento " +
                        "(id integer primary key autoincrement, competitor text, data text, ean text, " +
                        "marca text, modello text, productgroup text,  prezzo text,  " +
                        "asin text, prezzoamazon text)";
        */
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old,
                          int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS " + tablename;
        database.execSQL(query);
        onCreate(database);
    }

    public ArrayList<HashMap<String, String>> getAllInfo() {
        ArrayList<HashMap<String, String>> allList;
        allList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM " + tablename;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                //map.put("id", cursor.getString(0)); ID auto incrementa
                //map.put("competitor", cursor.getString(0));
                //map.put("data", cursor.getString(1));
                map.put("ean", cursor.getString(3));
                map.put("marca", cursor.getString(4));
                map.put("modello", cursor.getString(5));
                map.put("group", cursor.getString(6));
                map.put("prezzo", cursor.getString(7));
               // map.put("asin", cursor.getString(7));
                //map.put("prezzoAmazon", cursor.getString(8));

                allList.add(map);
            } while (cursor.moveToNext());
        }

        // return tutte le info
        return allList;
    }
}
