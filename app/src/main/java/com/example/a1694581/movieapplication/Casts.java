package com.example.a1694581.movieapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Casts extends AppCompatActivity implements CallBackMe{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casts);
        String url="";
        Intent getCastData=getIntent();
        if(getCastData!=null){
            url=getCastData.getStringExtra("actors");
            JsonRetriever.RetrieveFromURL(this,url,this);
        }else{
            Intent error=new Intent(this,Error.class);
            startActivity(error);
        }
    }
    @Override
    public void CallThis(String jsonText){
        try {
            JSONObject json = new JSONObject(jsonText);
            JSONArray jsonArray=json.getJSONArray("cast");
            int items=jsonArray.length();
            castFeed[] feeds=new castFeed[items];
            for(int x=0;x<items;x++){
                feeds[x]=new castFeed(jsonArray.getJSONObject(x).getInt("id"),"https://image.tmdb.org/t/p/w500"+jsonArray.getJSONObject(x).getString("profile_path"),
                        jsonArray.getJSONObject(x).getString("name"),jsonArray.getJSONObject(x).getString("character"));

            }
            ListView feedListView = (ListView)findViewById(R.id.Casts);
            castAdapter feedAdapter = new castAdapter(this,feeds);
            feedListView.setAdapter(feedAdapter);
            feedListView.setOnItemClickListener(    //Creating these on the fly.
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            castFeed myFeed = (castFeed)parent.getItemAtPosition(position);
                            gotClicked(myFeed);
                        }
                    }
            );
      } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void gotClicked(castFeed feed){
        Intent actor=new Intent(this,Person.class);
        actor.putExtra("actor","https://api.themoviedb.org/3/person/"+feed.id+"?api_key=4bae001f9927d0b39463403fe65ac713");
        startActivity(actor);
    }
    public void backToTop(View v){
        Intent top=new Intent(this,MainActivity.class);
        startActivity(top);
    }
}
