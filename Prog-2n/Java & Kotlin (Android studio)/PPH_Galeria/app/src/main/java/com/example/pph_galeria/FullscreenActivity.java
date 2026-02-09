package com.example.pph_galeria;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class FullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Habilitar ActionBar amb botó enrere
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Imatge");
        }

        // Crear ImageView dinàmicament
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setBackgroundColor(Color.BLACK);

        // Recollir la imatge de l’Intent
        int imageRes = getIntent().getIntExtra("imageRes", -1);
        if (imageRes != -1) {
            imageView.setImageResource(imageRes);
        }

        setContentView(imageView);
    }

    // Gestionar botó enrere de l'ActionBar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
