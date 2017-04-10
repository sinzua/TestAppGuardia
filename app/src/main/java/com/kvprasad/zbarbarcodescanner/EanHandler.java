package com.kvprasad.zbarbarcodescanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JUMBO on 01/04/2017.
 */

public class EanHandler extends ActionBarActivity {

    private Button buttonBack;
    private TextView eanTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String message = b.getString("key", ""); // the blank String in the second parameter is the default value of this variable. In case the value from previous activity fails to be obtained, the app won't crash: instead, it'll go with the default value of an empty string

        //creo la mappa per i parametri
        Map<String,String> map;
        map = UrlParameterHandler.getInstance().buildMapForItemSearch(message);

        //connetto handler


        setContentView(R.layout.menu_readed_ean);

        eanTextView = (TextView) findViewById(R.id.eanTextView);
        eanTextView.setText(message);

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_readed_ean, container, false);
        return rootView;
    }

*/




    public void checkEan(String scanResult) {

       /* try {
            //da inserire metodo ricerca dB o ricerca aws

            //mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            Log.d("DBG", "Error checkEan: " + e.getMessage());
        }*/
    }


}


