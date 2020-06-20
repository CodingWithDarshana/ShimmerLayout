package com.codingwithdarshana.shimmerlayout.retrofit;

import com.codingwithdarshana.shimmerlayout.model.VideoList;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.codingwithdarshana.shimmerlayout.retrofit.APIClient.API_KEY;


/**
 * Created by Darshana Chorat on 2020-05-25.
 */
public interface APIInterface {

    @GET("videos?part=snippet&chart=mostPopular&regionCode=IN&maxResults=10&key=" + API_KEY)
    Call<VideoList> getAllVideos();

}
