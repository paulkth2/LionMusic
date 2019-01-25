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
    private MediaPlayer mediaPlayer;
    private ToggleButton playButton;
    private ImageButton ffButton;
    private SeekBar seekBar;

    private double startTime = 0;
    private double finalTime = 0;

    private int forwardTime = 5000;

    private final Context mContext;
    private Handler myHandler = new Handler();



    public SongAdapter(Context context, int layout, ArrayList<SongItem> titles){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titles=titles;
        this.layout=layout;
        this.mContext=context;
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

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        playButton = (ToggleButton) convertView.findViewById(R.id.playToggle);
        ffButton = (ImageButton) convertView.findViewById(R.id.rewindButton);
        seekBar = (SeekBar) convertView.findViewById(R.id.seekBar);

        try {
            mediaPlayer.setDataSource(mContext, songitem.getSongUri());
        } catch (IllegalArgumentException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
                else{
                    mediaPlayer.pause();
                }
            }
        });

        ffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);

            }
        }});



        TextView artist=(TextView)convertView.findViewById(R.id.artist);
        artist.setText(songitem.getArtist());
        TextView title=(TextView)convertView.findViewById(R.id.songTitle);
        title.setText(songitem.getTitle());
        return convertView;
    }

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
}
