package com.madcamp.lionmusic;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
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

public class SelectProfileActivity extends AppCompatActivity {

    private GridView profileGrid;
    private ImageButton closeButton;
    private Button cancelButton;

    private boolean set = false;
    private String key = "";
    private DatabaseReference likedArtistPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        profileGrid = findViewById(R.id.profileGrid);
        cancelButton = findViewById(R.id.selectProfileCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        closeButton = findViewById(R.id.selectProfileCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        likedArtistPreference = FirebaseDatabase.getInstance().getReference();

        Query artistQuery = likedArtistPreference.child("users").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //getting liked aritist
        artistQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    key = post.getKey();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ProfileImageAdapter profileImageAdapter = new ProfileImageAdapter(this);
        profileGrid.setAdapter(profileImageAdapter);

        profileGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                //getting nickname
                final int positioned = position;
                FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("profile").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!set){
                        FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("profile").setValue(String.valueOf(positioned));
                        set = true;}
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("likedSongs").child(newSongKey).setValue(song);
            }

        });

    }

    @Override
    public void onResume(){
        super.onResume();


    }
}
