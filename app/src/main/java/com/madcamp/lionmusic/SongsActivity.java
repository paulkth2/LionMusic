package com.madcamp.lionmusic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import io.saeid.fabloading.LoadingView;

public class SongsActivity extends Activity {

    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        loadingView = findViewById(R.id.loading_view);
        int kakao_1 = R.drawable.kakao1;
        int kakao_2 = R.drawable.kakao2;
        int kakao_3 = R.drawable.kakao3;
        int kakao_4 = R.drawable.kakao4;
        loadingView.addAnimation(Color.parseColor("#FFD200"), kakao_1, LoadingView.FROM_LEFT);
        loadingView.addAnimation(Color.parseColor("#2F5DA9"), kakao_2,LoadingView.FROM_TOP);
        loadingView.addAnimation(Color.parseColor("#FF4218"), kakao_3, LoadingView.FROM_RIGHT);
        loadingView.addAnimation(Color.parseColor("#C7E7FB"), kakao_4, LoadingView.FROM_BOTTOM);

        loadingView.startAnimation();
    }
}
