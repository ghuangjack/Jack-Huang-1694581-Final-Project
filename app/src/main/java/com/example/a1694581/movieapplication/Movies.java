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

public class Movies extends AppCompatActivity implements CallBackMe{
    EditText search=null;
    TextView title=null;
    Button back=null,next=null;
    String style="popular";
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        search=(EditText)findViewById(R.id.MovieSearch);
        title=(TextView)findViewById(R.id.MovieTitle);
        back=(Button)findViewById(R.id.MovieBack);
        next=(Button)findViewById(R.id.MovieNext);
        if(style=="popular"){
            title.setText("Popular Movies");
        }else if(style=="top_rated"){
            title.setText("Top Rated Movies");
        }else if(style=="upcoming"){
            title.setText("Upcoming Movies");
        }else if(style=="now_playing"){
            title.setText("Now Playing Movies");
        }
        String url="https://api.themoviedb.org/3/movie/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
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
                        jsonArray.getJSONObject(x).getString("title"),jsonArray.getJSONObject(x).getString("release_date"),
                        jsonArray.getJSONObject(x).getDouble("vote_average")*10,jsonArray.getJSONObject(x).getString("overview"));
            }
            ListView feedListView = (ListView)findViewById(R.id.Movies);
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
        Intent searchFor=new Intent(this,Answer.class);
        searchFor.putExtra("ansURL","https://api.themoviedb.org/3/movie/"+feed.id+"?api_key=4bae001f9927d0b39463403fe65ac713");
        startActivity(searchFor);
    }
    public void movieBack(View v){
        Intent back=new Intent(this,MainActivity.class);
        startActivity(back);
    }
    public void moviePopular(View v){
        style="popular";
        String url="https://api.themoviedb.org/3/movie/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    public void movieRate(View v){
        style="top_rated";
        String url="https://api.themoviedb.org/3/movie/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    public void movieUpComing(View v){
        style="upcoming";
        String url="https://api.themoviedb.org/3/movie/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    public void moviePlayed(View v){
        style="now_playing";
        String url="https://api.themoviedb.org/3/movie/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
    public void movieFind(View v){
        if(search.getText().toString()!=null){
            String searching=search.getText().toString();
            searching.replace(" ","+");
            boolean special=false;
            Intent goSearch=new Intent(this,SearchResult.class);
            goSearch.putExtra("find","https://api.themoviedb.org/3/search/movie?api_key=4bae001f9927d0b39463403fe65ac713&query="+searching+"&page=1");
            goSearch.putExtra("special",special);
            startActivity(goSearch);
        }

    }
    public void movieBackPage(View v){
        if(page>1){
            page=page-1;
            String url="https://api.themoviedb.org/3/movie/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
            JsonRetriever.RetrieveFromURL(this,url,this);
        }
    }
    public void movieNextPage(View v){
        if(page<1000){
            page=page+1;
            String url="https://api.themoviedb.org/3/movie/"+style+"?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
            JsonRetriever.RetrieveFromURL(this,url,this);
        }
    }
}
