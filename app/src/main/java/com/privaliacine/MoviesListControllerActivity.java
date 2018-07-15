package com.privaliacine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MoviesListControllerActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_movies_activity);
        listView = (ListView) findViewById(R.id.listViewMovies);

        ArrayList<String> listMovies = (ArrayList<String>) getIntent().getSerializableExtra("stringArrayList");

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listMovies);
        listView.setAdapter(stringArrayAdapter);
    }
}
