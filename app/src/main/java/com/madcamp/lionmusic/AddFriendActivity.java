package com.madcamp.lionmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddFriendActivity extends AppCompatActivity {

    private ImageButton closeButton;
    private Button cancelButton;
    private Button addButton;
    private EditText emailInput;

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

        //친구 추가 버튼
        addButton = findViewById(R.id.AddFriendButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
