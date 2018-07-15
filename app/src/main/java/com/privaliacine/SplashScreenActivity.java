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
    ArrayList<String> stringArrayList = new ArrayList<String>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        Retrofit();

        intent = new Intent(this, MoviesListControllerActivity.class);
    }

    public void Retrofit() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceMoviesList.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceMoviesList serviceMoviesList = retrofit.create(ServiceMoviesList.class);
        Call<Movies> requestResults = serviceMoviesList.listResults(ServiceMoviesList.API_KEY, ServiceMoviesList.CONTENT_TYPE);

        requestResults.enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG", "Error" + response.code());
                } else {
                    Log.i("TAG", "Connected" + response.code());
                    Movies movies = response.body();
                    for (Results results : movies.results) {
                       // stringArrayList.add(results.backdrop_path);
                       // stringArrayList.add(results.title);
                        //llama metodo que vuelve una String con el año de lanzamiento
                        stringArrayList.add(ConvertDate(results.release_date));
                      //  stringArrayList.add(results.overview);

                        Log.i(TAG, String.format("%s: %s", results.title, results.release_date));
                    }
                    //pasa el parametro del stringArray por intent y inicializa otra activity
                    intent.putExtra("stringArrayList", stringArrayList);
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
