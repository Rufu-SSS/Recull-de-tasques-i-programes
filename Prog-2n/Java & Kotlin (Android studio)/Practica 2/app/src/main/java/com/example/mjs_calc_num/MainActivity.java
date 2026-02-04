package com.example.mjs_calc_num;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9;
    Button btn_suma, btn_resta, btn_mult, btn_div, btn_igual, btn_C, btn_decimal;
    EditText txt_input;

    String operacio = "";
    double num1 = 0, num2 = 0, resultat = 0;
    boolean novaOperacio = true;

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

        // 🔹 Enllaçar els elements
        txt_input = findViewById(R.id.txt_input);
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
        btn_decimal = findViewById(R.id.btn_decimal);

        btn_suma = findViewById(R.id.btn_suma);
        btn_resta = findViewById(R.id.btn_resta);
        btn_mult = findViewById(R.id.btn_mult);
        btn_div = findViewById(R.id.btn_div);
        btn_igual = findViewById(R.id.btn_igual);
        btn_C = findViewById(R.id.btn_C);

        // 🔹 Botons numèrics
        android.view.View.OnClickListener listenerNum = v -> {
            Button b = (Button) v;
            if (novaOperacio) {
                txt_input.setText("");
                novaOperacio = false;
            }
            txt_input.append(b.getText());
        };

        btn_0.setOnClickListener(listenerNum);
        btn_1.setOnClickListener(listenerNum);
        btn_2.setOnClickListener(listenerNum);
        btn_3.setOnClickListener(listenerNum);
        btn_4.setOnClickListener(listenerNum);
        btn_5.setOnClickListener(listenerNum);
        btn_6.setOnClickListener(listenerNum);
        btn_7.setOnClickListener(listenerNum);
        btn_8.setOnClickListener(listenerNum);
        btn_9.setOnClickListener(listenerNum);

        // 🔹 Punt decimal
        btn_decimal.setOnClickListener(v -> {
            String current = txt_input.getText().toString();
            if (novaOperacio) {
                txt_input.setText("");
                novaOperacio = false;
            }
            if (!current.contains(".")) {
                txt_input.append(".");
            }
        });

        // 🔹 Operacions
        btn_suma.setOnClickListener(v -> guardarOperacio("+"));
        btn_resta.setOnClickListener(v -> guardarOperacio("-"));
        btn_mult.setOnClickListener(v -> guardarOperacio("*"));
        btn_div.setOnClickListener(v -> guardarOperacio("/"));

        // 🔹 Igual
        btn_igual.setOnClickListener(v -> calcularResultat());

        // 🔹 Clear
        btn_C.setOnClickListener(v -> {
            txt_input.setText("");
            num1 = num2 = resultat = 0;
            operacio = "";
        });
    }

    private void guardarOperacio(String op) {
        try {
            num1 = Double.parseDouble(txt_input.getText().toString());
            operacio = op;
            novaOperacio = true;
        } catch (Exception e) {
            txt_input.setText("Error");
        }
    }

    private void calcularResultat() {
        try {
            num2 = Double.parseDouble(txt_input.getText().toString());
            switch (operacio) {
                case "+": resultat = num1 + num2; break;
                case "-": resultat = num1 - num2; break;
                case "*": resultat = num1 * num2; break;
                case "/":
                    if (num2 == 0) {
                        txt_input.setText("∞");
                        return;
                    }
                    resultat = num1 / num2;
                    break;
                default: resultat = num2; break;
            }
            txt_input.setText(String.valueOf(resultat));
            novaOperacio = true;
        } catch (Exception e) {
            txt_input.setText("Error");
        }
    }
}
