package com.madcamp.lionmusic;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewManager;

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
        loadingView.addAnimation(Color.parseColor("#ffebf4"), kakao_1, LoadingView.FROM_LEFT);
        loadingView.addAnimation(Color.parseColor("#ebfff6"), kakao_2,LoadingView.FROM_TOP);
        loadingView.addAnimation(Color.parseColor("#f0ebff"), kakao_3, LoadingView.FROM_RIGHT);
        loadingView.addAnimation(Color.parseColor("#dbe7b5"), kakao_4, LoadingView.FROM_BOTTOM);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingView.startAnimation();
        loadingView.setRepeat(1000);
    }

    public void start(View v) {
        loadingView.startAnimation();
    }

    private void endLoading(){
        loadingView.pauseAnimation();
        ((ViewManager)loadingView.getParent()).removeView(loadingView);
    }
}
