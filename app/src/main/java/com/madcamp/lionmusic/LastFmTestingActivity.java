package com.madcamp.lionmusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.umass.lastfm.Caller;

public class LastFmTestingActivity extends AppCompatActivity {

    private static final String TAG = "LastFmTestingActivity";
    private TextView resText;

    //Stores session key
    private String key = "";
    private String password = "!Xswe1478";
    private String username = "paulkth2";
    private String secret = "48eafeb15620ecb849188752993f2547";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_fm_testing);

        resText = (TextView)findViewById(R.id.resText);

        //Before making requests
        String getMobileSessionOne = "https://ws.audioscrobbler.com/2.0/?method=auth.getMobileSession&password=";
        String getMobileSessionTwo = "&username=";
        String getMobileSessionThree = "&api_key=092309c4abd76c8769f8e7c023498ef5&api_sig=";
        String api_sig = "";
        String getMobileSessionFour = "&format=json";

        //getting md5 formatted session
        api_sig = md5("api_key092309c4abd76c8769f8e7c023498ef5methodauth.getMobileSessionpassword"+password+"username"+username+secret);

        final String requestUrl = getMobileSessionOne+password+getMobileSessionTwo+username+getMobileSessionThree+api_sig+getMobileSessionFour;
        Log.d(TAG, requestUrl);
        Log.d(TAG, "api_sig:"+api_sig);
                
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, requestUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    key = response.getJSONObject("session").getString("key");
                    resText.setText(key);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(LastFmTestingActivity.this).add(jsonObjectRequest);
    }



    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
