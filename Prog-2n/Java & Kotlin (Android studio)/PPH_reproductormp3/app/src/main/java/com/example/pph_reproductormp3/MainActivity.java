package com.example.pph_reproductormp3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView songTitle;
    ImageView imgCover;
    SeekBar seekBar;

    Button btnPrevious, btnPlay, btnPause, btnStop, btnNext;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();

    int[] songs = {};
    int[] covers = {R.raw.amanecienunhotel, R.raw.diarrhero, R.raw.oppyDay, R.raw.serena};
    String[] songTitles = {"gutalax diarrhero", "lil mabu oppy day", "orslok amaneci en un hotel", "2slimey serena"};
    int currentIndex=0;



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

        songTitle = findViewById(R.id.songTitle);
        imgCover = findViewById(R.id.imgCover);
        seekBar = findViewById(R.id.seekBar);
        btnPrevious = findViewById(R.id.btnPrev);
        btnPlay=findViewById(R.id.btnPlay);
        btnPause=findViewById(R.id.btnPause);
        btnStop=findViewById(R.id.btnStop);
        btnNext=findViewById(R.id.btnNext);
    }
    private void inicializePlayer(){
        mediaPlayer=MediaPlayer.create(this,songs[currentIndex]);
        songTitle.setText(songTitles[currentIndex]);
        imgCover.setImageResource(covers[currentIndex]);
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void updateSeekBar(){
        seekBar.setProgress(mediaPlayer)
    }
    private void configurarAnimacio() {
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(10000); // 10 segons per volta
        rotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    private void prepararCancó() {
        if (mediaPlayer != null) { mediaPlayer.release(); }
        mediaPlayer = MediaPlayer.create(this, songs[currentIndex]);
        mediaPlayer.setLooping(isLooping);
        mediaPlayer.setVolume(1.0f, 1.0f);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                PlaybackParams params = new PlaybackParams();
                params.setSpeed(1.0f);
                mediaPlayer.setPlaybackParams(params);
            } catch (Exception e) { e.printStackTrace(); }
        }

        ivCover.setImageResource(covers[currentIndex]);
        tvTitle.setText(titles[currentIndex]);
        tvArtist.setText(artists[currentIndex]);
        seekBar.setMax(mediaPlayer.getDuration());
        tvTotalTime.setText(formatarTemps(mediaPlayer.getDuration()));

        mediaPlayer.setOnCompletionListener(mp -> {
            if (!isLooping) btnNext.performClick();
        });
    }

    private void reproduirAra() {
        mediaPlayer.start();
        ivCover.startAnimation(rotateAnimation); // El disc comença a girar
        btnPlayPause.setImageResource(android.R.drawable.ic_media_pause);
        actualitzarSeekBar();
    }

    private void canviarCancó(int delta) {
        currentIndex = (currentIndex + delta + songs.length) % songs.length;
        prepararCancó();
        reproduirAra();
    }

    private void actualitzarSeekBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            tvCurrentTime.setText(formatarTemps(mediaPlayer.getCurrentPosition()));
            handler.postDelayed(this::actualitzarSeekBar, 1000);
        }
    }

    private String formatarTemps(int ms) {
        return String.format(Locale.getDefault(), "%02d:%02d", (ms/1000)/60, (ms/1000)%60);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.release();
    }