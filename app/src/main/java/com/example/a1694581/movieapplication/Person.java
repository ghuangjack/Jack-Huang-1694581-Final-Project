package com.example.a1694581.movieapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Person extends AppCompatActivity implements CallBackMe{
    TextView name=null,birthday=null,place=null,biography=null;
    ImageView photo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        String url="";
        Intent info=getIntent();
        if(info!=null){
            url=info.getStringExtra("actor");
            JsonRetriever.RetrieveFromURL(this,url,this);
        }else{
            Intent error=new Intent(this,Error.class);
            startActivity(error);
        }
        name=(TextView)findViewById(R.id.PersonName);
        photo=(ImageView)findViewById(R.id.PersonPic);
        birthday=(TextView)findViewById(R.id.birthday);
        place=(TextView)findViewById(R.id.PlaceBirth);
    }
    @Override
    public void CallThis(String jsonText){
        try {
            JSONObject json = new JSONObject(jsonText);
            name.setText(json.getString("name"));
            new DownloadImageTask(photo).execute("https://image.tmdb.org/t/p/w500"+json.getString("profile_path"));
            birthday.setText("Birthday:  "+json.getString("birthday"));
            place.setText("Place of Birth:  "+json.getString("place_of_birth"));
            JSONArray jsonArray=json.getJSONArray("cast");
            int items=jsonArray.length();
            actFeed[] feeds=new actFeed[items];
            for(int x=0;x<items;x++){
                feeds[x]=new actFeed(jsonArray.getJSONObject(x).getInt("id"),jsonArray.getJSONObject(x).getString("media_type")
                        ,jsonArray.getJSONObject(x).getString("title"),jsonArray.getJSONObject(x).getString("character"));
            }
            actAdapter feedAdapter = new actAdapter(this,feeds);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void gotClicked(actFeed feed){
        Intent movie=new Intent(this,Answer.class);
        if(feed.mediaStyle=="movie"){
            movie.putExtra("ansURL","https://api.themoviedb.org/3/movie/"+feed.movieID+"?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(movie);
        }else if(feed.mediaStyle=="tv"){
            movie.putExtra("ansURL","https://api.themoviedb.org/3/tv/"+feed.movieID+"?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(movie);
        }
    }
    public void main(View v){
        Intent top=new Intent(this,MainActivity.class);
        startActivity(top);
    }
}
