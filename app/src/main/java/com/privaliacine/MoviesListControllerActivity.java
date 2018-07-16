package com.privaliacine;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class MoviesListControllerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_movies_activity);
        //customizar action bar/ llama el Layout actionbar_custom.xml
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_custom);
        //recibe el ArrayList pasado por la clase SplashScreenActivity
        ArrayList<HashMap<String,String>> arrayListParameter = (ArrayList<HashMap<String,String>>) getIntent().getSerializableExtra("stringArrayList");

        //crea el Simple adapter para compor los datos de listview_movies_activity.xml con data_movies.xml
        SimpleAdapter simpleAdapter = new SimpleAdapter(this.getBaseContext(), arrayListParameter, R.layout.data_movies, new String[]{"image", "title", "releaseDate", "overView"}, new int[]{R.id.imgView, R.id.textViewTitle, R.id.textViewReleaseDate, R.id.textViewOverView});
        //crea el objeto del ListView en listview_movies_activity.xml
        ListView listViewItems = findViewById(R.id.listViewItems);
        listViewItems.setAdapter(simpleAdapter);
    }
}
