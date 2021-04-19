package com.example.mt.menitest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TaskDetalji extends AppCompatActivity implements LoadJsonObject.Listener {

    private Task task;

    Button btpic, btnup;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    String mCurrentPhotoPath;
    ImageView image;
    public String URL;
ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detalji);

URL = getResources().getString(R.string.ProdukcijaSajt) + "DnevnikPrevoza/UploadAndroid";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bar = (ProgressBar) findViewById(R.id.progressBartask);
        bar.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.Uslikano);

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
        TextView Files = (TextView)findViewById(R.id.BrojSlika);
        TextView Prevoznik = (TextView)findViewById(R.id.Prevoznik);
        TextView PrevoznikLabel = (TextView)findViewById(R.id.PrevoznikLabel);

        if (task.getPregledano().equals(""))
        {
            Prevoznik.setVisibility(View.GONE);
            PrevoznikLabel.setVisibility(View.GONE);
        }
        else
        {
            Prevoznik.setText(task.getPregledano());
        }

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
        Files.setText(task.getFiles());
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
        detalji.putExtra("podStatus",0);
        this.startActivity(detalji);
    }

    public void btnUTransportu(View view)
    {
        Intent detalji = new Intent( this, TaskDetail.class);
        detalji.putExtra("task", task);
        detalji.putExtra("status","U_transportu");
        detalji.putExtra("podStatus",0);
        this.startActivity(detalji);
    }

    public void btnDostavljeno(View view)
    {
        Intent detalji = new Intent( this, TaskDetail.class);
        detalji.putExtra("task", task);
        detalji.putExtra("status","Dostavljeno");
        detalji.putExtra("podStatus",0);
        this.startActivity(detalji);
    }

    public void btnDostavljenoBrzaPosta(View view)
    {
        Intent detalji = new Intent( this, TaskDetail.class);
        detalji.putExtra("task", task);
        detalji.putExtra("status","Dostavljeno");
        detalji.putExtra("podStatus",7);
        this.startActivity(detalji);
    }

    public void btnDostavljenoSkladiste(View view)
    {
        Intent detalji = new Intent( this, TaskDetail.class);
        detalji.putExtra("task", task);
        detalji.putExtra("status","Dostavljeno");
        detalji.putExtra("podStatus",1);
        this.startActivity(detalji);
    }

public void  onUtovarAdressClick(View view)
{
    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
            Uri.parse("google.navigation:q="+task.getUtovar()));

    startActivity(intent);
}

    public void  onIstovarAdressClick(View view)
    {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+task.getIstovar()));

        startActivity(intent);
    }

    public void btnUcitajIzGalerije(View view)
    {
        /*
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
        }

        */
/*
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
*/
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
               // fileUri = Uri.fromFile(photoFile);
                fileUri = FileProvider.getUriForFile(this, "com.example.mt.menitest.provider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(takePictureIntent, 100);
            }
        }
    }

    private File createImageFile() throws IOException {
       // File storageDir = Environment.getExternalStorageDirectory();
       // File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
       // File storageDir =  getFilesDir();
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";

        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public void btnUcitajIzGalerije2(View view)
    {
        // Check Camera
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
            Bitmap bitmap, resized;

          //  Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentUri);

                resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.4), (int)(bitmap.getHeight()*0.4), true);


                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 33, bao);
                byte[] ba = bao.toByteArray();

                ba1 =  android.util.Base64.encodeToString(ba,android.util.Base64.DEFAULT);

                new uploadToServer().execute();

            } catch (IOException e) {
                e.printStackTrace();
            }


/*
            BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
*/


            // Cursor to get image uri to display
/*
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
*/

          //  ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
          //  imageView.setImageBitmap(photo);



            // Get the bitmap from drawable object
/*
            File f = new File(mCurrentPhotoPath);


            try {
                OutputStream out = new FileOutputStream(f);
                Bitmap finalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);

                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
*/


/*
            String filePath = f.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 33, bao);

            byte[] ba = bao.toByteArray();

            ba1 =  android.util.Base64.encodeToString(ba,android.util.Base64.DEFAULT);

            // Log.e("base64", "-----" + ba1);

            // Upload image to server
            new uploadToServer().execute();
            */

        }

        if (requestCode == 200 && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                Bitmap resized = Bitmap.createScaledBitmap(selectedImage,(int)(selectedImage.getWidth()*0.4), (int)(selectedImage.getHeight()*0.4), true);


                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                resized.compress(Bitmap.CompressFormat.JPEG, 33, bao);
                byte[] ba = bao.toByteArray();

                ba1 =  android.util.Base64.encodeToString(ba,android.util.Base64.DEFAULT);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(TaskDetalji.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

            new uploadToServer().execute();

        }

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




    public class uploadToServer extends AsyncTask<Void, Void, String> {

        //private ProgressDialog pd = new ProgressDialog(TaskDetalji.this);
        protected void onPreExecute() {
            super.onPreExecute();
          //  pd.setMessage("Wait image uploading!");
          //  pd.show();
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
            nameValuePairs.add(new BasicNameValuePair("sb", task.getSerijskiBroj() ));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());


                Toast.makeText(TaskDetalji.this, st, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
               e.printStackTrace();
            }


            return "Success";

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            bar.setVisibility(View.INVISIBLE);

            int a =  Integer.parseInt(task.getFiles() ) ;
            a++;

            task.setFiles(a+"");

            Intent detalji = new Intent( getApplicationContext() , TaskDetalji.class);
            detalji.putExtra("task", task);
            getApplicationContext().startActivity(detalji);

            //pd.hide();
            //pd.dismiss();
        }
    }

}

