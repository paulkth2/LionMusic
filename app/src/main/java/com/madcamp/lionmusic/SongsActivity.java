package com.madcamp.lionmusic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SongsActivity extends Activity {
    private ArrayList<SongItem> titles;
    private String requestUrl = "";
    private ListView songList;
    private static final String TAG = "SongsActivity";
    private ArrayList<SongItem> testTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        testTitles = new ArrayList<>();
        titles = new ArrayList<>();
        String searchString = getIntent().getStringExtra("SearchValue");

        songList = (ListView) findViewById(R.id.song_list);

        String urlFirst = "https://secure.galiboo.com/api/discover/tracks/smart_search/?token=7bc7a3054fbbf21480aa5f767fc67aa31fc30c68&threshold=0.95&q=";
        String urlSecond = "&count=50&page=1";

        requestUrl = urlFirst+searchString+urlSecond;
        Log.d(TAG, "onCreate: "+requestUrl);




        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: is it even trying?");
                try {
                    JSONArray resultArray = response.getJSONArray("results");
                    Log.d(TAG, "onResponse: response Arrived");
                    Log.d(TAG, "onResponse: responses:" +resultArray.toString());
                    for (int i=0; i < resultArray.length(); i++) {
                        titles.add(new SongItem(resultArray.getJSONObject(i).getString("title")));
                        Log.d(TAG, "onResponse: "+resultArray.getJSONObject(i).getString("title"));
                    }

                    SongAdapter adapter2 = new SongAdapter(SongsActivity.this, R.layout.song_item, titles);
                    songList.setAdapter(adapter2);

                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: exception catching");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: volley error!");
            }
        }){

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");
            return headers;
        }};

        Volley.newRequestQueue(SongsActivity.this).add(jsonObjectRequest);



    }




}
