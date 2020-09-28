package com.example.minhaplaylist.helper;

import com.example.minhaplaylist.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(PlaylistConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



    }

}
