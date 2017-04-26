package com.rilevamento;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by my on 25/04/17.
 */

public class EanChecker extends ActionBarActivity {
    private static final String tablename = "rilevamento";  // nome tabella
    private static final String databasename = "rilinfo"; // Dtabasename

    private static final String eanField = "ean"; // column ean

    DBController controller = new DBController(this);


    public boolean EanVerifier(String ean){
        try {
            if (ean.equals("")) {
                Toast.makeText(getApplicationContext(), "Errore Passaggio EAN", Toast.LENGTH_SHORT).show();
            } else {
                controller = new DBController(getApplicationContext());
                SQLiteDatabase db = controller.getReadableDatabase();//.getWritableDatabase();
                String Query = "Select * from " + tablename + " where " + eanField + " = " + ean;
                Cursor cursor = db.rawQuery(Query, null);

                if(cursor.getCount() <= 0){
                    cursor.close();
                    return false;
                }
                cursor.close();
                db.close();
            }
        } catch (Exception ex) {
            Log.e("Errore CheckDB", ex.getMessage());
        }
        //ritorna vero se esiste gia
        return true;
    }

}


