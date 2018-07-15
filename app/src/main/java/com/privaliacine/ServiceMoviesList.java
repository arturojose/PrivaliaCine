package com.privaliacine;

import com.privaliacine.models.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceMoviesList {
    public static final String BASE_URL = "https://api.themoviedb.org/4/";
    public static final String API_KEY = "93aea0c77bc168d8bbce3918cefefa45";
    public static final String CONTENT_TYPE = "application/json;charset=utf-8";
    @GET("list/1")
    Call<Movies> listResults(@Query("api_key") String key, @Query("Content-Type") String contentType);
}
