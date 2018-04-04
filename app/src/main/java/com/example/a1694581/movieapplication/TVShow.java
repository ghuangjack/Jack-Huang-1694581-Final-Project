package com.example.a1694581.movieapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TVShow extends AppCompatActivity implements CallBackMe{
    EditText search=null;
    TextView title=null;
    Button back=null,next=null;
    String style="popular";
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow);
        search=(EditText)findViewById(R.id.TVSearch);
        title=(TextView)findViewById(R.id.TVTitle);
        back=(Button)findViewById(R.id.TVBack);
        next=(Button)findViewById(R.id.TVNext);
        if(style=="popular"){
            title.setText("Popular TV Shows");
        }else if(style=="top_rated"){
            title.setText("Top Rated TV Shows");
        }else if(style=="on_the_air"){
            title.setText("Currently Airing TV Shows");
        }else if(style=="airing_today"){
            title.setText("TV Shows Airing Today");
        }
        String url="https://api.themoviedb.org/3/tv/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    @Override
    public void CallThis(String jsonText){
        try {
            JSONObject json = new JSONObject(jsonText);
            JSONArray jsonArray=json.getJSONArray("results");
            int items=jsonArray.length();
            listFeed[] feeds=new listFeed[items];
            for(int x=0;x<items;x++){
                feeds[x]=new listFeed(jsonArray.getJSONObject(x).getInt("id"),"https://image.tmdb.org/t/p/w500"+jsonArray.getJSONObject(x).getString("poster_path"),
                        jsonArray.getJSONObject(x).getString("name"),jsonArray.getJSONObject(x).getString("first_air_date"),
                        jsonArray.getJSONObject(x).getDouble("vote_average")*10,jsonArray.getJSONObject(x).getString("overview"));
            }
            ListView feedListView = (ListView)findViewById(R.id.TV);
            listAdapter feedAdapter = new listAdapter(this,feeds);
            feedListView.setAdapter(feedAdapter);

            feedListView.setOnItemClickListener(    //Creating these on the fly.
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listFeed myFeed = (listFeed)parent.getItemAtPosition(position);
                            gotClicked(myFeed);
                        }
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void gotClicked(listFeed feed)
    {
        Intent TVSearching=new Intent(this,Answer.class);
        TVSearching.putExtra("ansURL","https://api.themoviedb.org/3/tv/"+feed.id+"?api_key=4bae001f9927d0b39463403fe65ac713");
        startActivity(TVSearching);
    }
    public void TVBackAll(View v){
        Intent back=new Intent(this,MainActivity.class);
        startActivity(back);
    }
    public void TVP(View v){
        style="popular";
        String url="https://api.themoviedb.org/3/tv/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    public void TVTR(View v){
        style="top_rated";
        String url="https://api.themoviedb.org/3/tv/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    public void TVOT(View v){
        style="on_the_air";
        String url="https://api.themoviedb.org/3/tv/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    public void TVAT(View v){
        style="airing_today";
        String url="https://api.themoviedb.org/3/tv/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    public void TVSearch(View v){
        if(search.getText().toString()!=null){
            String searching=search.getText().toString();
            searching.replace(" ","+");
            boolean special=false;
            Intent goSearch=new Intent(this,SearchResult.class);
            goSearch.putExtra("find","https://api.themoviedb.org/3/search/tv?api_key=4bae001f9927d0b39463403fe65ac713&query="+searching+"&page=1");
            goSearch.putExtra("special",special);
            startActivity(goSearch);
        }

    }
    public void TVBackPage(View v){
        if(page>1){
            page=page-1;
            String url="https://api.themoviedb.org/3/tv/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
            JsonRetriever.RetrieveFromURL(this,url,this);
        }
    }
    public void TVNextPage(View v){
        if(page<1000){
            page=page+1;
            String url="https://api.themoviedb.org/3/tv/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
            JsonRetriever.RetrieveFromURL(this,url,this);
        }
    }
}
