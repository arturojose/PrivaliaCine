package com.privaliacine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class MoviesListControllerActivity extends AppCompatActivity {
    private Context context = MoviesListControllerActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_movies);

        //customizar action bar/ llama el Layout actionbar_custom.xml
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_custom);

        //recibe el String Array pasado por la clase SplashScreenActivity
        String[] imageURL = getIntent().getStringArrayExtra("stringArrayImage");
        String[] title = getIntent().getStringArrayExtra("stringArrayTitle");
        String[] releaseDate = getIntent().getStringArrayExtra("stringArrayReleaseDate");
        String[] overView = getIntent().getStringArrayExtra("stringArrayOverView");

        //creamos un Adapter custmizado para formar la actitivy con los datos
        SimpleAdapterData simpleAdapterData = new SimpleAdapterData(context, imageURL, title, releaseDate, overView);

        //definimos la variable y el adapter customizado que instanciamos arriba
        ListView listView = (ListView) findViewById(R.id.listViewMovies);
        listView.setAdapter(simpleAdapterData);
    }
}
