package com.dezhen.a500pxsave.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dezhen.a500pxsave.R;

import java.util.ArrayList;

/**
 * Created by root on 29/07/17.
 */

public class Settings_Main extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private String string_content[] = {"\t1. How to use this app",
                                       "\t2. About",
                                       "\t3. Rate this app",
                                       "\t4. Share this app",
                                       "\t5. Suggestions"};
    private ArrayList<String> arrayList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_new_layout);
        setTitle("Setting");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.image_scrolling_top);
        Glide.with(this).load(R.mipmap.stock_beauty)
                .fitCenter().into(imageView);


        getInstance();

        arrayList = new ArrayList<>();
        for(int i = 0; i < string_content.length; i ++){
            arrayList.add(string_content[i]);
        }

        //very important without this there will be nothing to display
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);

        recyclerView.addItemDecoration(new ItemDecoration(context,
                ItemDecoration.VERTICAL_LIST));

        recyclerView.setAdapter(new MyRecyclerViewAdapter(context, arrayList));
    }

    public void getInstance(){
        context = Settings_Main.this;
        recyclerView = (RecyclerView) findViewById(R.id.settings_recylcerview);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Settings_Main.this.finish();
    }
}
