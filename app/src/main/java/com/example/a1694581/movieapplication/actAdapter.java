package com.example.a1694581.movieapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by 1694581 on 3/20/2018.
 */

public class actAdapter extends ArrayAdapter<actFeed> {
    public actAdapter (Context context, actFeed[] objects){
        super(context, R.layout.cast, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater myInflator = LayoutInflater.from(getContext());
        View customFeedView = myInflator.inflate(R.layout.cast, parent, false);
        TextView media=(TextView)customFeedView.findViewById(R.id.actMedia);
        TextView title=(TextView)customFeedView.findViewById(R.id.actTitle);
        TextView character=(TextView)customFeedView.findViewById(R.id.actChar);
        String mediaType=getItem(position).mediaStyle,movieActed=getItem(position).mediaTitle,actedChar=getItem(position).mediaChar;
        if(mediaType=="movie"){
            media.setText("* Movie");
        }else if(mediaType=="tv"){
            media.setText("* TV");
        }

        title.setText(movieActed);
        character.setText("as "+actedChar);
        return customFeedView;
    }
}
