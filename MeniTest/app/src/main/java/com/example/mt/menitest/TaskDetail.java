 package com.example.mt.menitest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

 public class TaskDetail extends AppCompatActivity {

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
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


       task = (Task)getIntent().getSerializableExtra("task");
       String status = getIntent().getExtras().getString("status");


        TextView text = (TextView)findViewById(R.id.TaskPromjenaStatusa);
        text.setText("Promjeni status prevoza " + "\nSerijski Broj: " + task.getSerijskiBroj() + "\nRelacija: " + task.getUtovar() + "\n" + task.getIstovar() + "\n u " + status + " ?");
    }


    public void bntZavrsi(View view)
    {
        Toast.makeText(this, "" + task.getIdTask(), Toast.LENGTH_SHORT).show();


    }

}
