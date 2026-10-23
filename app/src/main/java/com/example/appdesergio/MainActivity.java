package com.example.appdesergio;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {



    EditText edPeso;
    EditText edAltura;
    Button btnCalcular;
    double imc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnCalcular = findViewById(R.id.btnCalcular);
        edAltura = findViewById(R.id.edAltura);
        edPeso = findViewById(R.id.edPeso);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCalcular.setOnClickListener( v -> {
            String strPeso =edPeso.getText().toString();
            String strAltura =edAltura.getText().toString();

            double peso = Double.parseDouble(strPeso);
            double altura = Double.parseDouble(strAltura);

            imc = peso/(altura*altura);

            Intent intent = new Intent(MainActivity.this, Calcular.class);

            intent.putExtra("imc", imc);
            intent.putExtra("peso", peso);
            intent.putExtra("altura", altura);

            startActivity(intent);

        });
    }
}