package com.privaliacine;

import com.privaliacine.models.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceMoviesList {
    public static final String BASE_URL = "https://api.themoviedb.org/4/";
    public static final String API_KEY = "93aea0c77bc168d8bbce3918cefefa45";
    public static final String CONTENT_TYPE = "application/json;charset=utf-8";
    public static final String LANGUAGE = "es-ES";
    @GET("list/{listNumber}")
    Call<Movies> listResults(@Path("listNumber") int listNumber, @Query("api_key") String key, @Query("Content-Type") String contentType, @Query("language") String language);
}
