package com.madcamp.lionmusic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Button cancelButton;
    private ImageButton closeButton;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private static final String TAG = "RegisterActivity";

    private TextView email;
    private TextView nick;
    private TextView pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //textview initialization
        email = findViewById(R.id.RegisterEmailInput);
        nick = findViewById(R.id.NicknameInput);
        pass = findViewById(R.id.RegisterPWInput);

        cancelButton = findViewById(R.id.RegisterCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        closeButton = findViewById(R.id.registerCloseButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        registerButton = findViewById(R.id.RegisterConfirmButton);

        //initialize firebase stuffs
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtxt = email.getText().toString();
                String passwordtxt = pass.getText().toString();

                if(TextUtils.isEmpty(emailtxt)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passwordtxt)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                }

                if(passwordtxt.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }

                //Registration part
                firebaseAuth.createUserWithEmailAndPassword(emailtxt, passwordtxt)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    User user = new User();
                                    user.setEmail(email.getText().toString());
                                    user.setNickname(nick.getText().toString());
                                    user.setProfile("1");

                                    List<String> friendList = new ArrayList<>();
                                    friendList.add("paulkth2@naver.com");
                                    user.setFriends(friendList);

                                    List<String> likedArtistList = new ArrayList<>();
                                    likedArtistList.add("Imagine Dragons");
                                    user.setLikedArtist(likedArtistList);

                                    List<String> tagsList = new ArrayList<>();
                                    tagsList.add("Charming");
                                    tagsList.add("Cool");
                                    tagsList.add("Exciting");
                                    user.setTags(tagsList);

                                    List<HashMap<String, String>> likedSongList = new ArrayList<>();
                                    HashMap<String, String> song = new HashMap<>();
                                    song.put("title", "Thunder");
                                    song.put("artist", "Adam Sanders");
                                    song.put("url", "https://storage.googleapis.com/gb_spotify20k/spotify_preview_audios/5SexQfrzlUXaW3SRlceVEt.mp3");
                                    likedSongList.add(song);
                                    user.setLikedSongs(likedSongList);



                                    DatabaseReference newUser = firebaseDatabase.getReference().child("users").push();


                                    newUser.setValue(user);
                                    Toast.makeText(RegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();

                                    //Returns to login Activity by finishing
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });






    }
}
