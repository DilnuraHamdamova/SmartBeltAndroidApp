package com.example.smartbelt;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

public class ExerciseActivity extends AppCompatActivity {

    private RecyclerView videoRecyclerView;

    private final int[] videoList = {
            R.raw.exercise1,
            R.raw.exercise2,
            R.raw.exercise3,
            R.raw.exercise4,
            R.raw.exercise5,
            R.raw.exercise6,
            R.raw.exercise7
    };

    private final String[] videoTitles = {
            "Exercise 1", "Exercise 2", "Exercise 3",
            "Exercise 4", "Exercise 5", "Exercise 6", "Exercise 7"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoRecyclerView.setAdapter(new VideoAdapter(videoList, videoTitles));
    }

    static class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

        private final int[] videos;
        private final String[] videoTitles;

        VideoAdapter(int[] videos, String[] videoTitles) {
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
            Uri videoUri = Uri.parse("android.resource://" +
                    holder.itemView.getContext().getPackageName() + "/" + videoResId);
            holder.videoView.setVideoURI(videoUri);

            MediaController mediaController = new MediaController(holder.itemView.getContext());
            mediaController.setAnchorView(holder.videoView);
            holder.videoView.setMediaController(mediaController);

            holder.videoView.seekTo(1); // preview holatida

            holder.videoTitle.setText(videoTitles[position]);
        }

        @Override
        public int getItemCount() {
            return videos.length;
        }

        static class VideoViewHolder extends RecyclerView.ViewHolder {
            VideoView videoView;
            TextView videoTitle;

            VideoViewHolder(@NonNull View itemView) {
                super(itemView);
                videoView = itemView.findViewById(R.id.videoView);
                videoTitle = itemView.findViewById(R.id.videoTitle);
            }
        }
    }
}
