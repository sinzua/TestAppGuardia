package com.rilevamento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by my on 22/04/17.
 */

public class AddManual extends Activity{
    private TextView eanTextPop;
    private Button buttonBack;
    private Button buttonAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String ean= b.getString("key", "");
        setContentView(R.layout.popup);
        setFinishOnTouchOutside(false);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (heigth*.6));



        eanTextPop = (TextView) findViewById(R.id.eanTextPop);
        eanTextPop.setText(ean); //message codice barre

        buttonBack = (Button) findViewById(R.id.buttonBackPop);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BarcodeScanner.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        buttonAdd = (Button) findViewById(R.id.buttonAddPop);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Da Configurare", Toast.LENGTH_LONG).show();
            }
        });

    }
}
