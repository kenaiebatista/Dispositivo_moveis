package com.example.appdesergio;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    int posicao = 0;
    Button botaoAvancar;
    Button botaoVoltar;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        botaoVoltar = findViewById(R.id.btnAnterior);
        botaoAvancar = findViewById(R.id.btnProximo);
        imageView = findViewById(R.id.imageView);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Integer[] images = new Integer[]{
                R.drawable.cachorro,
                R.drawable.gardem,
                R.drawable.happy,
                R.drawable.patinho,
                R.drawable.porquinho
        };

        botaoAvancar.setOnClickListener( v -> {
            if(posicao== images.length-1) posicao = 0;
            posicao++;
            imageView.setImageResource(images[posicao]);

        });

        botaoVoltar.setOnClickListener( v -> {

            posicao--;
            if(posicao < 0) posicao = images.length-1;
            imageView.setImageResource(images[posicao]);

        });
    }
}