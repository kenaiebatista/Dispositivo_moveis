package com.example.appdesergio;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Calcular extends AppCompatActivity {



    EditText edPeso;

    EditText edAltura;
    Button btnCalcular;
    ImageView imageView;


    double imc = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calcular);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
                (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Integer[] images = new Integer[]{
                R.drawable.perfil,
                R.drawable.abaixopeso,
                R.drawable.normal,
                R.drawable.sobrepeso,
                R.drawable.obesidade1,
                R.drawable.obesidade2,
                R.drawable.obesidade3,
        };
        ImageView imageView = findViewById(R.id.imageView2);

        double altura = getIntent().getExtras().getDouble("altura");
        TextView textAltura =findViewById(R.id.txtAltura);
        textAltura.setText(Double.toString(altura));

        double peso = getIntent().getExtras().getDouble("peso");
        TextView textPeso =findViewById(R.id.txtPeso);
        textPeso.setText(Double.toString(peso));

        double imc = getIntent().getExtras().getDouble("imc");
        TextView txtIMC =findViewById(R.id.txtIMC);
        txtIMC.setText(String.format("%.2f", imc));

        TextView textClass = findViewById(R.id.txtClass);

        if(imc<18.5) {
            textClass.setText("Abaixo do peso");
            imageView.setImageResource(images[1]);
        }
        else if(imc>=18.5&&imc<=24.9) {
            textClass.setText("Normal");
            imageView.setImageResource(images[2]);
        }
        else if(imc>=25&&imc<=29.9) {
            textClass.setText("Sobrepeso");
            imageView.setImageResource(images[3]);
        }
        else if(imc>=30&&imc<=34.9) {
            textClass.setText("Obesidade grau 1");
            imageView.setImageResource(images[4]);
        }
        else if(imc>=35&&imc<=39.9) {
            textClass.setText("Obesidade grau 2");
            imageView.setImageResource(images[5]);
        }
        else if(imc>=40) {
            textClass.setText("Obesidade grau 3");
            imageView.setImageResource(images[6]);
        } else{
            imageView.setImageResource(images[0]);
        }

    }
}