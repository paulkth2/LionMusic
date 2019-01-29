package com.madcamp.lionmusic;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    private ImageButton closeButton;
    private Button cancelButton;
    private Button addButton;
    private EditText emailInput;

    private DatabaseReference likedArtistPreference;
    private String key;
    private boolean stopDuplicateArtist= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        //추가할 친구의 이메일 입력 칸
        emailInput = findViewById(R.id.AddFriendEmailInput);

        //친구 추가 닫기 버튼
        closeButton = findViewById(R.id.AddFriendCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //친구 추가 취소 버튼
        cancelButton = findViewById(R.id.AddFriendCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        //친구 추가 버튼
        addButton = findViewById(R.id.AddFriendButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("friends").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> givenList = (ArrayList<String>) dataSnapshot.getValue();
                        if (givenList == null) {
                            givenList = new ArrayList<>();
                        }
                        if (!stopDuplicateArtist) {
                            givenList.add(emailInput.getText().toString());
                            FirebaseDatabase.getInstance().getReference().getRoot().child("users").child(key).child("friends").setValue(givenList);
                            stopDuplicateArtist = true;
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
