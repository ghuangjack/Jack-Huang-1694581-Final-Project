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

public class People extends AppCompatActivity implements CallBackMe{
    EditText search=null;
    TextView title=null;
    Button back=null;
    Button next=null;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        search=(EditText)findViewById(R.id.PeopleSearch);
        title=(TextView)findViewById(R.id.PeopleTitle);
        back=(Button)findViewById(R.id.peopleBackPage);
        next=(Button)findViewById(R.id.peopleNextPage);
        String url="https://api.themoviedb.org/3/person/popular?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);

    }
    @Override
    public void CallThis(String jsonText){
        try {
            JSONObject json=new JSONObject(jsonText);
            JSONArray jsonArray=json.getJSONArray("results");
            int items=jsonArray.length();
            personFeed[] feeds=new personFeed[items];
            for(int x=0;x<items;x++){
                feeds[x]=new personFeed(jsonArray.getJSONObject(x).getInt("id"),
                 "https://image.tmdb.org/t/p/w500"+jsonArray.getJSONObject(x).getString("profile_path"),
                 jsonArray.getJSONObject(x).getString("name"));
            }
            ListView feedListView = (ListView)findViewById(R.id.People);
            personAdapter feedAdapter = new personAdapter(this,feeds);
            feedListView.setAdapter(feedAdapter);

            feedListView.setOnItemClickListener(    //Creating these on the fly.
                    new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            personFeed myFeed = (personFeed) parent.getItemAtPosition(position);
                            gotClicked(myFeed);
                        }
                    }
            );
            if(jsonArray.length()==0){
                Intent error=new Intent(this,Error.class);
                startActivity(error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void gotClicked(personFeed feed){
        Intent cast=new Intent(this,Person.class);
        cast.putExtra("actor","https://api.themoviedb.org/3/person/"+feed.personID+"?api_key=4bae001f9927d0b39463403fe65ac713");
        startActivity(cast);
    }
    public void PersonBackPage(View v){
        Intent back=new Intent(this,MainActivity.class);
        startActivity(back);
    }
    public void PersonSearch(View v){
        if(search.getText().toString()!=null){
            String keyword=search.getText().toString();
            keyword.replace(" ","+");
            boolean special=false;
            Intent goSearch=new Intent(this,SearchResult.class);
            goSearch.putExtra("find","https://api.themoviedb.org/3/search/person?api_key=4bae001f9927d0b39463403fe65ac713&query="+keyword+"&page=1");
            goSearch.putExtra("special",special);
            startActivity(goSearch);
        }

    }

    public void peopleBack(View v){
        if(page>1){
            page=page-1;
            String url="https://api.themoviedb.org/3/person/popular?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
            JsonRetriever.RetrieveFromURL(this,url,this);
        }
    }
    public void peopleNext(View v){
        page=page+1;
        String url="https://api.themoviedb.org/3/person/popular?api_key=4bae001f9927d0b39463403fe65ac713&page="+page;
        JsonRetriever.RetrieveFromURL(this,url,this);
    }
}
