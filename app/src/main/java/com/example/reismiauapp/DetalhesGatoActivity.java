package com.example.reismiauapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reismiauapp.helpers.BottomNavBarHelper;

public class DetalhesGatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_gato);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detalhesGato), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton btnEditar = findViewById(R.id.btnEditar);
        Button btnAdotar = findViewById(R.id.btnAdotar);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(DetalhesGatoActivity.this, CadastroAdminActivity.class);
            startActivity(intent);
        });

        btnAdotar.setOnClickListener(v -> {
            String petName = "William";
            String phoneNumber = "+5554992223853";
            String message = "Olá! Acessei o app e tenho interesse em adotar o seguinte pet: \n" + petName;

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message)));
            startActivity(intent);
        });


        btnVoltar.setOnClickListener(v -> {
            finish();
        });

        BottomNavBarHelper.setup(this);
    }
}
