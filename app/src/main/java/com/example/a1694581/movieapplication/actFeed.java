package com.example.a1694581.movieapplication;

/**
 * Created by 1694581 on 3/20/2018.
 */

public class actFeed {
    public String mediaStyle,mediaTitle,mediaChar;
    public int movieID;
    public actFeed(int id,String style,String title,String character){
        this.movieID=id;
        this.mediaStyle=style;
        this.mediaTitle=title;
        this.mediaChar=character;
    }
}
