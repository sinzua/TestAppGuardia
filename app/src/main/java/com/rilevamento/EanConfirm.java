package com.rilevamento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by my on 26/04/17.
 */

public class EanConfirm extends Activity {


    private Button additemyes;
    private Button additemno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        final String ean = b.getString("key", "");
        final String title = b.getString("title", "");
        final String manufacturer = b.getString("manufacturer", "");
        final String productGroup = b.getString("productGroup", "");
        final String asin = b.getString("asin", "");

        setContentView(R.layout.additempopup);


        setFinishOnTouchOutside(false);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (heigth * .6));



        additemyes = (Button) findViewById(R.id.additemyes );
        additemyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DA COMPLETAREEEEEE!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        additemno = (Button) findViewById(R.id.additemno );
        additemno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBarCode();
            }
        });
    }

    public void backBarCode(){
        Intent intent = new Intent(getApplicationContext(), BarcodeScanner.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}