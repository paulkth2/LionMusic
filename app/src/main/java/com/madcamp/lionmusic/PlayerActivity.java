package com.madcamp.lionmusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PlayerActivity extends AppCompatActivity {
    private TextView titleText;
    private SeekBar seekBar;
    private ToggleButton playButton;
    private ImageButton rewindButton;
    private ImageButton forwardButton;
    private TextView timeText;

    private String songTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);

        songTitle = getIntent().getStringExtra("title");

        //initialize views
        titleText = (TextView)findViewById(R.id.playerSongTitle);
        timeText = (TextView)findViewById(R.id.timeLeft);
        playButton = (ToggleButton)findViewById(R.id.playToggle);
        rewindButton = (ImageButton)findViewById(R.id.rewindBackButton);
        forwardButton = (ImageButton)findViewById(R.id.rewindForwardButton);



    }

}
