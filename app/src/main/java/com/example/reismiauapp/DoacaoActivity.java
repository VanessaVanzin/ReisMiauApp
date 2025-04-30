package com.example.reismiauapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reismiauapp.helpers.BottomNavBarHelper;

public class DoacaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.doacao), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView instagramIcon = findViewById(R.id.ic_instagram);
        ImageView emailIcon = findViewById(R.id.ic_mail);

        instagramIcon.setOnClickListener(v -> {
            String instagramUrl = "https://www.instagram.com/reismiau/";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
            startActivity(intent);
        });

        emailIcon.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:reismiaus@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Quero ajudar a Reis Miau!");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Olá! Gostaria de saber como posso ajudar.");

            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            }
        });

        BottomNavBarHelper.setup(this);
    }
}
