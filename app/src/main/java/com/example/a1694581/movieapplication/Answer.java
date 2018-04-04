package com.example.a1694581.movieapplication;

import android.content.Intent;
import android.support.annotation.TransitionRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Answer extends AppCompatActivity  implements CallBackMe {
    TextView ansTitle = null, date = null, ansRate = null, ansType = null, ansSeason = null, ansOverview = null;
    ImageView photo = null;
    String style = "";
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ansTitle = (TextView) findViewById(R.id.MovieTitle);
        photo = (ImageView) findViewById(R.id.MovieImage);
        date = (TextView) findViewById(R.id.ansDate);
        ansRate = (TextView) findViewById(R.id.Rating);
        ansType = (TextView) findViewById(R.id.ansType);
        ansSeason = (TextView) findViewById(R.id.ansSeason);
        ansOverview = (TextView) findViewById(R.id.ansOverview);
        String url = "";
        Intent getInfo = getIntent();
        if (getInfo.getStringExtra("ansURL") != null) {
            url = getInfo.getStringExtra("ansURL");
            if (url.contains("3/movie")) {
                style = "movie";
                JsonRetriever.RetrieveFromURL(this, url, this);
            } else if (url.contains("3/tv")) {
                style = "tv";
                JsonRetriever.RetrieveFromURL(this, url, this);
            } else {
                Intent error = new Intent(this, Error.class);
                startActivity(error);
            }
        } else {
            Intent error = new Intent(this, Error.class);
            startActivity(error);
        }
    }

    @Override
    public void CallThis(String jsonText) {
        try {
            JSONObject json = new JSONObject(jsonText);
            if (style == "movie") {
                ansTitle.setText(json.getString(("title")));
                date.setText("Release Date: "+json.getString("release_date"));
                int rate=json.getInt("vote_average")*10;
                ansRate.setText("Rating:  "+rate+"%");
                JSONArray jsonArray=json.getJSONArray("genres");
                String types="";
                for(int x=0;x<jsonArray.length();x++){
                    types+=jsonArray.getJSONObject(x).getString("name")+",";
                    if(x==jsonArray.length()-1){
                        types+=jsonArray.getJSONObject(x).getString("name");
                    }
                }
                ansType.setText("Type:\n"+types);
                ansSeason.setVisibility(View.GONE);
                String descr=json.getString("overview");
                ansOverview.setText("Overview:\n"+descr);
            } else if (style == "tv") {
                ansTitle.setText(json.getString(("name")));
                date.setText("First Air Date: "+json.getString("first_air_date"));
                int rate=json.getInt("vote_average")*10;
                ansRate.setText("Rating:  "+rate+"%");
                JSONArray jsonArray=json.getJSONArray("genres");
                String types="";
                for(int x=0;x<jsonArray.length();x++){
                    types+=jsonArray.getJSONObject(x).getString("name")+",";
                    if(x==jsonArray.length()-1){
                        types+=jsonArray.getJSONObject(x).getString("name");
                    }
                }
                ansType.setText("Type:\n"+types);
                ansSeason.setVisibility(View.VISIBLE);
                int season=json.getInt("number_of_seasons");
                ansSeason.setText("Seasons: "+season);
                String descr=json.getString("overview");
                ansOverview.setText("Overview:\n"+descr);
            }
            new DownloadImageTask(photo).execute("https://image.tmdb.org/t/p/w500/" + json.getString("backdrop_path"));
            id = json.getInt("id");
            /*
            login.setText(json.getString("login"));


            new DownloadImageTask(avatar)
                    .execute(json.getString("avatar_url"));
                    */
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void ansMainPage(View v) {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    public void images(View v) {
        Intent imageShow = new Intent(this, Images.class);
        if (style == "movie") {
            imageShow.putExtra("imageURL", "https://api.themoviedb.org/3/movie/" + id + "/images?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(imageShow);
        } else if (style == "tv") {
            imageShow.putExtra("imageURL", "https://api.themoviedb.org/3/tv/" + id + "/images?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(imageShow);
        }
    }


    public void cast(View v){
        Intent actors=new Intent(this,Casts.class);
        if (style == "movie") {
            actors.putExtra("actors","https://api.themoviedb.org/3/movie/"+id+"/credits?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(actors);
        } else if (style == "tv") {
            actors.putExtra("actors","https://api.themoviedb.org/3/tv/"+id+"/credits?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(actors);
        }
    }
}
