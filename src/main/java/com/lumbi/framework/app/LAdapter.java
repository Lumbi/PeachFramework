package com.lumbi.framework.app;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriellumbi on 14-11-26.
 * Custom generic adapter.
 */
public abstract class LAdapter<T> extends BaseAdapter {

    protected List<T> mItems = new ArrayList<T>();
    protected Context mContext = null;

    public void update(List<T> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    public List<T> getItems(){ return mItems; }

    public T getModel(int i){
        return mItems.get(i);
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
