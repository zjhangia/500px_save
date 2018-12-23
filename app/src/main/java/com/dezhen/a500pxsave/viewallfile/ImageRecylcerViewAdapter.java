package com.dezhen.a500pxsave.viewallfile;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dezhen.a500pxsave.R;

import java.util.ArrayList;

/**
 * Created by root on 23/08/17.
 */

public class ImageRecylcerViewAdapter extends
        RecyclerView.Adapter<ImageRecylcerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Bitmap> arrayList;

    public ImageRecylcerViewAdapter(Context context, ArrayList<Bitmap> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public ImageRecylcerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.picture_layout, parent, false);
        ImageRecylcerViewAdapter.MyViewHolder myViewHolder
                = new ImageRecylcerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int count = position;
        holder.imageView.setImageBitmap(arrayList.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra("mimeType", "image*//*");
                Uri uri = Uri.parse(MediaStore.Images.Media
                        .insertImage(context.getContentResolver(),
                                arrayList.get(count), null, null));
                intent.setData(uri);
                ((Activity)context).startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public MyViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.a500px_imageview);
        }
    }
}
