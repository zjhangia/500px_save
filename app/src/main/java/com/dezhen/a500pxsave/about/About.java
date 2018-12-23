package com.dezhen.a500pxsave.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dezhen.a500pxsave.R;

/**
 * Created by root on 01/08/17.
 */

public class About extends AppCompatActivity {

    private ImageView imageView;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_new_layout);

        setTitle("About");
        imageView = (ImageView) findViewById(R.id.image_scrolling_top);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                About.this.finish();
            }
        });

        Glide.with(this).load(R.mipmap.stock_beauty)
                .fitCenter().into(imageView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        About.this.finish();
    }
}
