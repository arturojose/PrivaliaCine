package com.privaliacine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.privaliacine.models.Movies;
import com.privaliacine.models.Results;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "RESULT: ";
    private static final String URL_STATIC_IMG = "https://image.tmdb.org/t/p/w500";
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
        Call<Movies> call = serviceMoviesList.listResults(ServiceMoviesList.API_KEY, ServiceMoviesList.CONTENT_TYPE, ServiceMoviesList.LANGUAGE);

        call.enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG", "Error" + response.code());
                } else {
                    Log.i("TAG", "Connected" + response.code());
                    Movies movies = response.body();

                    ArrayList<HashMap<String,String>> stringArraylist = new  ArrayList<>();
                    HashMap<String, String> hashMap;
                    for (Results results : movies.getResults()) {
                        //crea HashMap para compor los parametros de ListView
                        hashMap = new HashMap<>();
                        //parametros del HashMap
                        hashMap.put("image",URL_STATIC_IMG + results.getBackdrop_path());
                        hashMap.put("title", results.getTitle());
                        hashMap.put("releaseDate", ConvertDate(results.getRelease_date()));
                        hashMap.put("overView", results.getOverview());
                        //añadir el hashmap al list
                        stringArraylist.add(hashMap);
                    }

                    //pasa el parametro del stringArray por intent y inicializa otra activity
                    intent.putExtra("stringArrayList", stringArraylist);
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
    /*
    public String Glide(String url) {
        Glide.with(this)
            .load(url)
            .into(imageView);

    }*/
}
