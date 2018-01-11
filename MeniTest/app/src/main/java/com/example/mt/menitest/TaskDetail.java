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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

 public class TaskDetail extends AppCompatActivity implements LoadJsonObject.Listener {

    private Task task;
     private String Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       task = (Task)getIntent().getSerializableExtra("task");
        Status = getIntent().getExtras().getString("status");


        TextView text = (TextView)findViewById(R.id.TaskPromjenaStatusa);
        text.setText("Promjeni status prevoza " + "\nSerijski Broj: " + task.getSerijskiBroj() + "\nRelacija: " + task.getUtovar() + "\n" + task.getIstovar() + "\n u " + Status + " ?");
    }


    public void bntZavrsi(View view)
    {

        SharedPreferences preferences =  this.getSharedPreferences("GMTEL", Context.MODE_PRIVATE);
        String token = preferences.getString("Token", "");

        CheckBox cb = (CheckBox)findViewById(R.id.sendEmail);
        int mail = cb.isChecked() ? 1 : 0;

        new LoadJsonObject(this).execute(getResources().getString(R.string.ProdukcijaSajt) + "DnevnikPrevoza/GetUpdateStatus?id="+ task.getIdTask() + "&status="+ Status +"&token="+token+"&mail="+mail);
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
             Toast.makeText(this, "Status uspješno ažuriran", Toast.LENGTH_SHORT).show();
             Intent i = new Intent(this, TaskPrevozi.class);
             this.startActivity(i);
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
