package com.example.a1694581.movieapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 1694581 on 3/20/2018.
 */

public class castAdapter extends ArrayAdapter<castFeed> {
    public castAdapter (Context context, castFeed[] objects){
        super(context, R.layout.cast_all, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customFeedView = myInflator.inflate(R.layout.cast_all, parent, false);
        ImageView photo=(ImageView) customFeedView.findViewById(R.id.castPhoto);
        TextView actor=(TextView)customFeedView.findViewById(R.id.castActor);
        TextView character=(TextView)customFeedView.findViewById(R.id.castChar);
        new DownloadImageTask(photo).execute(getItem(position).photo);
        String actorName=getItem(position).actorName,charName=getItem(position).charName;
        actor.setText(actorName);
        character.setText("as "+charName);
        return customFeedView;
    }
}
