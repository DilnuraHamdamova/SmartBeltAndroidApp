package com.example.smartbelt;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private final int[] videos;
    private final String[] videoTitles;
    public VideoAdapter(int[] videos, String[] videoTitles) {
        this.videos = videos;
        this.videoTitles = videoTitles;
    }
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        int videoResId = videos[position];
        Uri videoUri = Uri.parse("android.resource://"
                + holder.itemView.getContext().getPackageName()
                + "/" + videoResId);

        holder.videoView.setVideoURI(videoUri);


        MediaController mediaController = new MediaController(holder.itemView.getContext());
        mediaController.setAnchorView(holder.videoView);
        holder.videoView.setMediaController(mediaController);

        holder.videoView.seekTo(1); // preview uchun

        //har bir videoga alohida nom berish
        holder.videoTitle.setText(videoTitles[position]);
    }
    @Override
    public int getItemCount() {
        return videos.length;
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView videoTitle;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            videoTitle = itemView.findViewById(R.id.videoTitle);
        }
    }
}