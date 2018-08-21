package br.com.fabricio.cursoudemyyoutube;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final String GOOGLE_API_KEY = "AIzaSyByjtW4OSh1-nHfFtl00xkrY0r7M9jBTFQ";
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.PlaybackEventListener playbackEventListener;
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {
                Toast.makeText(getApplicationContext(), "onLoading ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoaded(String s) {
                Toast.makeText(getApplicationContext(), "onLoaded ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdStarted() {
                Toast.makeText(getApplicationContext(), "onAdStarted ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoStarted() {
                Toast.makeText(getApplicationContext(), "onVideoStarted ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoEnded() {
                Toast.makeText(getApplicationContext(), "onVideoEnded ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {
                Toast.makeText(getApplicationContext(), "onError ", Toast.LENGTH_SHORT).show();
            }
        };

        playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {
                Toast.makeText(getApplicationContext(), "onPlaying ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPaused() {
                Toast.makeText(getApplicationContext(), "onPaused ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopped() {
                Toast.makeText(getApplicationContext(), "onStopped ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBuffering(boolean b) {
                Toast.makeText(getApplicationContext(), "onBuffering ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSeekTo(int i) {
                Toast.makeText(getApplicationContext(), "onSeekTo ", Toast.LENGTH_SHORT).show();
            }
        };

        youTubePlayerView = findViewById(R.id.viewYoutubePlayer);
        youTubePlayerView.initialize(GOOGLE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean foiRestaurado) {
//        Toast.makeText(getApplicationContext(), "Player Iniciado com Sucesso!", Toast.LENGTH_SHORT).show();

//        youTubePlayer.loadVideo("h8PQQvNn6aI"); //carrega automaticamente o vide
//        youTubePlayer.cueVideo("h8PQQvNn6aI"); //necessario que o usuario inicie o video
//        youTubePlayer.cuePlaylist("RDMMh8PQQvNn6aI"); //executa uma playlist

//        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);

        if (!foiRestaurado) {
            youTubePlayer.cuePlaylist("RDMMh8PQQvNn6aI");
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getApplicationContext(), "Erro ao Iniciar o Player! " + youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
    }
}
