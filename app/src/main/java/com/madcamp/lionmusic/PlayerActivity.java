package com.madcamp.lionmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PlayerActivity extends AppCompatActivity {
    private static final String TAG = "PlayerActivity";
    
    private TextView titleText;
    private SeekBar seekBar;
    private ToggleButton playButton;
    private ImageButton rewindButton;
    private ImageButton forwardButton;
    private TextView timeText;
    private TextView artistText;

    private MediaPlayer mediaPlayer;
    private Uri songUri;

    private String songTitle;

    private double startTime = 0;
    private double finalTime = 0;

    public static int oneTimeOnly = 0;

    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;

    private ToggleButton likedArtist;
    private ToggleButton likedSong;

    private DatabaseReference likedArtistPreference;
    private ArrayList<String> likedArtists;

    private String key;

    private boolean stopDuplicate;
    private boolean stopInsertDuplicate;
    private boolean stopDuplicateArtist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);

        songTitle = getIntent().getStringExtra("title");

        //initialize views
        titleText = (TextView)findViewById(R.id.playerSongTitle);
        timeText = (TextView)findViewById(R.id.timeLeft);
        artistText = (TextView)findViewById(R.id.playerArtist);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        playButton = (ToggleButton)findViewById(R.id.playToggle);
        rewindButton = (ImageButton)findViewById(R.id.rewindBackButton);
        forwardButton = (ImageButton)findViewById(R.id.rewindForwardButton);

        Log.d(TAG, "song url: "+getIntent().getStringExtra("uri"));
        songUri = Uri.parse(getIntent().getStringExtra("uri"));
        seekBar.setClickable(false);

        titleText.setText(getIntent().getStringExtra("title"));
        artistText.setText(getIntent().getStringExtra("artist"));

        likedSong = findViewById(R.id.likeSongButton);
        likedArtist = findViewById(R.id.likeArtistButton);

        stopDuplicate = false;
        stopInsertDuplicate = false;
        stopDuplicateArtist = false;
        key = "";
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

        likedSong.setChecked(getIntent().getBooleanExtra("liked", false));
        if(likedSong.isChecked()){
            likedSong.setClickable(false);
        }
        likedArtist.setChecked(false);
        if(likedArtist.isChecked()){
            likedArtist.setClickable(false);
        }

        likedArtistPreference = FirebaseDatabase.getInstance().getReference();

        Query artistQuery = likedArtistPreference.child("users").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //getting liked aritist
        artistQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    key = post.getKey();
                    likedArtists = (ArrayList<String>) post.child("likedArtist").getValue();
                    for (int i = 0; i < likedArtists.size(); i++) {
                        if (likedArtists.get(i).equals(getIntent().getStringExtra("artist"))) {
                            likedArtist.setChecked(true);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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

                    timeText.setText(String.format("%d: %d",
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

        titleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlFirst = "https://secure.galiboo.com/api/metadata/tracks/search/?token=7bc7a3054fbbf21480aa5f767fc67aa31fc30c68&threshold=0.8&";
                String urlSecond = "&limit=20&page=1";
                String urlPart = "track=";

                String url = urlFirst+urlPart+titleText.getText().toString()+urlSecond;

                Intent myIntent = new Intent(PlayerActivity.this, SongsActivity.class);
                myIntent.putExtra("SearchValue", url);
                mediaPlayer.stop();
                oneTimeOnly = 0;
                seekBar.setProgress(0);
                startActivity(myIntent);
                finish();
            }
        });

        artistText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlFirst = "https://secure.galiboo.com/api/metadata/tracks/search/?token=7bc7a3054fbbf21480aa5f767fc67aa31fc30c68&threshold=0.8&";
                String urlSecond = "&limit=20&page=1";
                String urlPart = "artist=";

                String url = urlFirst+urlPart+artistText.getText().toString()+urlSecond;

                Intent myIntent = new Intent(PlayerActivity.this, SongsActivity.class);
                myIntent.putExtra("SearchValue", url);
                mediaPlayer.stop();
                oneTimeOnly = 0;
                seekBar.setProgress(0);
                startActivity(myIntent);
                finish();
            }
        });

        //song like onClickListener
        likedSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!likedSong.isChecked()){
                    /*
                    Log.d(TAG, "onClick: clicked");
                    FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("likedSongs").orderByChild("title").equalTo(getIntent().getStringExtra("title")).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot post: dataSnapshot.getChildren()){
                                String Songkey = post.getKey();
                                Log.d(TAG, "onDataChange: "+Songkey);
                                dataSnapshot.getRef().child(Songkey).removeValue();
                                stopDuplicate=false;
                                stopInsertDuplicate=false;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    */
                } else {

                    //getting nickname
                    FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("likedSongs").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<HashMap<String, String>> givenList = (ArrayList<HashMap<String,String>>) dataSnapshot.getValue();
                            if(givenList == null){
                                givenList = new ArrayList<>();
                            }
                            if(!stopDuplicate) {
                                    HashMap<String, String> song = new HashMap<>();
                                    song.put("title", getIntent().getStringExtra("title"));
                                    song.put("artist", getIntent().getStringExtra("artist"));
                                    song.put("url", getIntent().getStringExtra("uri"));
                                    givenList.add(song);
                                    FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("likedSongs").setValue(givenList);
                                    stopDuplicate = true;
                                    likedSong.setClickable(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    //FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("likedSongs").child(newSongKey).setValue(song);
                }
            }
        });

        likedArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(likedArtist.isChecked()){
                    //getting nickname
                    FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("likedArtist").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArrayList<String> givenList = (ArrayList<String>) dataSnapshot.getValue();
                            if (givenList == null) {
                                givenList = new ArrayList<>();
                            }
                            if (!stopDuplicateArtist) {
                                givenList.add(getIntent().getStringExtra("artist"));
                                FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("likedArtist").setValue(givenList);
                                stopDuplicateArtist = true;
                                likedArtist.setClickable(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            timeText.setText(String.format("%d: %d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: ");
        oneTimeOnly = 0;
        seekBar.setProgress(0);
        mediaPlayer.stop();
        finish();
    }
}
