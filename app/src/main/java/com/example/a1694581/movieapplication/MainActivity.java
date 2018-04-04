package com.example.a1694581.movieapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    int page=1;
    int bestMovie=0,nextMovie=0,lastMovie=0,bestTV=0,nextTV=0,lastTV=0;
    Button newThing=null;
    Button movie=null;
    Button tv=null;
    Button people=null;
    EditText search=null;
    Button find=null;
    ImageView image1=null;
    ImageView image2=null;
    ImageView image3=null;
    ImageView image4=null;
    ImageView image5=null;
    ImageView image6=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movie=(Button) findViewById(R.id.MenuMovie);
        tv=(Button) findViewById(R.id.MenuTV);
        people=(Button) findViewById(R.id.MenuPeople);
        image1=(ImageView)findViewById(R.id.first);
        image2=(ImageView)findViewById(R.id.second);
        image3=(ImageView)findViewById(R.id.third);
        image4=(ImageView)findViewById(R.id.fourth);
        image5=(ImageView)findViewById(R.id.fifth);
        image6=(ImageView)findViewById(R.id.sixth);
        String urlTV = "https://api.themoviedb.org/3/tv/on_the_air?api_key=4bae001f9927d0b39463403fe65ac713&page=1";
        String urlMovie = "https://api.themoviedb.org/3/movie/now_playing?api_key=4bae001f9927d0b39463403fe65ac713&page=1";


        //For TV
        StringRequest requestTV = new StringRequest(urlTV, new Response.Listener<String>() {
            @Override
            public void onResponse(String ss) {
                RetrievedTvJson(ss);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Throw some error here.

            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(this);
        rQueue.add(requestTV);


        StringRequest requestMovie = new StringRequest(urlMovie, new Response.Listener<String>() {
            @Override
            public void onResponse(String ss) {
                RetrievedMovieJson(ss);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //Throw some error here.

            }
        });

        rQueue.add(requestMovie);



    }

    public void RetrievedTvJson (String jsonText)
    {
        try{
            JSONObject json=new JSONObject(jsonText);
            JSONArray item=json.getJSONArray("results");
            JSONObject best = item.getJSONObject(0);
            String bestPic="https://image.tmdb.org/t/p/w500"+best.getString("backdrop_path");
            bestTV=best.getInt("id");
            JSONObject next = item.getJSONObject(1);
            String nextPic="https://image.tmdb.org/t/p/w500"+next.getString("poster_path");
            nextTV=next.getInt("id");
            JSONObject last = item.getJSONObject(2);
            String lastPic="https://image.tmdb.org/t/p/w500"+last.getString("poster_path");
            lastTV=last.getInt("id");
            new DownloadImageTask(image2).execute(bestPic);
            new DownloadImageTask(image5).execute(nextPic);
            new DownloadImageTask(image6).execute(lastPic);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void RetrievedMovieJson (String jsonText)
    {
        try{
            JSONObject json=new JSONObject(jsonText);
            JSONArray item=json.getJSONArray("results");
            JSONObject best = item.getJSONObject(0);
            String bestPic="https://image.tmdb.org/t/p/w500"+best.getString("backdrop_path");
            bestMovie=best.getInt("id");
            JSONObject next = item.getJSONObject(1);
            String nextPic="https://image.tmdb.org/t/p/w500"+next.getString("poster_path");
            nextMovie=next.getInt("id");
            JSONObject last = item.getJSONObject(2);
            String lastPic="https://image.tmdb.org/t/p/w500"+last.getString("poster_path");
            lastMovie=last.getInt("id");
            new DownloadImageTask(image1).execute(bestPic);
            new DownloadImageTask(image3).execute(nextPic);
            new DownloadImageTask(image4).execute(lastPic);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void movie(View v){
        Intent movie=new Intent(this,Movies.class);
        startActivity(movie);
    }
    public void TVShow(View v){
        Intent tv=new Intent(this,TVShow.class);
        startActivity(tv);
    }
    public void people(View v){
        Intent cast=new Intent(this,People.class);
        startActivity(cast);
    }

    public void nowPlaying(View v){
        String newURL="https://api.themoviedb.org/3/movie/now_playing?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        Intent go=new Intent(this,Movies.class);
        go.putExtra("if",newURL);
        go.putExtra("page",page);
    }
    public void airing(View v){
        String newURL="https://api.themoviedb.org/3/tv/on_the_air?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        Intent go=new Intent(this,TVShow.class);
        go.putExtra("if",newURL);
        go.putExtra("page",page);
    }
    public void bestMovie(View v){
        String newURL="https://api.themoviedb.org/3/movie/" +bestMovie+"?api_key=4bae001f9927d0b39463403fe65ac713";
        Intent go=new Intent(this,Answer.class);
        go.putExtra("key",newURL);
    }
    public void nextMovie(View v){
        String newURL="https://api.themoviedb.org/3/movie/" +nextMovie+"?api_key=4bae001f9927d0b39463403fe65ac713";
        Intent go=new Intent(this,Answer.class);
        go.putExtra("key",newURL);
    }
    public void lastMovie(View v){
        String newURL="https://api.themoviedb.org/3/movie/" +lastMovie+"?api_key=4bae001f9927d0b39463403fe65ac713";
        Intent go=new Intent(this,Answer.class);
        go.putExtra("key",newURL);
    }
    public void bestTV(View v){
        String newURL="https://api.themoviedb.org/3/tv/" +bestTV+"?api_key=4bae001f9927d0b39463403fe65ac713";
        Intent go=new Intent(this,Answer.class);
        go.putExtra("key",newURL);
    }
    public void nextTV(View v){
        String newURL="https://api.themoviedb.org/3/tv/" +nextTV+"?api_key=4bae001f9927d0b39463403fe65ac713";
        Intent go=new Intent(this,Answer.class);
        go.putExtra("key",newURL);
    }
    public void lastTV(View v){
        String newURL="https://api.themoviedb.org/3/tv/" +lastTV+"?api_key=4bae001f9927d0b39463403fe65ac713";
        Intent go=new Intent(this,Answer.class);
        go.putExtra("key",newURL);
    }
}
