package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    Button btn_act2;
    // Creem el botó que ens permetrà canviar d'activitat 1 a activitat 2

    Intent intent;
    // Intent ens permet iniciar una altra activitat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Activem un estat el qual ens deixa aprofitar tota la pantalla

        setContentView(R.layout.activity_main);
        // Carreguem el layout que associem a la segona activitat a partir del fitxer XML principal

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Ajustem els marges del contingut per tal que no se solapi res mentre es navega

        btn_act2 = findViewById(R.id.btn_act2);
        // Assignem el nostre botó amb el layout base (activity 1)

        btn_act2.setOnClickListener(  View->{
            intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
        // Diem al botó que ha de fer, en el nostre cas volem que vagi a la segona activitat, 
        // per tant fem que el botó vagi endavant, tancant aixi l'activitat 1 per anar a la 2.
    }
}
