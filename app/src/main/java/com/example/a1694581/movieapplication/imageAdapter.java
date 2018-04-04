package com.example.a1694581.movieapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by 1694581 on 3/20/2018.
 */

public class imageAdapter extends ArrayAdapter<imageFeed> {
    public imageAdapter(Context context, imageFeed[] objects){
        super(context, R.layout.image, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customFeedView = myInflator.inflate(R.layout.image, parent, false);
        ImageView photo=(ImageView) customFeedView.findViewById(R.id.imagePhoto);
        new DownloadImageTask(photo).execute(getItem(position).photo);
        return customFeedView;
    }
}
