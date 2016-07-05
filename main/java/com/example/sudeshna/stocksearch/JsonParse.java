package com.example.sudeshna.stocksearch;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sindhu Mukunda on 4/16/2016.
 */
public class JsonParse {
    final String TAG = "JsonParser.java";

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONObject getJSONFromUrl(URL url) {

        // make HTTP request
        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();
            //DefaultHttpClient httpClient = new DefaultHttpClient();
         //   HttpPost httpPost = new HttpPost(url);
           // HttpResponse httpResponse = httpClient.execute(httpPost);
            //HttpEntity httpEntity = httpResponse.getEntity();
            //is = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            connection.disconnect();
            json = sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }
        // return JSON String
        return jObj;
    }


}
