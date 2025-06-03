package com.l3.imcsomativa1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    EditText editPeso, editAltura;
    Button btnCalcular;
    TextView textResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPeso = findViewById(R.id.editPeso);
        editAltura = findViewById(R.id.editAltura);
        btnCalcular = findViewById(R.id.btnCalcular);
        textResultado = findViewById(R.id.textResultado);

        btnCalcular.setOnClickListener(v -> {

            String pesoStr = editPeso.getText().toString();
            String alturaStr = editAltura.getText().toString();

            if (pesoStr.isEmpty() || alturaStr.isEmpty()) {
                Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            double peso = Double.parseDouble(pesoStr);
            double altura = Double.parseDouble(alturaStr);

            if (altura <= 0 || peso <= 0) {
                Toast.makeText(MainActivity.this, "Valores inválidos!", Toast.LENGTH_SHORT).show();
                return;
            }

            double imc = peso / (altura * altura);
            String classificacao;

            if (imc < 18.5) {
                classificacao = "Abaixo do peso";
            } else if (imc < 24.9) {
                classificacao = "Dentro do peso";
            } else if (imc < 29.9) {
                classificacao = "Acima do peso";
            } else if (imc < 34.9) {
                classificacao = "Obesidade grau 1";
            } else if (imc < 39.9) {
                classificacao = "Obesidade grau 2";
            } else {
                classificacao = "Obesidade grau 3";
            }

            String resultado = String.format("IMC: %.2f\nClassificação: %s", imc, classificacao);
            textResultado.setText(resultado);
        });
    }
}
