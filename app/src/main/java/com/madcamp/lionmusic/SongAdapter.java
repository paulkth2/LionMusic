package com.madcamp.lionmusic;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class SongAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<SongItem> titles;
    private int layout;
    private boolean liked;

    private final Context mContext;


    public SongAdapter(Context context, int layout, ArrayList<SongItem> titles, boolean liked){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles=titles;
        this.layout=layout;
        this.mContext=context;
        this.liked=liked;
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



        TextView artist=(TextView)convertView.findViewById(R.id.artist);
        artist.setText(songitem.getArtist());
        TextView title=(TextView)convertView.findViewById(R.id.songTitle);
        title.setText(songitem.getTitle());
        ToggleButton heartToggle = convertView.findViewById(R.id.likeButton);
        heartToggle.setChecked(liked);
        return convertView;
    }

    /*

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            timeText.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    */
}
