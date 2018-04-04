package com.example.a1694581.movieapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchResult extends AppCompatActivity implements CallBackMe{
    EditText keyword=null;
    Button back=null,next=null;
    int page=1,maxPage=1000;
    String url="",style="";
    boolean special=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        keyword=(EditText)findViewById(R.id.SearchBox);
        back=(Button)findViewById(R.id.SearchBack);
        next=(Button)findViewById(R.id.SearchNext);
        Intent reason=getIntent();
        if(reason!=null){
            url=reason.getStringExtra("find");
            special=reason.getBooleanExtra("special",false);

                if(url.contains("search/multi")){
                    style="main";
                }else if(url.contains("search/movie")){
                    style="movie";
                }else if(url.contains("search/tv")){
                    style="tv";
                }else if(url.contains("search/person")){
                    style="person";
                }

            JsonRetriever.RetrieveFromURL(this,url,this);
        }else{
            Intent error=new Intent(this,Error.class);
            error.putExtra("style",style);
            startActivity(error);
        }
    }
    @Override
    public void CallThis(String jsonText){
        try {
            JSONObject json = new JSONObject(jsonText);
            maxPage=json.getInt("total_pages");
            if(style=="person"){
                JSONArray jsonArray=json.getJSONArray("results");
                int items=jsonArray.length();
                personFeed[] feeds=new personFeed[items];
                for(int x=0;x<items;x++){
                    feeds[x]=new personFeed(jsonArray.getJSONObject(x).getInt("id"),
                            "https://image.tmdb.org/t/p/w500"+jsonArray.getJSONObject(x).getString("profile_path"),
                            jsonArray.getJSONObject(x).getString("name"));
                }
                ListView feedListView = (ListView)findViewById(R.id.SearchResult);
                personAdapter feedAdapter = new personAdapter(this,feeds);
                feedListView.setAdapter(feedAdapter);

                feedListView.setOnItemClickListener(    //Creating these on the fly.
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                personFeed myFeed = (personFeed) parent.getItemAtPosition(position);
                                personGotClicked(myFeed);
                            }
                        }
                );
            }else if(style=="movie"){
                JSONArray jsonArray=json.getJSONArray("results");
                int items=jsonArray.length();
                listFeed[] feeds=new listFeed[items];
                for(int x=0;x<items;x++){
                    feeds[x]=new listFeed(jsonArray.getJSONObject(x).getInt("id"),"https://image.tmdb.org/t/p/w500"+jsonArray.getJSONObject(x).getString("poster_path"),
                            jsonArray.getJSONObject(x).getString("original_title"),jsonArray.getJSONObject(x).getString("release_date"),
                            jsonArray.getJSONObject(x).getDouble("vote_average")*10,jsonArray.getJSONObject(x).getString("overview"));
                }
                ListView feedListView = (ListView)findViewById(R.id.SearchResult);
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
            }else if(style=="tv"){
                JSONArray jsonArray=json.getJSONArray("results");
                int items=jsonArray.length();
                listFeed[] feeds=new listFeed[items];
                for(int x=0;x<items;x++){
                    feeds[x]=new listFeed(jsonArray.getJSONObject(x).getInt("id"),"https://image.tmdb.org/t/p/w500"+jsonArray.getJSONObject(x).getString("poster_path"),
                            jsonArray.getJSONObject(x).getString("name"),jsonArray.getJSONObject(x).getString("first_air_date"),
                            jsonArray.getJSONObject(x).getDouble("vote_average")*10,jsonArray.getJSONObject(x).getString("overview"));
                }
                ListView feedListView = (ListView)findViewById(R.id.SearchResult);
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void personGotClicked(personFeed feed){
        Intent cast=new Intent(this,Person.class);
        cast.putExtra("actor","https://api.themoviedb.org/3/person/"+feed.personID+"?api_key=4bae001f9927d0b39463403fe65ac713");
        startActivity(cast);
    }
    public void gotClickedSpecial(listFeedSpecial feed){
        if(feed.type=="movie"){
            Intent movie=new Intent(this,Answer.class);
            movie.putExtra("ansURL","https://api.themoviedb.org/3/movie/"+feed.id+"?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(movie);
        }else if(feed.type=="tv"){
            Intent tv=new Intent(this,Answer.class);
            tv.putExtra("ansURL","https://api.themoviedb.org/3/tv/"+feed.id+"?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(tv);
        }else{
            Intent error=new Intent(this,Error.class);
            error.putExtra("style",style);
            startActivity(error);
        }
    }
    public void gotClicked(listFeed feed){
        if(style=="movie"){
            Intent movie=new Intent(this,Answer.class);
            movie.putExtra("ansURL","https://api.themoviedb.org/3/movie/"+feed.id+"?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(movie);
        }else if(style=="tv"){
            Intent tv=new Intent(this,Answer.class);
            tv.putExtra("ansURL","https://api.themoviedb.org/3/tv/"+feed.id+"?api_key=4bae001f9927d0b39463403fe65ac713");
            startActivity(tv);
        }else{
            Intent error=new Intent(this,Error.class);
            error.putExtra("style",style);
            startActivity(error);
        }
    }
    public void goBack(View v){
        Intent back;
        if(style=="main"){
            back=new Intent(this,MainActivity.class);
            startActivity(back);
        }else if(style=="movie"){
            back=new Intent(this,Movies.class);
            startActivity(back);
        }else if(style=="tv"){
            back=new Intent(this,TVShow.class);
            startActivity(back);
        }else if(style=="person"){
            back=new Intent(this,People.class);
            startActivity(back);
        }
    }
    public void searchAgain(View v){
        if(keyword.getText().toString()!=null){
            String word=keyword.getText().toString();
            word.replace(" ","+");
            if(style=="movie"){
                url="https://api.themoviedb.org/3/search/movie?api_key=4bae001f9927d0b39463403fe65ac713&query="+word+"&page=1";
                JsonRetriever.RetrieveFromURL(this,url,this);
            }else if(style=="tv"){
                url="https://api.themoviedb.org/3/search/tv?api_key=4bae001f9927d0b39463403fe65ac713&query="+word+"&page=1";
                JsonRetriever.RetrieveFromURL(this,url,this);
            }else if(style=="person"){
                url="https://api.themoviedb.org/3/search/person?api_key=4bae001f9927d0b39463403fe65ac713&query="+word+"&page=1";
                JsonRetriever.RetrieveFromURL(this,url,this);
            }
        }

    }
    public void backPage(View v){
        if(page>1){
            page=page-1;

        }
    }
}
