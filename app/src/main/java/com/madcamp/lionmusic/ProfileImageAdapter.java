package com.madcamp.lionmusic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ProfileImageAdapter extends BaseAdapter {

    private Context mContext;

    // Constructor
    public ProfileImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.ryan01, R.drawable.ryan02, R.drawable.ryan03, R.drawable.ryan04,
            R.drawable.ryan05, R.drawable.ryan06, R.drawable.ryan07, R.drawable.ryan08,
            R.drawable.ryan09, R.drawable.ryan10, R.drawable.ryan11, R.drawable.ryan12,
            R.drawable.ryan13, R.drawable.ryan14, R.drawable.ryan15, R.drawable.ryan16
    };

}
