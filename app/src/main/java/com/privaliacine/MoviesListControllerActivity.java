package com.privaliacine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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
        int totalPage = (Integer) getIntent().getIntExtra("intTotalPages", 1);
        String[] imageURL = getIntent().getStringArrayExtra("stringArrayImage");
        String[] title = getIntent().getStringArrayExtra("stringArrayTitle");
        String[] releaseDate = getIntent().getStringArrayExtra("stringArrayReleaseDate");
        String[] overView = getIntent().getStringArrayExtra("stringArrayOverView");

        //creamos un Adapter custmizado para formar la actitivy con los datos
        SimpleAdapterData simpleAdapterData = new SimpleAdapterData(context, imageURL, title, releaseDate, overView);

        //definimos la variable y el adapter customizado que instanciamos arriba
        ListView listView = findViewById(R.id.listViewMovies);
        listView.setAdapter(simpleAdapterData);

        /****************************************************************************/
        /*****busca las peliculas dinamicamente - No me ha dado tiempo**************/
        /*
        EditText editTextSearch = (EditText) findViewById(R.id.editTextSearchActionBar);
        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MoviesListControllerActivity.this.simpleAdapterData.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        /**************************************************************************************/
        //actualizar el listView con los datos de la pagina del servidor - No me ha dado tiempo
        /****************************************************************************************/
        /*
        listView.setOnScrollListener(new OnScrollFinishListener(context) {
            @Override
            protected void onScrollFinished() {
                // do whatever you need here!
            }
        });
        */
    }


}
