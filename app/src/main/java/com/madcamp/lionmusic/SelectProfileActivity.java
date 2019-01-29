package com.madcamp.lionmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

public class SelectProfileActivity extends AppCompatActivity {

    private GridView profileGrid;
    private ImageButton closeButton;
    private Button cancelButton;

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
    }

    @Override
    public void onResume(){
        super.onResume();
        ProfileImageAdapter profileImageAdapter = new ProfileImageAdapter(this);
        profileGrid.setAdapter(profileImageAdapter);

        profileGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){


                Toast.makeText(getApplicationContext(), "Item " + position + " selected.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
