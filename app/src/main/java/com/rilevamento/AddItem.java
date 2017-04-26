package com.rilevamento;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by my on 22/04/17.
 */

public class AddItem extends Activity {

    private TextView addItemEan;
    private TextView addItemTitle;
    private Button addItemInsert;

    DBController controller = new DBController(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        final String ean = b.getString("key", "");
        final String title = b.getString("title", "");
        final String manufacturer = b.getString("manufacturer", "");
        final String productGroup = b.getString("productGroup", "");
        final String asin = b.getString("asin", "");

        setContentView(R.layout.additem);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        setFinishOnTouchOutside(false);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (heigth*.6));

        addItemEan = (TextView) findViewById(R.id.addItemEan);
        addItemEan.setText(ean); //message codice barre

        addItemTitle = (TextView) findViewById(R.id.addItemTitle);
        addItemTitle.setText(title);



        addItemInsert = (Button) findViewById(R.id.addItemInsert );
        addItemInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edt = (EditText)findViewById(R.id.addItemPrice);
                String price = edt.getText().toString();

                Log.i("AddItem", ean+" "+title+ " "+price +" " +manufacturer +" " +productGroup+" "+asin);

                if(TextUtils.isEmpty(price)) {
                    edt.setError("Inserire Prezzo!!!");
                }
                else {

                    loadData(ean, title, manufacturer, productGroup, asin, price);
                    //test
                    Toast.makeText(getApplicationContext(), "Prodotto Inserito - prezzo "+price+" €", Toast.LENGTH_SHORT).show();
                    //onBackPressed(); non serve pulisco le activity
                }
                }

        });
    }



    public void loadData(String ean, String title, String manufacturer, String productgroup, String asn, String price ) {
        //DBController controller;// = new DBController(this);
        String priceAmazon = "10";
        String competitor = "UNIEURO ORISTANO";

        //DateFormat date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        //DateFormat date  = SimpleDateFormat.getDateTimeInstance();

        Log.d("Data", getDateTime());
        try {
            if (ean.equals("") || title.equals("")|| manufacturer.equals("")|| productgroup .equals("")|| asn.equals("")) {
                Toast.makeText(getApplicationContext(), "errore dati", Toast.LENGTH_LONG).show();
            } else {
                controller = new DBController(getApplicationContext());
                SQLiteDatabase db = controller.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put("competitor", competitor);
                cv.put("data", getDateTime());
                cv.put("ean", ean);
                cv.put("marca", manufacturer);
                cv.put("modello", title);
                cv.put("productgroup", productgroup );
                cv.put("prezzo", price);
                cv.put("asin", asn);
                cv.put("prezzoAmazon", priceAmazon);
                db.insert("rilevamento", null, cv);
                db.close();
                Toast.makeText(getApplicationContext(),"Caricato",Toast.LENGTH_SHORT).show();
                backBarCode();
            }
        } catch (Exception ex) {
            Log.e("Exception Insert dB", ex.getMessage().toString());
            backBarCode();
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void backBarCode(){
        Intent intent = new Intent(getApplicationContext(), BarcodeScanner.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
