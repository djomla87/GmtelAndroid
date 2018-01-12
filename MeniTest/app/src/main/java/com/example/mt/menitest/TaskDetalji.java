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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class TaskDetalji extends AppCompatActivity implements LoadJsonObject.Listener {

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detalji);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        task = (Task)getIntent().getSerializableExtra("task");

        TextView SerijskiBroj = (TextView)findViewById(R.id.SerijskiBroj);
        TextView Vozilo = (TextView)findViewById(R.id.Vozilo);
        TextView Istovar = (TextView)findViewById(R.id.Istovar);
        TextView Status = (TextView)findViewById(R.id.Status);
        TextView Roba = (TextView)findViewById(R.id.Roba);
        TextView DatumAzuriranja = (TextView)findViewById(R.id.DatumAzuriranja);
        TextView Utovar = (TextView)findViewById(R.id.Utovar);
        TextView UvoznaSpedicija = (TextView)findViewById(R.id.UvoznaSpedicija);
        TextView IzvoznaSpedicija = (TextView)findViewById(R.id.IzvoznaSpedicija);
        TextView Uvoznik = (TextView)findViewById(R.id.Uvoznik);
        TextView Izvoznik = (TextView)findViewById(R.id.Izvoznik);
        TextView Napomena = (TextView)findViewById(R.id.Napomena);
        TextView RefBroj = (TextView)findViewById(R.id.RefBroj);


        SerijskiBroj.setText(task.getSerijskiBroj());
        Vozilo.setText(task.getVozilo());
        Istovar.setText(task.getIstovar());
        Status.setText(task.getStatus());
        Roba.setText(task.getRoba());
        DatumAzuriranja.setText(task.getDatumAzuriranja());
        Utovar.setText(task.getUtovar());
        UvoznaSpedicija.setText(task.getUvoznaSpedicija());
        IzvoznaSpedicija.setText(task.getIzvoznaSpedicija());
        Uvoznik.setText(task.getUvoznik());
        Izvoznik.setText(task.getIzvoznik());
        Napomena.setText(task.getNapomena());
        RefBroj.setText(task.getRefBroj());

        SharedPreferences preferences =  this.getSharedPreferences("GMTEL", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", "");
        String rola = preferences.getString("Rola", "");

        if (rola.equals("admin"))
        new LoadJsonObject(this).execute(getResources().getString(R.string.ProdukcijaSajt) + "DnevnikPrevoza/AndroidTaskSeen?token="+token+"&VozacVidio=0&AdminVidio=1");
        else
        new LoadJsonObject(this).execute(getResources().getString(R.string.ProdukcijaSajt) + "DnevnikPrevoza/AndroidTaskSeen?token="+token+"&VozacVidio=1&AdminVidio=0");

    }


    public void btnNijeUtovareno(View view)
    {
        Intent detalji = new Intent( this, TaskDetail.class);
        detalji.putExtra("task", task);
        detalji.putExtra("status","Nije_utovareno");
        this.startActivity(detalji);
    }

    public void btnUTransportu(View view)
    {
        Intent detalji = new Intent( this, TaskDetail.class);
        detalji.putExtra("task", task);
        detalji.putExtra("status","U_transportu");
        this.startActivity(detalji);
    }

    public void btnDostavljeno(View view)
    {
        Intent detalji = new Intent( this, TaskDetail.class);
        detalji.putExtra("task", task);
        detalji.putExtra("status","Dostavljeno");
        this.startActivity(detalji);
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
          //  Toast.makeText(this, "Status uspješno ažuriran", Toast.LENGTH_SHORT).show();
          //  Intent i = new Intent(this, TaskPrevozi.class);
          //  this.startActivity(i);
        }
        else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError() {
        Toast.makeText(this, "Greska", Toast.LENGTH_SHORT).show();
    }
}
