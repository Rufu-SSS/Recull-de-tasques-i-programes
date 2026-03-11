package com.example.pph_reproductormp3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView songTitle, tvArtist, tvCurrentTime, tvTotalTime;
    ImageView imgCover;
    SeekBar seekBar;
    ImageButton btnPrevious, btnPlay, btnPause, btnStop, btnNext;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    int[] songs = {R.raw.orslokamanecienunhotel, R.raw.gutalaxdiarrhero, R.raw.lilmabuoppyday, R.raw.serena2slimey};
    int[] covers = {R.raw.amanecienunhotel, R.raw.diarrhero, R.drawable.oppyday, R.raw.serena};
    String[] songTitles = {"Amaneci en un hotel", "Diarrhero", "Oppy Day", "Serena"};
    String[] artists = {"Orslok", "Gutalax", "Lil Mabu", "2Slimey"};

    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        songTitle     = findViewById(R.id.songTitle);
        tvArtist      = findViewById(R.id.tvArtist);
        imgCover      = findViewById(R.id.imgCover);
        seekBar       = findViewById(R.id.seekBar);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvTotalTime   = findViewById(R.id.tvTotalTime);
        btnPrevious   = findViewById(R.id.btnPrev);
        btnPlay       = findViewById(R.id.btnPlay);
        btnPause      = findViewById(R.id.btnPause);
        btnStop       = findViewById(R.id.btnStop);
        btnNext       = findViewById(R.id.btnNext);

        inicialitzarPlayer();
        configurarListeners();
    }

    private void inicialitzarPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, songs[currentIndex]);
        songTitle.setText(songTitles[currentIndex]);
        tvArtist.setText(artists[currentIndex]);
        imgCover.setImageResource(covers[currentIndex]);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(0);
        tvCurrentTime.setText(formatarTemps(0));
        tvTotalTime.setText(formatarTemps(mediaPlayer.getDuration()));

        mediaPlayer.setOnCompletionListener(mp -> canviarCanco(1));
    }

    private void configurarListeners() {
        btnPlay.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                actualitzarSeekBar();
            }
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                handler.removeCallbacksAndMessages(null);
            }
        });

        btnStop.setOnClickListener(v -> {
            mediaPlayer.stop();
            handler.removeCallbacksAndMessages(null);
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(this, songs[currentIndex]);
            seekBar.setProgress(0);
            tvCurrentTime.setText(formatarTemps(0));
        });

        btnNext.setOnClickListener(v -> canviarCanco(1));

        btnPrevious.setOnClickListener(v -> canviarCanco(-1));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    tvCurrentTime.setText(formatarTemps(progress));
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacksAndMessages(null);
            }
            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer.isPlaying()) actualitzarSeekBar();
            }
        });
    }

    private void canviarCanco(int delta) {
        handler.removeCallbacksAndMessages(null);
        mediaPlayer.stop();
        mediaPlayer.release();
        currentIndex = (currentIndex + delta + songs.length) % songs.length;
        inicialitzarPlayer();
        mediaPlayer.start();
        actualitzarSeekBar();
    }

    private void actualitzarSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        tvCurrentTime.setText(formatarTemps(mediaPlayer.getCurrentPosition()));
        handler.postDelayed(this::actualitzarSeekBar, 500);
    }

    private String formatarTemps(int ms) {
        return String.format(Locale.getDefault(), "%02d:%02d", (ms / 1000) / 60, (ms / 1000) % 60);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}