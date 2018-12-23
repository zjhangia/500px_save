package com.dezhen.a500pxsave.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dezhen.a500pxsave.R;
import com.dezhen.a500pxsave.about.About;
import com.dezhen.a500pxsave.address.Address;
import com.dezhen.a500pxsave.guidline.Guidline;

import java.util.ArrayList;

/**
 * Created by root on 29/07/17.
 */

public class MyRecyclerViewAdapter extends
        RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>  {
    private ArrayList<String> arrayList;
    private Context context;

    private Intent intent;

    public MyRecyclerViewAdapter(Context context, ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.settings_cardview,
                parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*((TextView)holder.itemView.findViewById(R.id.textview_recycleview))
                .setText(arrayList.get(position));*/
        final int count = position;
        holder.textView.setText(arrayList.get(position));
        CardView cardView = (CardView) holder.itemView.findViewById(R.id.settings_content);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){

                    intent = new Intent(context, Guidline.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
                }
                if(count == 1){
                    intent = new Intent(context, About.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                if(count == 2){
                    intent = new Intent();
                    intent.setData(Uri.parse(Address.app_site));
                    intent.setAction(Intent.ACTION_VIEW);
                    context.startActivity(intent);
                }
                if(count == 3){
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT,Address.share);
                    intent.setType("text/plain");
                    context.startActivity(intent);
                }
                if(count == 4){
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse(Address.email));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback:500pxSave");
                    intent.putExtra(Intent.EXTRA_TEXT, "Hi DeZhenTec:");
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.settings_textview);
            cardView = (CardView)itemView.findViewById(R.id.settings_content);
        }
    }
}
