package com.l3.margem;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private EditText valorFipeInput;
    private TextView resultadoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Alterar a cor da StatusBar para vermelho
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.parseColor("#BA0E0E"));
        }

        valorFipeInput = findViewById(R.id.valorFipeInput);
        resultadoView = findViewById(R.id.resultadoView);

        valorFipeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                calcularPrecos();
            }
        });
    }

    private void calcularPrecos() {
        try {
            double fipe = Double.parseDouble(valorFipeInput.getText().toString());
            StringBuilder resultado = new StringBuilder();
            resultado.append("20% abaixo: ").append(fipe * 0.80).append("\n");
            resultado.append("25% abaixo: ").append(fipe * 0.75).append("\n");
            resultado.append("30% abaixo: ").append(fipe * 0.70).append("\n");
            resultado.append("35% abaixo: ").append(fipe * 0.65).append("\n");
            resultado.append("40% abaixo: ").append(fipe * 0.60).append("\n");
            resultadoView.setText(resultado.toString());
        } catch (NumberFormatException e) {
            resultadoView.setText("");
        }
    }
}
