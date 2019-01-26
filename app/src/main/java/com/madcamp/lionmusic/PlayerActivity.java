package com.madcamp.lionmusic;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {
    private TextView titleText;
    private SeekBar seekBar;
    private ToggleButton playButton;
    private ImageButton rewindButton;
    private ImageButton forwardButton;
    private TextView timeText;

    private MediaPlayer mediaPlayer;
    private Uri songUri;

    private String songTitle;

    private double startTime = 0;
    private double finalTime = 0;

    public static int oneTimeOnly = 0;

    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);

        songTitle = getIntent().getStringExtra("title");

        //initialize views
        titleText = (TextView)findViewById(R.id.playerSongTitle);
        timeText = (TextView)findViewById(R.id.timeLeft);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        playButton = (ToggleButton)findViewById(R.id.playToggle);
        rewindButton = (ImageButton)findViewById(R.id.rewindBackButton);
        forwardButton = (ImageButton)findViewById(R.id.rewindForwardButton);

        songUri = Uri.parse(getIntent().getStringExtra("uri"));
        seekBar.setClickable(false);

        titleText.setText(getIntent().getStringExtra("title"));

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(PlayerActivity.this, songUri);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }

        playButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mediaPlayer.start();

                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekBar.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }

                    /*
                    tx2.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            finalTime)))
                    );*/

                    timeText.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                            startTime)))
                    );

                    seekBar.setProgress((int)startTime);
                    myHandler.postDelayed(UpdateSongTime,100);
                } else {
                    mediaPlayer.pause();
                }

            }
        });

        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);

                }
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);

                }
            }
        });


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
