package com.example.networkapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<int[]> {
    public static String URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        LoaderManager.getInstance(this).initLoader(1,null,this);
    }

    @NonNull
    @Override
    public Loader<int[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new FetchData(this, URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<int[]> loader, int[] data) {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(data[i]);
            stringBuilder.append("\n");
        }
        textView.setText(stringBuilder.toString());
    }

    @Override
    public void onLoaderReset(@NonNull Loader<int[]> loader) {

    }
}