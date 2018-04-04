package com.example.a1694581.movieapplication;

import java.util.ArrayList;

/**
 * Created by 1694581 on 3/7/2018.
 */

public class listFeed {
    public String photo;
    public double rating;
    public String listTitle,date,overview;
    int id;

    public listFeed(int id,String photo,String title,String date,double rating,String overview)
    {
        this.id=id;
        this.photo=photo;
        this.listTitle=title;
        this.date=date;
        this.rating=rating;
        this.overview=overview;
    }
}
