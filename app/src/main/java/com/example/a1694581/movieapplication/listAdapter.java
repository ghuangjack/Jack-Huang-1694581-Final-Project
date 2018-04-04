package com.example.a1694581.movieapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by 1694581 on 3/8/2018.
 */

public class listAdapter extends ArrayAdapter<listFeed> {
    public listAdapter(Context context, listFeed[] objects) {
        super(context, R.layout.list, objects);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflator = LayoutInflater.from(getContext());

        View customFeedView = myInflator.inflate(R.layout.list, parent, false);

        ImageView photo=(ImageView) customFeedView.findViewById(R.id.photoPic);
        TextView name=(TextView)customFeedView.findViewById(R.id.listName);
        TextView date=(TextView)customFeedView.findViewById(R.id.listDate);
        TextView rate=(TextView)customFeedView.findViewById(R.id.listRate);
        TextView context=(TextView) customFeedView.findViewById(R.id.listContent);

        new DownloadImageTask(photo).execute(getItem(position).photo);
        //Get our values
        String title=getItem(position).listTitle;
        String dateStr=getItem(position).date;
        double ratingDouble=getItem(position).rating;
        String contents=getItem(position).overview;
        //We have access to message and picture.
        //Set our values
        name.setText(title);
        date.setText(dateStr);
        rate.setText(String.valueOf(ratingDouble)+"%");
        context.setText(contents);
        return customFeedView;  //Sending the view back, in this case as a row.
    }
}
