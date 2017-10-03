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
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Logovanje extends AppCompatActivity implements LoadJsonObject.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logovanje);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }

    public void onLoginButtonClick(View view)
    {
        EditText Username = (EditText) findViewById(R.id.Username);
        EditText Lozinka = (EditText) findViewById(R.id.Password);

        new LoadJsonObject(this).execute("http://gmtel-office.com/api/Login?username="+Username.getText()+"&password="+Lozinka.getText());
    }

    @Override
    public void onLoaded(JSONObject array) {

        try {
            JSONObject result = array;

            if (result.getString("Korisnik").equals("") || result.getString("Korisnik") == null || result.getString("Korisnik").equals("null") )
                Toast.makeText(this, "Pogrešno korisničko ime ili lozinka", Toast.LENGTH_LONG).show();
            else {
                SharedPreferences preferences = getSharedPreferences("GMTEL", Context.MODE_PRIVATE);
                preferences.edit().putString("Korisnik", result.getString("Korisnik")).commit();
                preferences.edit().putString("Token", result.getString("Code")).commit();

                EditText Username = (EditText) findViewById(R.id.Username);
                EditText Lozinka = (EditText) findViewById(R.id.Password);

                Username.setText("");
                Lozinka.setText("");

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }


        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onError() {
        Toast.makeText(this, "Greska u pozivu WS", Toast.LENGTH_LONG).show();
    }
}
