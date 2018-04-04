package com.example.a1694581.movieapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 1694581 on 3/15/2018.
 */

public class personAdapter extends ArrayAdapter<personFeed> {
    public personAdapter(Context context, personFeed[] objects) {
        super(context, R.layout.peoples, objects);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());

        View customFeedView = myInflator.inflate(R.layout.peoples, parent, false);

        ImageView photo=(ImageView) customFeedView.findViewById(R.id.castPhoto);
        TextView name=(TextView)customFeedView.findViewById(R.id.castName);
        TextView content=(TextView)customFeedView.findViewById(R.id.castContent);

        new DownloadImageTask(photo).execute(getItem(position).photo);
        //Get our values
        int id=getItem(position).personID;
        String naming=getItem(position).castName;
        //We have access to message and picture.
        //Set our values
        name.setText(naming);
        return customFeedView;  //Sending the view back, in this case as a row.
    }
}
