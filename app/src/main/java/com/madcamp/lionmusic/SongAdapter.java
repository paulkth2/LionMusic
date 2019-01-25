package com.madcamp.lionmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<SongItem> titles;
    private int layout;
    public SongAdapter(Context context, int layout, ArrayList<SongItem> titles){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles=titles;
        this.layout=layout;
    }

    @Override
    public int getCount(){return titles.size();}
    @Override
    public String getItem(int position){return titles.get(position).getTitle();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        SongItem songitem = titles.get(position);
        //ImageView icon=(ImageView)convertView.findViewById(R.id.imageview);
        //icon.setImageResource(listviewitem.getIcon());
        TextView name=(TextView)convertView.findViewById(R.id.songTitle);
        name.setText(songitem.getTitle());
        return convertView;
    }
}
