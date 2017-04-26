package com.rilevamento;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by my on 24/04/17.
 */

public class ManualInsert extends Activity {

    private Spinner categories;
    private Button addItembtn;

    DBController controller = new DBController(this);

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
//        String ean = b.getString("key", "");
        setContentView(R.layout.manual_insert);
        addListenerCategoriesSelection();



        addItembtn = (Button) findViewById(R.id.addItembtn );
        addItembtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ean, model, manufacturer, productGroup,asn, price;
                asn = "noAsinLoadManual";

                EditText edtEan = (EditText)findViewById(R.id.manualEan);
                ean = edtEan.getText().toString();

                EditText edtBrand = (EditText)findViewById(R.id.manualBrand);
                manufacturer = edtBrand.getText().toString();

                EditText edtModel = (EditText)findViewById(R.id.manualModel);
                model = edtModel.getText().toString();

                EditText edtPrice = (EditText)findViewById(R.id.manualPrice);
                price = edtPrice.getText().toString();

                categories = (Spinner) findViewById(R.id.spinnercategories);

                productGroup = String.valueOf(categories.getSelectedItem());

                Log.i("AddItem", ean+" "+model+ " "+price +" " +manufacturer +" " +productGroup+" "+asn);

                if(TextUtils.isEmpty(ean)) {
                    edtEan.setError("Inserire EAN!!!");
                    Toast.makeText(getApplicationContext(), "Inserire EAN!!!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(model)){
                    edtModel.setError("Inserire Modello!!!");
                    Toast.makeText(getApplicationContext(), "Inserire Modello!!!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(price)){
                    edtPrice.setError("Inserire Prezzo!!!");
                    Toast.makeText(getApplicationContext(), "Inserire Prezzo!!!", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(manufacturer)){
                    edtBrand.setError("Inserire Marca!!!");
                    Toast.makeText(getApplicationContext(), "Inserire Marca!!!", Toast.LENGTH_SHORT).show();
                }else if(checkCategorie(productGroup)){
                    Toast.makeText(getApplicationContext(), "Inserire Categorie!!!", Toast.LENGTH_SHORT).show();
                }

                else{
                    loadData(ean, model, manufacturer, productGroup, asn, price);
                    //test
                    Toast.makeText(getApplicationContext(), "Prodotto Inserito - prezzo "+price+" €", Toast.LENGTH_SHORT).show();
                    //onBackPressed(); non serve pulisco le activity
                }
            }

        });
    }


    private boolean checkCategorie (String str){
        if(str.equals("Scegliere una Categoria"))
            return true;
        else
            return false;
    }

    private void addListenerCategoriesSelection() {
        categories = (Spinner) findViewById(R.id.spinnercategories);
        categories.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }


    public void loadData(String ean, String title, String manufacturer, String productgroup, String asn, String price) {
        //DBController controller;// = new DBController(this);
        String priceAmazon = "10";
        String competitor = "UNIEURO ORISTANO";
        //DateFormat date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        //DateFormat date  = SimpleDateFormat.getDateTimeInstance();
        Log.d("Data", getDateTime());
        try {
            if (ean.equals("") || title.equals("") || manufacturer.equals("") || productgroup.equals("") || asn.equals("")) {
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
                cv.put("productgroup", productgroup);
                cv.put("prezzo", price);
                cv.put("asin", asn);
                cv.put("prezzoAmazon", priceAmazon);
                db.insert("rilevamento", null, cv);
                db.close();
                Toast.makeText(getApplicationContext(), "Caricato", Toast.LENGTH_SHORT).show();
                backHome();
            }
        } catch (Exception ex) {
            Log.e("Exception Insert dB", ex.getMessage().toString());
            backHome();
        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void backHome() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}