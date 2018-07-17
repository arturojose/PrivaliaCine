package com.privaliacine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class ConnectionServer extends Activity{
    Context context;
    int numberPage;
    private static final String TAG = "RESULT: ";
    private static final String URL_STATIC_IMG = "https://image.tmdb.org/t/p/w500";
    private ArrayList<String> arrayListImage = new ArrayList<>();
    private ArrayList<String> arrayListTitle = new ArrayList<>();
    private ArrayList<String> arrayListReleaseDate = new ArrayList<>();
    private ArrayList<String> arrayListOverView = new ArrayList<>();

    public ConnectionServer(Context context, int numberPage){
        this.context = context;
        this.numberPage = numberPage;
        ConnectServerRetrofit(this.context, this.numberPage);
    }

    public void ConnectServerRetrofit(final Context context, int listNumber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceMoviesList.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceMoviesList serviceMoviesList = retrofit.create(ServiceMoviesList.class);
        Call<Movies> call = serviceMoviesList.listResults(listNumber, ServiceMoviesList.API_KEY, ServiceMoviesList.CONTENT_TYPE, ServiceMoviesList.LANGUAGE);

        call.enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG", "Error" + response.code());
                } else {
                    Log.i("TAG", "Connected" + response.code());
                    Movies movies = response.body();

                    int totalPages = movies.total_pages;

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

                    //preparamos el intent para el cambio de clase
                    Intent intent = new Intent(context, MoviesListControllerActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //pasa el parametro Array por intent y inicializa otra activity (MoviesListControllerActivity
                    intent.putExtra("intTotalPages", totalPages);
                    intent.putExtra("stringArrayImage", arrayListImage.toArray(imageURL));
                    intent.putExtra("stringArrayTitle", arrayListTitle.toArray(title));
                    intent.putExtra("stringArrayReleaseDate", arrayListReleaseDate.toArray(releaseDate));
                    intent.putExtra("stringArrayOverView", arrayListOverView.toArray(overView));

                    context.startActivity(intent);
                    //getApplicationContext().finish();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
            }
        });
    }

    private String ConvertDate(Date dateRelease){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateRelease);

        return String.valueOf(calendar.get(Calendar.YEAR));
    }

}
