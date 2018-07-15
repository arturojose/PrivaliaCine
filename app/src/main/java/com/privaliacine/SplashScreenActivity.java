package com.privaliacine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.privaliacine.models.Movies;
import com.privaliacine.models.Results;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "RESULT: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit();

        Intent intent = new Intent(this, MoviesList.class);
        startActivity(intent);
        finish();
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
                        Log.i(TAG, String.format("%s: %s", results.title, results.release_date));
                    }
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }
}
