package com.example.minhaplaylist;

import android.os.Bundle;

import com.example.minhaplaylist.helper.PlaylistConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayActivity extends YouTubeBaseActivity
                        implements YouTubePlayer.OnInitializedListener {


    private YouTubePlayerView youTubePlayerView;
    private String idVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        youTubePlayerView = findViewById(R.id.playVideo);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            idVideo = bundle.getString("idVideo");
            youTubePlayerView.initialize(PlaylistConfig.CHAVE_PLAYLIST_API, this);

        }



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setFullscreen(true);
        youTubePlayer.setShowFullscreenButton(false);
        youTubePlayer.loadVideo(idVideo);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
