package com.dezhen.a500pxsave.viewallfile;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dezhen.a500pxsave.R;
import com.dezhen.a500pxsave.settings.ItemDecoration;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by root on 23/08/17.
 */

public class ViewAllFile extends AppCompatActivity {
    private Context context;
    private RecyclerView recyclerView;

    private ArrayList<Bitmap> arrayList;
    private final String file_path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "500pxSave";
    private File[] files;
    private int count;
    private Bitmap[] bitmaps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlypicture_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("View Files");

        getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });

        files = new File(file_path).listFiles();
        if(files == null){
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Warning\n")
                    .setMessage("\nYou haven't downloaded any pictures yet.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ((Activity) context).finish();
                        }
                    })
                    .show();
        }else{
            count = files.length;
            if(count == 0){
                Toast.makeText(context, "You haven't downloaded any pictures yet!",
                        Toast.LENGTH_SHORT).show();
            }else{
                if(count > 15){
                    //count = 15;
                }
                arrayList = new ArrayList<>();
                bitmaps = new Bitmap[count];
                for(int i = 0; i < count; i ++){
                    bitmaps[i] = BitmapFactory.decodeFile(files[i].getPath());
                    arrayList.add(bitmaps[i]);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                recyclerView.addItemDecoration(new ItemDecoration(context,
                        ItemDecoration.VERTICAL_LIST));

                recyclerView.setAdapter(new ImageRecylcerViewAdapter(context, arrayList));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_viewfile_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        int id = item.getItemId();
        if(id ==  R.id.file_help){
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Help")
                    .setMessage("\n1.\tShow all the downloaded pictures"
                            + "\n\n" + "2.\tClick the picture to set wallpaper\n")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getInstance(){
        context = ViewAllFile.this;
        recyclerView = (RecyclerView) findViewById(R.id.onlypicture_recyclerview);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ViewAllFile.this.finish();
    }
}
