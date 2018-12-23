package com.dezhen.a500pxsave.guidline;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dezhen.a500pxsave.R;
import com.dezhen.a500pxsave.common.CommonActivity;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by root on 30/07/17.
 */

public class Guidline extends AppCompatActivity {
    private Context context;
    private ViewPager viewPager;
    private ArrayList<View> arrayList;
    private LayoutInflater layoutInflater;
    private View[] views;
    private LinearLayout linearLayout;
    private ImageView[] imageViews;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.howtouse_layout);
        setTitle("Guider Line");
        getInstance();
        initViews();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Guidline");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });

        arrayList = new ArrayList<>();
        for(int i = 0; i < views.length; i ++){
            arrayList.add(views[i]);
        }

        viewPager.setAdapter(new MyViewPagerAdapter(arrayList));
    }

    private void getInstance(){
        context = Guidline.this;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        layoutInflater = getLayoutInflater();
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
    }

    private void initViews(){
        views = new View[5];
        imageViews = new ImageView[5];
        views[0] = layoutInflater.inflate(R.layout.guidline_0, linearLayout, false);
        imageViews[0] = (ImageView) views[0].findViewById(R.id.guildline_image_0);
        imageViews[0].setImageBitmap(getBitmap(R.mipmap.guideline_0));
        views[1] = layoutInflater.inflate(R.layout.guidline_1, linearLayout, false);
        imageViews[1] = (ImageView) views[1].findViewById(R.id.guildline_image_1);
        imageViews[1].setImageBitmap(getBitmap(R.mipmap.guideline_1));
        views[2] = layoutInflater.inflate(R.layout.guidline_2, linearLayout, false);
        imageViews[2] = (ImageView) views[2].findViewById(R.id.guildline_image_2);
        imageViews[2].setImageBitmap(getBitmap(R.mipmap.guideline_2));
        views[3] = layoutInflater.inflate(R.layout.guidline_3, linearLayout, false);
        imageViews[3] = (ImageView) views[3].findViewById(R.id.guildline_image_3);
        imageViews[3].setImageBitmap(getBitmap(R.mipmap.guideline_3));
        views[4] = layoutInflater.inflate(R.layout.guidline_4, linearLayout, false);
    }

    private Bitmap getBitmap(int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Guidline.this.finish();
    }
}
