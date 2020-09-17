package com.example.networkapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class FetchData extends AsyncTaskLoader<int[]> {
    private String url;
    public FetchData(@NonNull Context context,String url) {
        super(context);
        this.url=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public int[] loadInBackground() {
        if(url==null)
            return null;
        return  Fetch.doInBackground(url);
    }
}
