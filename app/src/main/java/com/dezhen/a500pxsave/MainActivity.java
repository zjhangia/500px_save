package com.dezhen.a500pxsave;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dezhen.a500pxsave.settings.Settings_Main;
import com.dezhen.a500pxsave.viewallfile.ViewAllFile;


import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String ORIGIN_URL_PRE = "https://500px.com/";

    private Context context;
    private TextView textView_tilte;
    private ImageButton button_share;
    private ImageButton button_download;
    private ImageView imageView;
    //private FloatingActionButton fab;
    private String originalUrl;
    private String url;//the image address

    private String file_path;
    private String file_name;
    private Handler handler;
    private Intent intent;

    private EditText url_text;
    private ImageButton search;
    private String url_string;

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to get permission
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what != 0){
                    makeToast("You should get the permission!");
                }
            }
        };
        Thread thread = new Thread(GetPermission);
        thread.start();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getHandle
        getInstance();

        getDataFormShareIcon();

       /* search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url_string = url_text.getText().toString();

                boolean legal = toCheckTheUrlLegal(url_string);
                if(legal == true){
                    getUrl(url_string.replace(ORIGIN_URL_PRE, "").split("/")[1]);
                }else{
                    makeToast("Invalid url.");
                }

            }
        });
*/
    }

    Runnable GetPermission = new Runnable() {
        @Override
        public void run() {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        PERMISSIONS,1);
            } else if(ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                Toast.makeText(MainActivity.this, "You need the Storage Permission to Store the image", Toast.LENGTH_LONG).show();
            }
            Message message = new Message();
            message.what = 0;
            handler.sendMessage(message);
        }

    };
    public void getInstance(){
        context = MainActivity.this;
        textView_tilte = (TextView) findViewById(R.id.image_title);
        button_share = (ImageButton) findViewById(R.id.image_share);
        button_download = (ImageButton) findViewById(R.id.image_downlaod);
        imageView = (ImageView) findViewById(R.id.image);
        //fab = (FloatingActionButton) findViewById(R.id.fab);

       /* search = (ImageButton) findViewById(R.id.search);
        url_text = (EditText) findViewById(R.id.url_string);*/
    }

    public void getDataFormShareIcon(){
        originalUrl = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if(TextUtils.isEmpty(originalUrl) || !originalUrl.startsWith(ORIGIN_URL_PRE)){
            makeToast("" + "Please see the guider line for more details");
           /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Error\n")
                    .setMessage("\nInvalid Url Please see the guideline for more details")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();*/
            return ;
        }
        getUrl(originalUrl.replace(ORIGIN_URL_PRE, "").split("/")[1]);
    }

    public void getUrl(String code){

        Api.loadHtml(code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {

                    Handler handler;
                    Bitmap bitmap;

                    @Override
                    public void onCompleted() {
                        unsubscribe();
                        //finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String msg;
                        if (e == null || TextUtils.isEmpty(e.getMessage())) {
                            msg = getString(R.string.unknown_error);
                        } else {
                            msg = e.getMessage();
                        }
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                        unsubscribe();
                        //finish();
                    }

                    @Override
                    public void onNext(String html) {
                        if (TextUtils.isEmpty(html)) {
                            onError(new Exception(getString(R.string.get_html_null)));
                        }

                        //to get the image address
                        makeToast("Parsering the url please wait...");
                        url = ContentParser.parser(html);
                        file_name = ContentParser.getTitle(html);
                        //makeToast("The URL is: " + url);
                        if (TextUtils.isEmpty(url)) {
                            onError(new Exception(getString(R.string.parser_html_null)));
                        }

                        handler = new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                if(msg.what == 0){
                                    //makeToast("The url is + " + url);
                                    SaveImage.saveImg(MainActivity.this, file_name + ".jpg", file_path, url);
                                    imageView.setImageBitmap(bitmap);
                                    textView_tilte.setText(file_name);
                                    //makeToast("Now you can click the button to download the picture ^_^");
                                }
                            }
                        };

                        Thread thread = new Thread(GetDataFromInternet);
                        thread.start();

                        file_path = new CreatFile().CreatFilePath();
                        //to creat a file and download
                        button_download.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (DownloadMangerResolver.resolve(MainActivity.this)) {
                                    SaveImage.saveImg(MainActivity.this, file_name + ".jpg", file_path, url);
                                }
                            }
                        });
                    }

                    Runnable GetDataFromInternet = new Runnable() {
                        @Override
                        public void run() {
                            bitmap = new GetInternetImageResource(context, url)
                                    .getImageResource();
                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    };
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            intent = new Intent(context, Settings_Main.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.viewfile){
            intent = new Intent(context, ViewAllFile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ((Activity)context).finish();
    }

    public void makeToast(String string){
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeToast("You have the permission!");
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private boolean toCheckTheUrlLegal(String string){

        if(TextUtils.isEmpty(string) || !string.startsWith(ORIGIN_URL_PRE)){
            return false;
        }else{
            return true;
        }

    }

}
