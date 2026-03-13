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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // ── Vistes de la interfície ──────────────────────────────────────────────
    TextView songTitle, tvArtist, tvCurrentTime, tvTotalTime;
    ImageView imgCover;
    SeekBar seekBar;
    ImageButton btnPrevious, btnPlay, btnPause, btnStop, btnNext;
    RecyclerView recyclerView;
    CancoAdapter adapter;

    // ── Reproductor i temporitzador ──────────────────────────────────────────
    MediaPlayer mediaPlayer;
    Handler handler = new Handler(); // Actualitza el SeekBar cada 500ms

    // ── Dades de les cançons ─────────────────────────────────────────────────
    int[] songs   = {R.raw.placebospecialk, R.raw.orslokamanecienunhotel, R.raw.gutalaxdiarrhero, R.raw.spiritboxbelcarra};
    int[] covers  = {R.drawable.specialk, R.drawable.amanecienunhotel, R.drawable.diarrhero, R.drawable.belcarra};
    String[] songTitles = {"Special K", "Amaneci en un hotel", "Diarrhero", "Belcarra"};
    String[] artists    = {"Placebo", "Orslok", "Gutalax", "Spiritbox"};
    int[] durades; // Durades precalculades en ms per evitar múltiples MediaPlayers actius

    int currentIndex = 0; // Índex de la cançó en reproducció

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajusta el padding perquè el contingut no quedi tapat per les barres del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ── Enllaça variables amb elements del layout XML ────────────────────
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
        recyclerView  = findViewById(R.id.recyclerView);

        // Precalcula les durades de totes les cançons i allibera cada MediaPlayer immediatament
        // Evita tenir múltiples instàncies actives alhora que causarien so fregit
        durades = new int[songs.length];
        for (int i = 0; i < songs.length; i++) {
            MediaPlayer mp = MediaPlayer.create(this, songs[i]);
            durades[i] = mp.getDuration();
            mp.release();
        }

        // ── Configura el RecyclerView amb la llista de cançons ───────────────
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CancoAdapter(this, covers, songTitles, artists, durades, currentIndex,
                index -> {
                    // Quan es clica una fila de la llista, canvia directament a aquella cançó
                    currentIndex = index;
                    canviarCanco(0);
                });
        recyclerView.setAdapter(adapter);

        inicialitzarPlayer();
        configurarListeners();
    }

    // Carrega la cançó actual al MediaPlayer i actualitza tota la interfície
    // Reutilitza el mateix objecte MediaPlayer amb reset() per evitar acumulació de recursos
    private void inicialitzarPlayer() {
        if (mediaPlayer == null) {
            // Primera vegada: crea el MediaPlayer des de zero
            mediaPlayer = MediaPlayer.create(this, songs[currentIndex]);
        } else {
            try {
                // Vegades posteriors: reutilitza el mateix objecte per evitar fuites de memòria
                mediaPlayer.reset();
                mediaPlayer.setDataSource(getResources().openRawResourceFd(songs[currentIndex]));
                mediaPlayer.prepare();
            } catch (Exception e) {
                // Si falla el reset, crea un de nou com a fallback
                mediaPlayer.release();
                mediaPlayer = null;
                mediaPlayer = MediaPlayer.create(this, songs[currentIndex]);
            }
        }

        mediaPlayer.setVolume(1.0f, 1.0f); // Volum al màxim (0.0 - 1.0)
        songTitle.setText(songTitles[currentIndex]);
        tvArtist.setText(artists[currentIndex]);
        imgCover.setImageResource(covers[currentIndex]);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setProgress(0);
        tvCurrentTime.setText(formatarTemps(0));
        tvTotalTime.setText(formatarTemps(mediaPlayer.getDuration()));

        // Quan la cançó acaba, passa automàticament a la següent
        mediaPlayer.setOnCompletionListener(mp -> canviarCanco(1));

        // Comença parat: Play actiu, Pause desactivat
        actualitzarEstatBotons(false);
    }

    // Assigna els events de clic i moviment a tots els controls
    private void configurarListeners() {

        // Botó Play: inicia la reproducció si no estava reproduint
        btnPlay.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                actualitzarSeekBar();
                actualitzarEstatBotons(true);
            }
        });

        // Botó Pause: pausa i atura les actualitzacions del SeekBar
        btnPause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                handler.removeCallbacksAndMessages(null);
                actualitzarEstatBotons(false);
            }
        });

        // Botó Stop: atura, allibera i recrea el MediaPlayer des del principi
        btnStop.setOnClickListener(v -> {
            mediaPlayer.stop();
            handler.removeCallbacksAndMessages(null);
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(this, songs[currentIndex]);
            seekBar.setProgress(0);
            tvCurrentTime.setText(formatarTemps(0));
            actualitzarEstatBotons(false);
        });

        // Botons Següent i Anterior
        btnNext.setOnClickListener(v -> canviarCanco(1));
        btnPrevious.setOnClickListener(v -> canviarCanco(-1));

        // SeekBar: permet cercar una posició dins la cançó manualment
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Només actua si el canvi l'ha fet l'usuari, no el codi
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    tvCurrentTime.setText(formatarTemps(progress));
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Atura les actualitzacions automàtiques mentre l'usuari llisca
                handler.removeCallbacksAndMessages(null);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Reprèn les actualitzacions si la cançó estava reproduint
                if (mediaPlayer != null && mediaPlayer.isPlaying()) actualitzarSeekBar();
            }
        });
    }

    // Canvia de cançó: delta = +1 (següent), -1 (anterior), 0 (clic directe a la llista)
    private void canviarCanco(int delta) {
        handler.removeCallbacksAndMessages(null);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null; // Null explícit per evitar reutilització d'un objecte alliberat
        }
        // Càlcul circular: va del final al principi i viceversa
        currentIndex = (currentIndex + delta + songs.length) % songs.length;
        inicialitzarPlayer();
        mediaPlayer.start();
        actualitzarSeekBar();
        actualitzarEstatBotons(true);
        // Actualitza el ressaltat a la llista de continuació
        if (adapter != null) adapter.setCurrentIndex(currentIndex);
    }

    // Activa/desactiva els botons Play i Pause amb opacitat visual
    private void actualitzarEstatBotons(boolean reproduint) {
        btnPlay.setEnabled(!reproduint);
        btnPause.setEnabled(reproduint);
        // Botó desactivat = translúcid (alpha 0.4), activat = opac (alpha 1.0)
        btnPlay.setAlpha(reproduint ? 0.4f : 1f);
        btnPause.setAlpha(reproduint ? 1f : 0.4f);
    }

    // Actualitza el SeekBar i el temps actual cada 500ms mentre reprodueix
    // Elimina callbacks anteriors per evitar múltiples bucles simultanis
    private void actualitzarSeekBar() {
        handler.removeCallbacksAndMessages(null);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            tvCurrentTime.setText(formatarTemps(mediaPlayer.getCurrentPosition()));
            handler.postDelayed(this::actualitzarSeekBar, 500); // Es crida a si mateixa cada 0.5s
        }
    }

    // Converteix mil·lisegons a format MM:SS (ex: 125000ms → "02:05")
    private String formatarTemps(int ms) {
        return String.format(Locale.getDefault(), "%02d:%02d", (ms / 1000) / 60, (ms / 1000) % 60);
    }

    // Allibera tots els recursos en tancar l'app per evitar fuites de memòria
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