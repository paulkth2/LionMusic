package com.madcamp.lionmusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class MyMusicActivity extends AppCompatActivity {

    private ImageButton homeButton;
    private ImageButton logoutButton;
    private ImageButton friendButton;

    private ListView artistList;
    private ListView songList;

    private String tag1 = "";
    private String tag2 = "";
    private String tag3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymusic);

        homeButton = findViewById(R.id.homeButton);
        logoutButton = findViewById(R.id.logoutButton);
        friendButton = findViewById(R.id.friendButton);

        artistList = findViewById(R.id.artistListview);
        songList = findViewById(R.id.songListview);

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