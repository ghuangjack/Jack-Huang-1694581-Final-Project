package com.example.a1694581.movieapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Error extends AppCompatActivity {
    EditText keyword;
    String style="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        Intent find=getIntent();
        if(find!=null){
            style=find.getStringExtra("style");
        }
    }
    public void backtoHome(View v){
        Intent switcher;
        if(style=="main"){
            switcher=new Intent(this,MainActivity.class);
            startActivity(switcher);
        }else if(style=="movie"){
            switcher=new Intent(this,Movies.class);
            startActivity(switcher);
        }else if(style=="tv"){
            switcher=new Intent(this,TVShow.class);
            startActivity(switcher);
        }else if(style=="person"){
            switcher=new Intent(this,People.class);
            startActivity(switcher);
        }
    }

}
