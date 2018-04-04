package com.example.a1694581.movieapplication;

/**
 * Created by 1694581 on 3/15/2018.
 */

public class listFeedSpecial {
    public String photo;
    public double rating;
    public String listTitle,date,overview,type;
    int id;
    public listFeedSpecial(int id,String type,String photo,String title,String date,double rating,String overview)
    {
        this.id=id;
        this.type=type;
        this.photo=photo;
        this.listTitle=title;
        this.date=date;
        this.rating=rating;
        this.overview=overview;
    }
}
