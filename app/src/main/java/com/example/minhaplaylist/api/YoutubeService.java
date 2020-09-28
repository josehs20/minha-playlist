package com.example.minhaplaylist.api;

import com.example.minhaplaylist.model.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

    @GET("search")
    Call<Resultado> recuperaVideos(
            @Query("part") String part,
            @Query("order") String order,
            @Query("maxResults") String maxResults ,
            @Query("key") String key,
            @Query("channelId") String channelId,
            @Query("q") String q

    );


}
