package com.madcamp.lionmusic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.view.ViewManager;
import android.widget.Toast;

import io.saeid.fabloading.LoadingView;

public class SongsActivity extends Activity {
    private ArrayList<SongItem> titles;
    private String requestUrl = "";
    private ListView songList;
    private static final String TAG = "SongsActivity";

    private LoadingView loadingView;

    private ArrayList<HashMap<String, String>> likedSongs;

    private DatabaseReference likedArtistPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        titles = new ArrayList<>();
        String requestUrl = getIntent().getStringExtra("SearchValue");

        songList = (ListView) findViewById(R.id.song_list);

        Log.d(TAG, "onCreate: "+requestUrl);

        loadingView = findViewById(R.id.loading_view);
        int kakao_1 = R.drawable.kakao1;
        int kakao_2 = R.drawable.kakao2;
        int kakao_3 = R.drawable.kakao3;
        int kakao_4 = R.drawable.kakao4;
        loadingView.addAnimation(Color.parseColor("#ffebf4"), kakao_1, LoadingView.FROM_LEFT);
        loadingView.addAnimation(Color.parseColor("#ebfff6"), kakao_2, LoadingView.FROM_TOP);
        loadingView.addAnimation(Color.parseColor("#f0ebff"), kakao_3, LoadingView.FROM_RIGHT);
        loadingView.addAnimation(Color.parseColor("#dbe7b5"), kakao_4, LoadingView.FROM_BOTTOM);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray("results");
                    for (int i=0; i < resultArray.length(); i++) {
                        JSONObject jsonObject = resultArray.getJSONObject(i);
                        titles.add(new SongItem(jsonObject.getString("title"), jsonObject.getJSONArray("artists").getJSONObject(0).getString("name"), Uri.parse(jsonObject.getString("audio_url")), false));
                    }


                    likedArtistPreference = FirebaseDatabase.getInstance().getReference();

                    Query artistQuery = likedArtistPreference.child("users").orderByChild("email").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    //getting liked aritist
                    artistQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot post : dataSnapshot.getChildren()){
                                likedSongs = (ArrayList<HashMap<String, String>>) post.child("likedSongs").getValue();
                                if(likedSongs != null) {
                                    for (int i = 0; i < likedSongs.size(); i++) {
                                        for (int j = 0; j < titles.size(); j++) {
                                            if(likedSongs.get(i) != null) {
                                                if (likedSongs.get(i).get("title").equals(titles.get(j).getTitle()) && likedSongs.get(i).get("artist").equals(titles.get(j).getArtist())) {
                                                    titles.get(j).setLiked(true);
                                                }
                                            }
                                        }
                                    }
                                }

                                SongAdapter adapter2 = new SongAdapter(SongsActivity.this, R.layout.song_item, titles);
                                songList.setAdapter(adapter2);

                                loadingView.pauseAnimation();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

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




        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(SongsActivity.this, "clicked item"+position, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(SongsActivity.this, PlayerActivity.class);
                myIntent.putExtra("uri", titles.get(position).getSongUri().toString());
                myIntent.putExtra("title", titles.get(position).getTitle());
                myIntent.putExtra("artist", titles.get(position).getArtist());
                myIntent.putExtra("liked", titles.get(position).isLiked());
                startActivity(myIntent);
            }
        });





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
