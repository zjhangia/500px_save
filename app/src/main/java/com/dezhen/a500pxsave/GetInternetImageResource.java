package com.dezhen.a500pxsave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by root on 25/07/17.
 */

public class GetInternetImageResource {
    private Context context;
    private String string;
    private Bitmap bitmap;

    public GetInternetImageResource(Context context, String string) {
        this.context = context;
        this.string = string;
    }

    public Bitmap getImageResource(){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_download);

        try {
            URL url = new URL(string);
            InputStream is= url.openStream();
            //从InputStream流中解析出图片
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }
}
