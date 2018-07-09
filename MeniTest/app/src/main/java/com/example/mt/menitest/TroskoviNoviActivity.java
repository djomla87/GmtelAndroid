package com.example.mt.menitest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.mt.menitest.R.id.Status;

public class TroskoviNoviActivity extends AppCompatActivity implements LoadJSONTask.Listener, LoadJsonObject.Listener {

    Troskovi objTrosak;

    public Spinner vrsteTroskova;
    public Spinner vrsteValuta;

    public ArrayAdapter<CharSequence> adapter;
    public ArrayAdapter<CharSequence> adapter1;

    ArrayList<String> spinnerArray = new ArrayList<String>();
    ArrayList<String> spinnerArray1 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troskovi_novi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            objTrosak = (Troskovi) getIntent().getSerializableExtra("ObjTrosak");
        }
        catch (Exception ex){}

        vrsteTroskova = (Spinner) findViewById(R.id.VrstaTroskaSppiner);
        vrsteValuta = (Spinner) findViewById(R.id.ValutaSpinner);

        spinnerArray1.add("");
        spinnerArray1.add("BAM");
        spinnerArray1.add("EUR");
        spinnerArray1.add("USD");
        spinnerArray1.add("RSD");

        ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray1); //selected item will look like a spinner set from XML
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        vrsteValuta.setAdapter(spinnerArrayAdapter1);


        if (objTrosak != null)
        {
            RadioButton rb1 = (RadioButton)findViewById(R.id.radioRashod);
            RadioButton rb2 = (RadioButton)findViewById(R.id.radioZaduzenje);
            EditText iznosText = (EditText)findViewById(R.id.IznosDecimal);

            String [] niz = objTrosak.getIznos().split(" ");

            Spinner sp = (Spinner)findViewById(R.id.ValutaSpinner);
            Spinner sp1 = (Spinner)findViewById(R.id.VrstaTroskaSppiner);

            if(objTrosak.getTip().equals("RASHOD"))
            {
                rb1.setChecked(true);
                rb2.setChecked(false);
            }
            else
            {
                rb2.setChecked(true);
                rb1.setChecked(false);
            }

            iznosText.setText(niz[0].replace(",","."));
            sp.setSelection(spinnerArray1.indexOf(niz[1]));



        }

        new LoadJSONTask(this).execute(getResources().getString(R.string.ProdukcijaSajt) + "DnevnikPrevoza/ListaVrsteTroskova");


    }

    @Override
    public void onLoaded(JSONArray arr) {

        spinnerArray.add("");
        for (int i =0; i<arr.length(); i++)
        {
            try {
                int     Id       = arr.getJSONObject(i).getInt("IdVrstaTroska");
                String Naziv     = arr.getJSONObject(i).getString("Naziv");

                spinnerArray.add(Naziv);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        vrsteTroskova.setAdapter(spinnerArrayAdapter);

        if (objTrosak!=null)
            vrsteTroskova.setSelection(spinnerArray.indexOf(objTrosak.getVrsta()));

    }

    @Override
    public void onLoaded(JSONObject obj) {
        String response = null;
        String message = null;

        try {
            response = obj.getString("response");
            message = obj.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (response.equals("OK")) {
            Toast.makeText(this, "Podaci uspješno sačuvani", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, TroskoviActivity.class);
            this.startActivity(i);
        }
        else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError() {

    }

    public void OnButtonClick(View view)
    {
        SharedPreferences preferences =  this.getSharedPreferences("GMTEL", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", "");
        RadioButton rb1 = (RadioButton)findViewById(R.id.radioRashod);
        RadioButton rb2 = (RadioButton)findViewById(R.id.radioZaduzenje);
        EditText iznosText = (EditText)findViewById(R.id.IznosDecimal);
        Spinner sp = (Spinner)findViewById(R.id.ValutaSpinner);
        Spinner sp1 = (Spinner)findViewById(R.id.VrstaTroskaSppiner);

        String tip = rb1.isChecked() ? "RASHOD" : "ZADUŽENJE";
        String iznos = iznosText.getText().toString();
        String valuta = sp.getSelectedItem().toString();
        String vrstatroska = sp1.getSelectedItem().toString();

        int IdTrosak = objTrosak == null ? 0 : objTrosak.getId();

        new LoadJsonObject(this).execute(getResources().getString(R.string.ProdukcijaSajt) + "DnevnikPrevoza/NapraviVozacevTrosak?token="+ token + "&IdTrosak=" + IdTrosak + "&iznos="+ iznos +"&valuta="+
                valuta+"&tip="+tip+"&vrstatroska="+vrstatroska+"&napomena=");

    }

}
