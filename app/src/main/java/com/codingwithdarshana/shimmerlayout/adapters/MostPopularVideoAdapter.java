package com.codingwithdarshana.shimmerlayout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.codingwithdarshana.shimmerlayout.R;
import com.codingwithdarshana.shimmerlayout.model.VideoList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Darshana Chorat on 2020-05-27.
 */
public class MostPopularVideoAdapter extends RecyclerView.Adapter<MostPopularVideoAdapter.VideoGalleryHolder> {
    private VideoList videoList;

    public MostPopularVideoAdapter(VideoList videoList, Context context) {
        this.videoList = videoList;
    }


    class VideoGalleryHolder extends RecyclerView.ViewHolder {
        ImageView img_thumbnil;
        TextView txt_tags, txt_title, txt_channelName, txt_posted_date;


        VideoGalleryHolder(View itemView) {

            super(itemView);
            img_thumbnil = itemView.findViewById(R.id.img_thumbnil);
            txt_tags = itemView.findViewById(R.id.txt_tags);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_channelName = itemView.findViewById(R.id.txt_channelName);
            txt_posted_date = itemView.findViewById(R.id.txt_posted_date);


        }
    }

    @Override
    public VideoGalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.most_popular_video_adapter, parent, false);

        return new VideoGalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoGalleryHolder holder, final int position) {

        holder.txt_channelName.setText(videoList.getItems().get(position).getSnippet().getChannelTitle());
        holder.txt_title.setText(videoList.getItems().get(position).getSnippet().getTitle());
        holder.txt_posted_date.setText(videoList.getItems().get(position).getSnippet().getPublishedAt());
        List<String> tags = videoList.getItems().get(position).getSnippet().getTags();
        String allTags = "";
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                if (!tags.get(i).trim().equals("")) {
                    allTags = allTags + " #" + tags.get(i);

                }
            }
            holder.txt_tags.setText(allTags);
        }

        if (videoList.getItems().get(position).getSnippet().getThumbnails().getMaxres() != null) {
            String url = videoList.getItems().get(position).getSnippet().getThumbnails().getMaxres().getUrl();
            Picasso.get().load(url).into(holder.img_thumbnil);

        } else {
            String url = videoList.getItems().get(position).getSnippet().getThumbnails().getStandard().getUrl();
            Picasso.get().load(url).fit().into(holder.img_thumbnil);

        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
