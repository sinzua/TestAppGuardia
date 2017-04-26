package com.rilevamento;

/**
 * Created by my on 22/04/17.
 */

import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;

        import java.util.HashMap;
        import java.util.List;


public class DataLoaded extends ActionBarActivity {
    DBController controller = new DBController(this);
    ListView ls;
    TextView infotext;
    TextView txtean;
    TextView txtmodel;
    TextView txtbrand;
    TextView txtgroup;
    TextView txtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataloaded);

        ls = (ListView) findViewById(R.id.dataloaded);
        infotext = (TextView) findViewById(R.id.txtresulttext);
        txtean = (TextView) findViewById(R.id.txtean);
        txtmodel = (TextView) findViewById(R.id.txtmodel);
        txtbrand = (TextView) findViewById(R.id.txtbrand);
        txtgroup = (TextView) findViewById(R.id.txtgroup);
        txtPrice = (TextView) findViewById(R.id.txtPrice);

        try {
            List<HashMap<String, String>> data = controller.getAllInfo();
            if (data.size() != 0) {
                // Srno, RMCode, Fileno, Loc, FileDesc, TAGNos
                SimpleAdapter adapter = new SimpleAdapter(
                        DataLoaded.this, data, R.layout.row,
                        new String[]{"ean", "marca", "modello", "group", "prezzo"}, new int[]{
                        //new String[]{"ean", "modello", "marca", "productgroup", "prezzo"}, new int[]{
                        R.id.txtean, R.id.txtbrand ,R.id.txtmodel, R.id.txtgroup, R.id.txtPrice});

                ls.setAdapter(adapter);
                String length = String.valueOf(data.size());
                String c;
                if(data.size()==1){
                    c = "o";
                }else {
                    c= "i";
                }
                infotext.setText(length + " rilevament"+c);
            } else {
                infotext.setText("Nessun Rilevamento effettuato");
            }

        } catch (Exception ex) {
            infotext.setText(ex.getMessage().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}