package com.example.mt.menitest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TroskoviNoviActivity extends AppCompatActivity implements LoadJSONTask.Listener {


    public Spinner vrsteTroskova;
    public ArrayAdapter<CharSequence> adapter;
    ArrayList<String> spinnerArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troskovi_novi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vrsteTroskova = (Spinner) findViewById(R.id.VrstaTroskaSppiner);

        new LoadJSONTask(this).execute(getResources().getString(R.string.ProdukcijaSajt) + "DnevnikPrevoza/ListaVrsteTroskova");



    }

    @Override
    public void onLoaded(JSONArray arr) {

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

    }

    @Override
    public void onError() {

    }
}
