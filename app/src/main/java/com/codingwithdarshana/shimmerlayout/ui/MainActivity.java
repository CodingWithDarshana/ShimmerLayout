package com.codingwithdarshana.shimmerlayout.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codingwithdarshana.shimmerlayout.R;
import com.codingwithdarshana.shimmerlayout.adapters.MostPopularVideoAdapter;
import com.codingwithdarshana.shimmerlayout.model.VideoList;
import com.codingwithdarshana.shimmerlayout.retrofit.APIClient;
import com.codingwithdarshana.shimmerlayout.retrofit.APIInterface;
import com.codingwithdarshana.shimmerlayout.utils.NetworkUtil;
import com.facebook.shimmer.ShimmerFrameLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ShimmerFrameLayout shimmerFrameLayout;
    MostPopularVideoAdapter mostPopularVideoAdapter;
    RecyclerView rec_mostPopularVideos;
    LinearLayout lnr_data_unavailable;
    TextView txt_retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        initViews();

        ShimmerFrameLayout shimmerFrameLayout =  findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.stopShimmerAnimation();
    }

    private void initViews() {
        rec_mostPopularVideos = findViewById(R.id.rec_mostPopularVideos);
        txt_retry = findViewById(R.id.txt_retry);
        lnr_data_unavailable = findViewById(R.id.lnr_data_unavailable);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext());
        rec_mostPopularVideos.setLayoutManager(gridLayoutManager);
        rec_mostPopularVideos.setItemAnimator(new DefaultItemAnimator());
        startFetching();
        txt_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFetching();
            }
        });

    }

    public void startFetching() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);

        shimmerFrameLayout.startShimmerAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (NetworkUtil.isNetworkConnected(MainActivity.this)) {
                    lnr_data_unavailable.setVisibility(View.GONE);
                    fetchMostPopularYoutubeVideos();

                } else {

                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    lnr_data_unavailable.setVisibility(View.VISIBLE);
                }


            }
        }, 2000);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void fetchMostPopularYoutubeVideos() {

        APIInterface apiService =
                APIClient.getClient().create(APIInterface.class);
        Call<VideoList> call = apiService.getAllVideos();
        call.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                if (response.isSuccessful()) {
                    VideoList videoList = response.body();
                    mostPopularVideoAdapter = new MostPopularVideoAdapter(videoList, getApplicationContext());
                    rec_mostPopularVideos.setAdapter(mostPopularVideoAdapter);
                    rec_mostPopularVideos.setVisibility(View.VISIBLE);

                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {
                    rec_mostPopularVideos.setVisibility(View.GONE);

                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);

                    Toast.makeText(MainActivity.this, "Something Went Wrong!..Please try again", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(View.GONE);
                rec_mostPopularVideos.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
