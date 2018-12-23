package com.dezhen.a500pxsave.guidline;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by root on 30/07/17.
 */

public class MyViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> arrayList;

    public MyViewPagerAdapter(ArrayList<View> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(arrayList.get(position));
        //return super.instantiateItem(container, position);
        return arrayList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(arrayList.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
