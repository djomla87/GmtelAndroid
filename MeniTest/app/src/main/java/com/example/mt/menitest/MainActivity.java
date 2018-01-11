package com.example.mt.menitest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoadJSONTask.Listener {



    private List<Task> mTaskMapList = new ArrayList<>();
    private NotificationCompat.Builder notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        SharedPreferences preferences = getSharedPreferences("GMTEL", Context.MODE_PRIVATE);
        if ( preferences.getString("Korisnik","").equals(""))
        {
            Intent intent = new Intent(this, Logovanje.class);
            startActivity(intent);
        }
        else
        {
            NavigationView n = (NavigationView) findViewById(R.id.nav_view);
            n.getMenu().findItem(R.id.nav_user).setTitle(preferences.getString("Korisnik",""));
        }


        try {
            Task task = (Task) getIntent().getSerializableExtra("task");

            if (task != null)
            {
                Intent intent = new Intent(this, TaskPrevozi.class);
                startActivity(intent);
            }
        }
        catch ( Exception e ){}

        String token = preferences.getString("Token", "");


      //  new LoadJSONTask(this).execute(getResources().getString(R.string.ProdukcijaSajt) + "DnevnikPrevoza/NoviTaskovi?token="+ token);
        int radi = BackgroundService.getStarted();

        if (radi != 1)
        startService(new Intent(this, BackgroundService.class));


        // new DbUserData().execute("http://gmtel-office.com/api/Login?username=mario.kuzmanovic&password=admin");

        /*
        SharedPreferences preferences = getSharedPreferences("com.blabla.yourapp", Context.MODE_PRIVATE);
        preferences.edit().putString("session", <yoursessiontoken>).commit();
        preferences.getString("session", "");
        */

    }


    @Override
    public void onLoaded(JSONArray arr){

        mTaskMapList.clear();

        for (int i =0; i<arr.length(); i++)
        {
            try {
                int     IdTask          = arr.getJSONObject(i).getInt("IdTask");
                String SerijskiBroj     = arr.getJSONObject(i).getString("SerijskiBroj");
                String Vozilo           = arr.getJSONObject(i).getString("Vozilo");
                String Istovar          = arr.getJSONObject(i).getString("Istovar");
                String Roba             = arr.getJSONObject(i).getString("Roba");
                String Status           = arr.getJSONObject(i).getString("Status");
                String DatumAzuriranja  = arr.getJSONObject(i).getString("DatumAzuriranja");
                String Utovar           = arr.getJSONObject(i).getString("Utovar");
                String UvoznaSpedicija = arr.getJSONObject(i).getString("UvoznaSpedicija");
                String IzvoznaSpedicija = arr.getJSONObject(i).getString("IzvoznaSpedicija");
                String Uvoznik = arr.getJSONObject(i).getString("Uvoznik");
                String Izvoznik = arr.getJSONObject(i).getString("Izvoznik");
                String Napomena = arr.getJSONObject(i).getString("Napomena");
                String RefBroj = arr.getJSONObject(i).getString("RefBroj");
                String Pregledano = arr.getJSONObject(i).getString("Pregledano");

                mTaskMapList.add(new Task(IdTask, SerijskiBroj, Vozilo, Istovar, Roba, Status, DatumAzuriranja, Utovar, UvoznaSpedicija, IzvoznaSpedicija, Uvoznik, Izvoznik, Napomena, RefBroj, Pregledano));



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        for (int i=0; i< mTaskMapList.size(); i++ )
        {

            notification.setSmallIcon(R.drawable.ic_launcher);
            notification.setTicker("This is a ticker");
            notification.setWhen(System.currentTimeMillis());
            notification.setContentTitle("Prevoz " + mTaskMapList.get(i).getSerijskiBroj());
            notification.setContentText("Utovar " + mTaskMapList.get(i).getUtovar());

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntet = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setContentIntent(pendingIntet);

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(mTaskMapList.get(i).getIdTask(), notification.build());

        }


    }

    @Override
    public void onError(){}


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_task) {
            Intent intent = new Intent(this, TaskPrevozi.class);
            startActivity(intent);

        } else if (id == R.id.nav_user) {
        } else if (id == R.id.nav_logout) {

            SharedPreferences preferences = getSharedPreferences("GMTEL", Context.MODE_PRIVATE);

            preferences.edit().remove("Korisnik").commit();
            preferences.edit().remove("Token").commit();

            Intent intent = new Intent(this, MainActivity.class);
            stopService(new Intent(this, BackgroundService.class));
            startActivity(intent);

        } else if (id == R.id.nav_exit) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    class DbUserData extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            String url = params[0];
            UrlJson a = new UrlJson();
            a.SetUrl(url);   //"http://gmtel-office.com/api/Login?username=mario.kuzmanovic&password=admin"
            JSONObject obj = a.GetJson();

            return obj;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            NavigationView n = (NavigationView) findViewById(R.id.nav_view);
            try {
                n.getMenu().findItem(R.id.nav_user).setTitle(result.getString("Korisnik"));

                SharedPreferences preferences = getSharedPreferences("GMTEL", Context.MODE_PRIVATE);
                preferences.edit().putString("Korisnik",result.getString("Korisnik")).commit();
                preferences.edit().putString("Token",result.getString("Code")).commit();

            } catch (JSONException e) {
                n.getMenu().findItem(R.id.nav_user).setTitle("");
            }

        }
    }

}



