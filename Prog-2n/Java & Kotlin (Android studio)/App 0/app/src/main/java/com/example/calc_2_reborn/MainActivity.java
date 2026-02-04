package com.example.calc_2_reborn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // Per mostrar missatges d'error a l'usuari

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaració de tots els botons i el TextView
    Button btn_comma, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9;
    Button btn_resta, btn_mult, btn_div, btn_suma, btn_equal, btn_clear, btn_clear2; // Afegim botó d'igual i de neteja
    TextView txt_resultat;

    // Variables d'estat per a la lògica de la calculadora
    private double valor1 = 0;
    private String operacioActual = ""; // Emmagatzema l'operació (+, -, *, /)
    private boolean nouValor = true; // Indica si el proper dígit començarà un nou número

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Configuració dels marges de la pantalla (boilerplate)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Inicialització dels components
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        txt_resultat = findViewById(R.id.txt_resultat);
        btn_suma = findViewById(R.id.btn_suma);
        btn_resta = findViewById(R.id.btn_resta);
        btn_mult = findViewById(R.id.btn_mult);
        btn_div = findViewById(R.id.btn_div);
        btn_comma = findViewById(R.id.btn_comma);
        // Cal assegurar-se que els IDs btn_equal i btn_clear existeixen a activity_main.xml
        btn_equal = findViewById(R.id.btn_equal);
        btn_clear = findViewById(R.id.btn_clear);
        btn_clear2 = findViewById(R.id.btn_clear2);
        // 2. Configuració dels Listeners per als DÍGITS i la COMA
        View.OnClickListener digitListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String digit = b.getText().toString();
                String currentText = txt_resultat.getText().toString();

                if (nouValor || currentText.equals("0")) {
                    // Si es comença un nou número, o el número actual és 0,
                    // es reemplaça el text amb el nou dígit.
                    txt_resultat.setText(digit);
                    nouValor = false;
                } else {
                    // Altrament, s'afegeix el dígit al final.
                    txt_resultat.setText(currentText + digit);
                }
            }
        };

        // Assignació del mateix listener a tots els botons de dígits
        btn_0.setOnClickListener(digitListener);
        btn_1.setOnClickListener(digitListener);
        btn_2.setOnClickListener(digitListener);
        btn_3.setOnClickListener(digitListener);
        btn_4.setOnClickListener(digitListener);
        btn_5.setOnClickListener(digitListener);
        btn_6.setOnClickListener(digitListener);
        btn_7.setOnClickListener(digitListener);
        btn_8.setOnClickListener(digitListener);
        btn_9.setOnClickListener(digitListener);

        // Listener per a la COMA (o punt decimal)
        btn_comma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = txt_resultat.getText().toString();
                // Utilitzem el punt com a separador decimal en programació (malgrat la coma al botó)
                if (!currentText.contains(".")) {
                    if (nouValor) {
                        txt_resultat.setText("0.");
                    } else {
                        txt_resultat.setText(currentText + ".");
                    }
                    nouValor = false;
                }
            }
        });


        // 3. Configuració dels Listeners per a les OPERACIONS (+, -, *, /)
        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                String operacioSeleccionada = b.getText().toString();

                try {
                    // 1. Convertir el text actual del TextView a un número (double)
                    double valorActual = Double.parseDouble(txt_resultat.getText().toString().replace(',', '.'));

                    if (!operacioActual.isEmpty()) {
                        // Si ja hi ha una operació pendent, calcula el resultat parcial
                        calcularResultat(valorActual);
                        // Mostrar el resultat parcial
                        txt_resultat.setText(String.valueOf(valor1));
                    } else {
                        // Si es la primera operació, simplement guardem el primer valor
                        valor1 = valorActual;
                    }

                    // 2. Guardar la nova operació i indicar que el proper input serà un nou número
                    operacioActual = operacioSeleccionada;
                    nouValor = true;

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Error de format de número.", Toast.LENGTH_SHORT).show();
                    netejar();
                }
            }
        };

        btn_suma.setOnClickListener(operationListener);
        btn_resta.setOnClickListener(operationListener);
        btn_mult.setOnClickListener(operationListener);
        btn_div.setOnClickListener(operationListener);

        // 4. Listener per al botó IGUAL (=)
        btn_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!operacioActual.isEmpty() && !nouValor) {
                    try {
                        double valor2 = Double.parseDouble(txt_resultat.getText().toString().replace(',', '.'));
                        calcularResultat(valor2);
                        txt_resultat.setText(formatResultat(valor1));

                        // Reiniciar l'estat per començar una nova operació
                        operacioActual = "";
                        nouValor = true;
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Error de format.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 5. Listener per al botó de Neteja (C/AC)
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                netejar();
            }
        });
        btn_clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                netejar();
            }
        });
        // Inicialització de la pantalla
        txt_resultat.setText("0");
    }

    /**
     * @brief Realitza el càlcul basat en l'operació actual i el segon valor.
     * @param valor2 El segon valor per a l'operació.
     */
    private void calcularResultat(double valor2) {
        switch (operacioActual) {
            case "+":
                valor1 = valor1 + valor2;
                break;
            case "-":
                valor1 = valor1 - valor2;
                break;
            case "*":
                valor1 = valor1 * valor2;
                break;
            case "/":
                if (valor2 != 0) {
                    valor1 = valor1 / valor2;
                } else {
                    Toast.makeText(this, "ERROR: Divisio per zero", Toast.LENGTH_LONG).show();
                    netejar();
                }
                break;
        }
    }

    /**
     * @brief Neteja l'estat de la calculadora
     */
    private void netejar() {
        valor1 = 0;
        operacioActual = "";
        nouValor = true;
        txt_resultat.setText("0");
    }

    /**
     * @brief Formata el resultat per evitar decimals innecessaris
     */
    private String formatResultat(double result) {
        if (result == (long) result) {
            return String.format("%d", (long) result);
        } else {
            return String.valueOf(result);
        }
    }
}