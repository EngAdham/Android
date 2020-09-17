package com.example.networkapp;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Fetch {
    public static String getHttpConnection(URL url) {
        String outputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            outputStream = getStream(httpURLConnection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return outputStream;
    }

    public static String getStream(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = null;
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
        return stringBuilder.toString();
    }

    public static int[] extractJson(String json) {
        int[] data = new int[10];
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray features = jsonObject.getJSONArray("features");
            for (int i = 0; i < 10; i++) {
                JSONObject current = features.getJSONObject(i);
                JSONObject properties = current.getJSONObject("properties");
                data[i] = properties.getInt("mag");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static int[] doInBackground(String url) {
        String output = null;
        output = getHttpConnection(getUrl(url));
        return extractJson(output);
    }

    public static URL getUrl(String url) {
        URL murl = null;
        try {
            murl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return murl;
    }
}
