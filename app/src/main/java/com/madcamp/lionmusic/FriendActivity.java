package com.madcamp.lionmusic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendActivity extends AppCompatActivity {
    private static final String TAG = "FriendActivity";

    private ImageButton addFriendButton;
    private DatabaseReference likedArtistPreference;
    private ArrayList<String> mfriends;
    private ListView friendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        friendList = findViewById(R.id.friendListview);
        mfriends = new ArrayList<>();


        addFriendButton = findViewById(R.id.addButton);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFriendActivity.class);
                startActivity(intent);
            }
        });

        likedArtistPreference = FirebaseDatabase.getInstance().getReference();

        Query artistQuery = likedArtistPreference.child("users").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //getting liked aritist
        artistQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    mfriends = (ArrayList<String>) post.child("friends").getValue();
                    Log.d(TAG, "onDataChange: "+mfriends);
                    FriendAdapter adapter = new FriendAdapter(FriendActivity.this, R.layout.friend_item, mfriends);
                    friendList.setAdapter(adapter);
                    //SongAdapter adapter2 = new SongAdapter(SongsActivity.this, R.layout.song_item, titles);
                    //songList.setAdapter(adapter2);
                    //loadingView.pauseAnimation();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(FriendActivity.this, FriendMusicActivity.class);
                myIntent.putExtra("email", mfriends.get(position));
                startActivity(myIntent);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        //친구 리스트 설정
        //FriendItem, FriendAdapter
    }
}
