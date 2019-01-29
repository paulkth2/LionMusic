package com.madcamp.lionmusic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyMusicActivity extends AppCompatActivity {
    private static final String TAG = "MyMusicActivity";

    private ImageButton homeButton;
    private ImageButton logoutButton;
    private ImageButton friendButton;

    private ListView artistList;
    private ListView songList;

    private String tag1 = "";
    private String tag2 = "";
    private String tag3 = "";

    ArrayList<SongItem> likedSongsArray;
    ArrayList<ArtistItem> likedArtistArray;

    private DatabaseReference likedArtistPreference;

    private TextView tagText1;
    private TextView tagText2;
    private TextView tagText3;

    private TextView nick;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymusic);

        homeButton = findViewById(R.id.homeButton);
        logoutButton = findViewById(R.id.logoutButton);
        friendButton = findViewById(R.id.friendButton);

        artistList = findViewById(R.id.artistListview);
        songList = findViewById(R.id.songListview);

        likedSongsArray = new ArrayList<>();
        likedArtistArray = new ArrayList<>();

        tagText1 = findViewById(R.id.likedTag1);
        tagText2 = findViewById(R.id.likedTag2);
        tagText3 = findViewById(R.id.likedTag3);

        nick = findViewById(R.id.nickname);

        //홈으로 돌아가는 버튼
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        //로그아웃 버튼
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //호오오옥시 자동로그인 지원한다면 로그아웃 넣어주세요!
                goToMainActivity();
            }
        });

        //친구 리스트를 확인하는 버튼
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FriendActivity.class);
                startActivity(intent);
            }
        });

        ImageView profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectProfileActivity.class);
                startActivity(intent);
            }
        });
        likedArtistPreference = FirebaseDatabase.getInstance().getReference();

        Query artistQuery = likedArtistPreference.child("users").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        //getting nickname
        artistQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren()){
                    String nickname = (String) post.child("nickname").getValue();
                    likedArtistArray = new ArrayList<>();
                    nick.setText(nickname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //getting liked aritist
        artistQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren()){
                    ArrayList<String> likedArtists = (ArrayList<String>) post.child("likedArtist").getValue();
                    likedArtistArray = new ArrayList<>();
                    for (int i=0; i<likedArtists.size(); i++){
                        likedArtistArray.add(new ArtistItem(likedArtists.get(i)));
                    }
                    ArtistAdapter adapter = new ArtistAdapter(MyMusicActivity.this, R.layout.artist_item, likedArtistArray, true);
                    artistList.setAdapter(adapter);
                    Log.d(TAG, "Got this: "+likedArtists);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //getting liked songs
        artistQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren()){
                    ArrayList<HashMap<String, String>> likedSongs = (ArrayList<HashMap<String, String>>) post.child("likedSongs").getValue();
                    likedSongsArray = new ArrayList<>();
                    if (likedSongs != null) {
                        for (int i = 0; i < likedSongs.size(); i++) {
                            if (likedSongs.get(i) != null) {
                                SongItem newSong = new SongItem(likedSongs.get(i).get("title"), likedSongs.get(i).get("artist"), Uri.parse(likedSongs.get(i).get("url")), true);
                                likedSongsArray.add(newSong);
                            }
                        }
                        SongAdapter adapter = new SongAdapter(MyMusicActivity.this, R.layout.song_item, likedSongsArray);
                        songList.setAdapter(adapter);
                    }
                    //Log.d(TAG, "Got this: "+likedArtists);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //getting tags
        artistQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot post : dataSnapshot.getChildren()){
                    ArrayList<String> tags = (ArrayList<String>) post.child("tags").getValue();
                    if (tags.size()>0){
                        tagText1.setText(tags.get(0));
                    }
                    if (tags.size()>1){
                        tagText2.setText(tags.get(1));
                    }
                    if (tags.size()>2){
                        tagText3.setText(tags.get(2));
                    }
                    //Log.d(TAG, "Got this: "+likedArtists);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MyMusicActivity.this, PlayerActivity.class);
                myIntent.putExtra("uri", likedSongsArray.get(position).getSongUri().toString());
                myIntent.putExtra("title", likedSongsArray.get(position).getTitle());
                myIntent.putExtra("artist", likedSongsArray.get(position).getArtist());
                myIntent.putExtra("liked", true);
                startActivity(myIntent);
            }
        });

        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String urlFirst = "https://secure.galiboo.com/api/metadata/tracks/search/?token=7bc7a3054fbbf21480aa5f767fc67aa31fc30c68&threshold=0.8&";
                String urlSecond = "&limit=20&page=1";
                String urlPart = "";
                urlPart = "artist=";
                String url = urlFirst+urlPart+likedArtistArray.get(position).getArtist()+urlSecond;

                Intent myIntent = new Intent(MyMusicActivity.this, SongsActivity.class);
                myIntent.putExtra("SearchValue", url);
                startActivity(myIntent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyMusicActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(MyMusicActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        //좋아하는 가수, 노래, 태그 설정
        //가수는 ArtistAdapter.java, ArtistItem.java, artist_item.xml 만들어놓음
        //노래는 아마 songitem 관련 클래스와 xml을 재활용하면 되지 않을까요 ㅎㅅㅎ
        //태그는 리스트뷰로 할까 했는데 좋아하는 태그 세 가지 정도만 보여줄 거면 그냥 텍스트뷰로 만드는 게 편할 거 같아서 텍스트뷰로 만들어놨어요
    }

    //MyMusicActivity를 끝내고 홈으로 돌아가는 함수
    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}