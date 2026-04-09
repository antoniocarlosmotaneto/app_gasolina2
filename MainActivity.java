package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaração dos componentes da tela
    EditText editTextAlcool;
    EditText editTextGasolina;
    TextView textViewResultado;
    Button buttonCalcular;
    Button buttonLimpar;

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

        // Vinculando os componentes do XML com as variáveis Java
        editTextAlcool    = findViewById(R.id.editTextAlcool);
        editTextGasolina  = findViewById(R.id.editTextGasolina);
        textViewResultado = findViewById(R.id.textViewResultado);
        buttonCalcular    = findViewById(R.id.buttonCalcular);
        buttonLimpar      = findViewById(R.id.buttonLimpar);

        // Listener do botão CALCULAR
        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });

        // Listener do botão LIMPAR
        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextAlcool.setText("");
                editTextGasolina.setText("");
                textViewResultado.setText("");
            }
        });
    }

    // Método que faz o cálculo e exibe o resultado
    public void calcular() {

        String strAlcool   = editTextAlcool.getText().toString().trim();
        String strGasolina = editTextGasolina.getText().toString().trim();

        // Validação: campos não podem estar vazios
        if (strAlcool.isEmpty() || strGasolina.isEmpty()) {
            Toast.makeText(this, "Preencha os dois campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        double precoAlcool;
        double precoGasolina;

        // Validação: os valores precisam ser números válidos
        try {
            precoAlcool   = Double.parseDouble(strAlcool.replace(",", "."));
            precoGasolina = Double.parseDouble(strGasolina.replace(",", "."));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Digite apenas valores numéricos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validação: preços precisam ser maiores que zero
        if (precoAlcool <= 0 || precoGasolina <= 0) {
            Toast.makeText(this, "Os preços precisam ser maiores que zero!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cálculo do percentual do álcool em relação à gasolina
        double percentual = (precoAlcool / precoGasolina) * 100;

        // Regra: álcool é vantajoso se for <= 70% do preço da gasolina
        if ((precoAlcool / precoGasolina) <= 0.70) {
            textViewResultado.setText(
                    String.format("Abasteça com ÁLCOOL!\n\nO álcool está a %.1f%% do preço da gasolina.", percentual)
            );
        } else {
            textViewResultado.setText(
                    String.format("Abasteça com GASOLINA!\n\nO álcool está a %.1f%% do preço da gasolina.", percentual)
            );
        }
    }
}