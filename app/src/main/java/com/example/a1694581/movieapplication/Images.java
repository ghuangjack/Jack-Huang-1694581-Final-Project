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

public class Images extends AppCompatActivity implements CallBackMe{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        String url="";
        Intent getImage=getIntent();
        if(getImage!=null){
            url=getImage.getStringExtra("imageURL");
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
            JSONArray jsonArray=json.getJSONArray("posters");
            int items=jsonArray.length();
            imageFeed[] feeds=new imageFeed[items];
            for(int x=0;x<items;x++){
                feeds[x]=new imageFeed("https://image.tmdb.org/t/p/original"+jsonArray.getJSONObject(x).getString("file_path"));
            }
            ListView feedListView = (ListView)findViewById(R.id.AllImages);
            imageAdapter feedAdapter = new imageAdapter(this,feeds);
            feedListView.setAdapter(feedAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void backP(View v){
        Intent top=new Intent(this,MainActivity.class);
        startActivity(top);
    }
}
