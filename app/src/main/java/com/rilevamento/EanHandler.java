package com.rilevamento;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/** s
 * Created by JUMBO on 01/04/2017.
 segui * https://www.lucazanini.eu/it/2012/android/the-android-os-networkonmainthreadexception-exception/
 https://github.com/mobilemerit/Youtube-Playlist-Android-App-Example/blob/dc8f7140f296bcdcf8efb05029e159014358736e/PlaylistApp/src/org/aynsoft/playList/HomeActivity.java
 http://mobilemerit.com/amazon-product-advertising-api-tutorial/
 */

public class EanHandler extends AppCompatActivity {
    EanChecker eanChecker = new EanChecker();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String ean= b.getString("key", ""); // the blank String in the second parameter is the default value of this variable. In case the value from previous activity fails to be obtained, the app won't crash: instead, it'll go with the default value of an empty string
        Map<String, String> map;
        map = UrlParameterHandler.getInstance().buildMapForItemSearch(ean);
        //StartLoading
        startLoading(map, ean);
    }

    public void startLoading(Map map, String ean){
        SignedRequestsHelper sh;
        try {
            sh = new SignedRequestsHelper();
            String url = sh.sign(map);
            Log.d("url>>>", url);
            if(url!=null){
                new LoadAsync().execute(url, ean);
            }
        } catch (UnsupportedEncodingException e) {
            Log.d("UnsupEncodingExcep", String.valueOf(e));
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Log.d("NoSuchAlgExcep", String.valueOf(e));
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.d("InvalidKeyExcep", String.valueOf(e));
            e.printStackTrace();
        }
    }

    class LoadAsync extends AsyncTask<String,Map,Void> {




        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String ean = params[1];
            String asin, title,manufacturer,productGroup;
            SearchObject o;
            Log.d("String url", url);
            Parser parser = new Parser();
            NodeList nodeList;
            nodeList = parser.getResponceNodeList(url);
            if (nodeList != null) {
                    //Log.d("connect() -> Titolo", parser.getSearchObject(nodeList,0).getTitle());
                o = parser.getSearchObject(nodeList,0);
                if(o!=null) {
                    title = o.getTitle();
                    manufacturer = o.getManufacturer();
                    productGroup = o.getProductGroup();
                    asin = o.getId();
                    //Log.d("connect() -> Titolo", str);
                    //verifico che non sia gia stato inserito
                    if(!checkEan(ean)){ verifica questo cazzo
                       // Toast.makeText(getApplicationContext(), "Prodotto Gia inserito", Toast.LENGTH_SHORT).show();
                        popUpVerifier(asin, ean, title, manufacturer, productGroup);
                    }else {
                        //inserisco i dati
                        addItem(asin, ean, title, manufacturer, productGroup);
                    }
                }else{
                    //  Log.d("connect()->TitoloNullo", "PRODOTTO non TROVATO");
                    makeToast(ean); //prodotto non trovato
                    return null;
                }
            }else {
                Log.d("searchObject not valid", url);
                //setTitleObjetc(searchObject);
            }
            return null;
        }

        private boolean checkEan(String ean) {
            if(eanChecker.EanVerifier(ean))
                return true;
                else
                return false;
        }

        private void makeToast(final String ean){
            final Bundle str= new Bundle();
            str.putString("key", ean);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(new Intent(EanHandler.this,AddManual.class).putExtras(str));
                    startActivity(intent);
                    //startActivity(new Intent(EanHandler.this,AddManual.class)).putExtras(ean);
                }
            });
        }

        private void addItem(final String asin, final String ean, final String title, final String manufacturer, final String productGroup){
            final Bundle str= new Bundle();
            str.putString("key", ean);
            str.putString("title", title);
            str.putString("manufacturer", manufacturer);
            str.putString("productGroup", productGroup);
            str.putString("asin", asin);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(new Intent(EanHandler.this,AddItem.class).putExtras(str));
                    startActivity(intent);
                    //startActivity(new Intent(EanHandler.this,AddManual.class)).putExtras(ean);
                }
            });
        }


        private void popUpVerifier(final String asin, final String ean, final String title, final String manufacturer, final String productGroup){
            final Bundle str= new Bundle();
            str.putString("key", ean);
            str.putString("title", title);
            str.putString("manufacturer", manufacturer);
            str.putString("productGroup", productGroup);
            str.putString("asin", asin);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_LONG).show();
                Log.d("popUpVerifier ean ", ean);
                Intent intent = new Intent(new Intent(EanHandler.this,EanConfirm.class).putExtras(str));
                startActivity(intent);
                //startActivity(new Intent(EanHandler.this,AddManual.class)).putExtras(ean);
            }
        });
    }


    }
}



/*
questa è la versione funzionante con l'activity e il layout  R.layout.menu_readed_ean

public class EanHandler extends AppCompatActivity {

    private Button buttonBack;
    private TextView eanTextView;
    private TextView textURL;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String ean= b.getString("key", ""); // the blank String in the second parameter is the default value of this variable. In case the value from previous activity fails to be obtained, the app won't crash: instead, it'll go with the default value of an empty string
        Map<String, String> map;
        map = UrlParameterHandler.getInstance().buildMapForItemSearch(ean);
        //StartLoading

        setContentView(R.layout.menu_readed_ean);
        eanTextView = (TextView) findViewById(R.id.eanTextView);
        eanTextView.setText(ean); //message codice barre
        startLoading(map, ean);

        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void startLoading(Map map, String ean){
        SignedRequestsHelper sh;
        try {
            sh = new SignedRequestsHelper();
            String url = sh.sign(map);
            Log.d("url>>>", url);
            if(url!=null){
                textURL = (TextView) findViewById(R.id.textURL);
                textURL.setText(url); //message
                new LoadAsync().execute(url, ean);
            }
        } catch (UnsupportedEncodingException e) {
            Log.d("UnsupEncodingExcep", String.valueOf(e));
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Log.d("NoSuchAlgExcep", String.valueOf(e));
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            Log.d("InvalidKeyExcep", String.valueOf(e));
            e.printStackTrace();
        }
    }

    class LoadAsync extends AsyncTask<String,Map,Void> {

        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String ean = params[1];
            String str;
            SearchObject o;
            Log.d("String url", url);
            Parser parser = new Parser();
            NodeList nodeList;
            nodeList = parser.getResponceNodeList(url);
            if (nodeList != null) {
                    //Log.d("connect() -> Titolo", parser.getSearchObject(nodeList,0).getTitle());
                o = parser.getSearchObject(nodeList,0);
                if(o!=null) {
                    str = o.getTitle();
                    //Log.d("connect() -> Titolo", str);
                    addItem(ean,str);
                    textTitle = (TextView) findViewById(R.id.textTitle);
                    setTitleObjetc(textTitle,str);
                }else{
                  //  Log.d("connect()->TitoloNullo", "PRODOTTO non TROVATO");
                    makeToast(ean); //prodotto non trovato
                    return null;
                }
            }else {
                Log.d("searchObject not valid", url);
                //setTitleObjetc(searchObject);
            }
            return null;
        }

        private void setTitleObjetc(final TextView text,final String value){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textTitle.setText(value);
                }
            });
        }

        private void makeToast(final String ean){
            final Bundle str= new Bundle();
            str.putString("key", ean);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(new Intent(EanHandler.this,AddManual.class).putExtras(str));
                    startActivity(intent);
                    //startActivity(new Intent(EanHandler.this,AddManual.class)).putExtras(ean);
                }
            });
        }

        private void addItem(final String ean, final String title){
            final Bundle str= new Bundle();
            str.putString("key", ean);
            str.putString("title", title);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(new Intent(EanHandler.this,AddItem.class).putExtras(str));
                    startActivity(intent);
                    //startActivity(new Intent(EanHandler.this,AddManual.class)).putExtras(ean);
                }
            });
        }
    }

}
 */