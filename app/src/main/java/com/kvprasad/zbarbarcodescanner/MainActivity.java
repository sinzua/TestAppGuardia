package com.kvprasad.zbarbarcodescanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button scannerButton;
    private Button manualButton;
    private Button loadDataButton;
    private Button listLoadedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }


        scannerButton = (Button) findViewById(R.id.scannerButton);
        manualButton = (Button) findViewById(R.id.manualButton);
        loadDataButton = (Button) findViewById(R.id.loadDataButton);
        listLoadedButton = (Button) findViewById(R.id.listLoadedButton);

        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                startActivity(intent);
            }
        });

        manualButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            Context context = getApplicationContext();
            CharSequence text = "TEST - Carica Manualmente!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            public void onClick(View v) {/*
                Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                startActivity(intent);*/
                toast.show();
            }
        });

        listLoadedButton.setOnClickListener(new View.OnClickListener() {
           // @Override
            Context context = getApplicationContext();
            CharSequence text = "TEST - Lista Caricati!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            public void onClick(View v) {
               /* Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                startActivity(intent);*/
                toast.show();
            }
        });

        loadDataButton.setOnClickListener(new View.OnClickListener() {
           // @Override
            Context context = getApplicationContext();
            CharSequence text = "TEST - Carica Dati!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            public void onClick(View v) {
                /*
                Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                startActivity(intent);*/
                toast.show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
