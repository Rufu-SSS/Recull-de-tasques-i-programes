package com.example.a3enratlla;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // Afegit per a missatges d'error/informació

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textInfo;

    // Array d'Integer per a facilitar la cerca de l'índex amb Arrays.asList().indexOf()
    Integer[] botons;

    int[] taula = new int[9];  // 0 = buit, 1 = jugador (X), -1 = IA (O)
    boolean jocAcabat = false;

    // Definim les marques de text per simplificar (X i O)
    private static final String MARCA_JUGADOR = "X";
    private static final String MARCA_IA = "O";

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

        textInfo = findViewById(R.id.textInfo);

        // Assegura't que aquests IDs existeixen al teu activity_main.xml
        botons = new Integer[]{
                R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6,
                R.id.btn_7, R.id.btn_8, R.id.btn_9
        };

        // Inicialitzar el tauler i l'estat del joc
        reiniciar(null);
    }

    /**
     * @brief Es crida quan el jugador prem un botó del tauler.
     * Ha d'estar assignat a android:onClick="posarFitxa" a tots els 9 botons de l'XML.
     */
    public void posarFitxa(View view) {
        if (jocAcabat) {
            Toast.makeText(this, R.string.joc_acabat_reinicia, Toast.LENGTH_SHORT).show();
            return;
        }

        Button btnJo = (Button) view;
        // Obtenim l'índex (0 a 8) del botó dins de l'array 'botons'
        int numBtn = Arrays.asList(botons).indexOf(view.getId());

        // Comprovació: si la casella ja està ocupada (no és 0), sortim.
        if (taula[numBtn] != 0) return;

        // 1. MOVIMENT DEL JUGADOR
        taula[numBtn] = 1;
        // Substitució de la crida a drawable per setText
        btnJo.setText(MARCA_JUGADOR);
        btnJo.setEnabled(false); // Deshabilitem el botó

        // 2. COMPROVACIONS DESPRÉS DEL JUGADOR
        if (comprovarGuany(1)) {
            textInfo.setText(R.string.has_guanyat);
            jocAcabat = true;
            return;
        }

        if (taulerPle()) {
            textInfo.setText(R.string.empat);
            jocAcabat = true;
            return;
        }

        // 3. MOVIMENT DE LA IA
        textInfo.setText(R.string.torn_ia); // Informem que mou la IA
        ia();

        // 4. COMPROVACIONS DESPRÉS DE LA IA
        if (comprovarGuany(-1)) {
            textInfo.setText(R.string.has_perdut);
            jocAcabat = true;
        } else if (taulerPle()) {
            textInfo.setText(R.string.empat);
            jocAcabat = true;
        } else {
            textInfo.setText(R.string.torn_jugador); // Tornar a informar que és el torn del jugador
        }
    }

    /**
     * @brief Realitza un moviment aleatori per part de la IA (-1) en una casella buida.
     */
    public void ia() {
        Random fitxaRandom = new Random();
        int pos;

        // Busquem una posició buida
        do {
            pos = fitxaRandom.nextInt(taula.length);
        } while (taula[pos] != 0 && !taulerPle());

        // Si el tauler és ple (empat), no fem res
        if (taulerPle() && taula[pos] != 0) return;

        // 1. Marcar el tauler lògic
        taula[pos] = -1;

        // 2. Actualitzar el botó (UI)
        Button btn = findViewById(botons[pos]);
        // Substitució de la crida a drawable per setText
        btn.setText(MARCA_IA);
        btn.setEnabled(false); // Deshabilitem el botó de la IA
    }

    /**
     * @brief Comprova si el jugador (1) o la IA (-1) ha guanyat.
     */
    public boolean comprovarGuany(int jugador) {
        int[][] guanyadores = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Horitzontals
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Verticals
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] linia : guanyadores) {
            if (taula[linia[0]] == jugador &&
                    taula[linia[1]] == jugador &&
                    taula[linia[2]] == jugador) {

                // Efecte visual en guanyar: reduir opacitat
                for (int pos : linia) {
                    Button b = findViewById(botons[pos]);
                    b.setAlpha(0.5f);
                }

                // Deshabilitar tots els botons al guanyar
                for (int id : botons) {
                    findViewById(id).setEnabled(false);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @brief Comprova si totes les caselles estan ocupades.
     */
    public boolean taulerPle() {
        for (int valor : taula) {
            if (valor == 0) return false;
        }
        return true;
    }

    /**
     * @brief Reinicia l'estat del joc i el tauler visual.
     * Ha d'estar assignat al botó de reinici a android:onClick="reiniciar"
     */
    public void reiniciar(View view) {
        // Reinici de l'estat lògic
        Arrays.fill(taula, 0);
        jocAcabat = false;

        // Reinici de la UI
        textInfo.setText(R.string.torn_jugador);

        for (int id : botons) {
            Button b = findViewById(id);
            // Netejar la marca (X/O), restaurar l'opacitat i habilitar
            b.setText("");
            b.setAlpha(1f);
            b.setEnabled(true);
        }
    }
}