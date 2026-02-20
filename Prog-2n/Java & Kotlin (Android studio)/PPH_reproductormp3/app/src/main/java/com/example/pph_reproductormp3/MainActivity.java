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
    int[] covers = {R.raw.AmanecíEnUnHotel, R.raw.Diarrhero, R.raw.OppyDay, R.raw.Serena};
    String[] songTitles = {"", ""};
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



    private void resetPlay(){
        mediaPlayer.release();
        mediaPlayer= MediaPlayer.create(this,songs[currentIndex]);
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        updateSeekBar();
    }

}