package com.madcamp.lionmusic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends Activity {
    private ToggleButton searchToggle;
    private EditText searchInput;
    private Button searchButton;

    private static final String TAG = "SearchActivity";

    private boolean categoryBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchToggle = (ToggleButton) findViewById(R.id.searchToggle);
        searchInput = (EditText) findViewById(R.id.search_input);
        searchButton = (Button) findViewById(R.id.search_button);

        searchToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    categoryBool = true;
                } else {
                    categoryBool = false;
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlFirst = "https://secure.galiboo.com/api/metadata/tracks/search/?token=7bc7a3054fbbf21480aa5f767fc67aa31fc30c68&threshold=0.8&";
                String urlSecond = "&limit=20&page=1";
                String urlPart = "";

                if(categoryBool){
                    urlPart = "track";
                } else {
                    urlPart = "artist";
                }

                String url = urlFirst+urlPart+searchInput.getText().toString()+urlSecond;

                Intent myIntent = new Intent(SearchActivity.this, SongsActivity.class);
                myIntent.putExtra("SearchValue", url);
                startActivity(myIntent);
            }
        });


    }
}
