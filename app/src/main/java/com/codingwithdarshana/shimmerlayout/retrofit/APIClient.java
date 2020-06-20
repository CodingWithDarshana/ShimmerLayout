package com.codingwithdarshana.shimmerlayout.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Darshana Chorat on 2020-05-25.
 */
public class APIClient {
    public static final String BASE_URL= "https://www.googleapis.com/youtube/v3/";
    public static final String API_KEY = "YOUR_API_KEY";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
