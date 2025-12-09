package com.example.pph_calc_text;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn_sumar, btn_restar, btn_multiplicar, btn_dividir;
    private TextView resultat;
    private EditText txt_num1, txt_num2;


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

        btn_sumar=findViewById(R.id.btn_suma);
        btn_restar=findViewById(R.id.btn_resta);
        btn_multiplicar=findViewById(R.id.btn_multiplicar);
        btn_dividir=findViewById(R.id.btn_dividir);
        resultat=findViewById(R.id.txt_resultat);
        txt_num1=findViewById(R.id.txt_num1);
        txt_num2=findViewById(R.id.txt_num2);

        btn_sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(txt_num1.getText().toString());
                int num2 = Integer.parseInt(txt_num2.getText().toString());
                int txt_resultat= sumar(num1,num2);
                resultat.setText(String.valueOf(txt_resultat));
            }
        });
        btn_restar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(txt_num1.getText().toString());
                int num2 = Integer.parseInt(txt_num2.getText().toString());
                int txt_resultat= restar(num1,num2);
                resultat.setText(String.valueOf(txt_resultat));
            }
        }));
        btn_multiplicar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(txt_num1.getText().toString());
                int num2 = Integer.parseInt(txt_num2.getText().toString());
                int txt_resultat= multiplicar(num1,num2);
                resultat.setText(String.valueOf(txt_resultat));
            }
        }));
        btn_dividir.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(txt_num1.getText().toString());
                int num2 = Integer.parseInt(txt_num2.getText().toString());
                int txt_resultat= dividir(num1,num2);
                resultat.setText(String.valueOf(txt_resultat));
            }
        }));

    }

    public int sumar(int a, int b){return a+b;}
    public int restar(int a, int b){return a-b;}
    public int multiplicar(int a, int b){return a*b;}
    public int dividir(int a, int b){return a/b;}

}