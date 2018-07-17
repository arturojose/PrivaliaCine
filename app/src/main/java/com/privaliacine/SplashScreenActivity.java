package com.privaliacine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.privaliacine.models.Movies;
import com.privaliacine.models.Results;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "RESULT: ";
    private static final String URL_STATIC_IMG = "https://image.tmdb.org/t/p/w500";
    private ArrayList<String> arrayListImage = new ArrayList<>();
    private ArrayList<String> arrayListTitle = new ArrayList<>();
    private ArrayList<String> arrayListReleaseDate = new ArrayList<>();
    private ArrayList<String> arrayListOverView = new ArrayList<>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        //ya preparamos el intent para el cambio de clase que va ocurrir en definitivo dentro del metodo Retrofit
        intent = new Intent(this, MoviesListControllerActivity.class);

        //llama metodo para consumir el servidor usando la libreria retrofit
        ConnectServerRetrofit();
    }

    public void ConnectServerRetrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceMoviesList.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceMoviesList serviceMoviesList = retrofit.create(ServiceMoviesList.class);
        Call<Movies> call = serviceMoviesList.listResults(ServiceMoviesList.API_KEY, ServiceMoviesList.CONTENT_TYPE, ServiceMoviesList.LANGUAGE);

        call.enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG", "Error" + response.code());
                } else {
                    Log.i("TAG", "Connected" + response.code());
                    Movies movies = response.body();

                    for (Results results : movies.getResults()) {
                        arrayListImage.add(URL_STATIC_IMG + results.getBackdrop_path());
                        arrayListTitle.add(results.getTitle());
                        arrayListReleaseDate.add(ConvertDate(results.getRelease_date()));
                        arrayListOverView.add(results.getOverview());
                    }
                    //converte el listArray para Array (definimos la variable y el tamaño del Array)
                    String[] imageURL = new String[arrayListImage.size()];
                    String[] title = new String[arrayListTitle.size()];
                    String[] releaseDate = new String[arrayListReleaseDate.size()];
                    String[] overView= new String[arrayListOverView.size()];

                    //pasa el parametro Array por intent y inicializa otra activity (MoviesListControllerActivity
                    intent.putExtra("stringArrayImage", arrayListImage.toArray(imageURL));
                    intent.putExtra("stringArrayTitle", arrayListTitle.toArray(title));
                    intent.putExtra("stringArrayReleaseDate", arrayListReleaseDate.toArray(releaseDate));
                    intent.putExtra("stringArrayOverView", arrayListOverView.toArray(overView));

                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    public String ConvertDate(Date dateRelease){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateRelease);

        return String.valueOf(calendar.get(Calendar.YEAR));
    }
}
