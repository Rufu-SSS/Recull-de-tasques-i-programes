package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity2 extends AppCompatActivity {

    Button btn_back; 
    // Botó que permet a l'usuari tornar a l'anterior pàgina en cas que aquest vulgui


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Activem un estat el qual ens deixa aprofitar tota la pantalla

        setContentView(R.layout.activity_main2);
        // Carreguem el layout que associem a la segona activitat a partir del fitxer XML principal

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ajustem els marges del contingut per tal que no se solapi res mentre es navega

        btn_back = findViewById(R.id.btn_back);
        // Assignem el nostre botó amb el layout base (activity 2)

        btn_back.setOnClickListener(view -> {
            finish();
        });
        // Diem al botó que ha de fer, en el nostre cas volem que torni a la primera activitat, 
        // per tant fem que el botó vagi enrere, tancant aixi l'activitat 2 per anar a la primera.

    }
}