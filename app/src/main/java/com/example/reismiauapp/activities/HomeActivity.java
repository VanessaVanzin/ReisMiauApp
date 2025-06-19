package com.example.reismiauapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reismiauapp.R;
import com.example.reismiauapp.helpers.BottomNavBarHelper;

public class HomeActivity extends AppCompatActivity {
    private ImageButton btnLista, btnDoacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLista = findViewById(R.id.btnLista);
        btnDoacao = findViewById(R.id.btnDoacao);
        ImageView instagramIcon = findViewById(R.id.instagram_icon);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListaGatosActivity.class);
                startActivity(intent);
            }
        });

        btnDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DoacaoActivity.class);
                startActivity(intent);
            }
        });

        instagramIcon.setOnClickListener(v -> {
            String instagramUrl = "https://www.instagram.com/reismiau/";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
            startActivity(intent);
        });

        BottomNavBarHelper.setup(this);
    }
}
