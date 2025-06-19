package com.example.reismiauapp.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reismiauapp.R;
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

        Button btnSejaParceiro = findViewById(R.id.doacao_button);
        Button btnCopiarPix = findViewById(R.id.btn_copiar_chave_pix);
        ImageView instagramIcon = findViewById(R.id.ic_instagram);
        ImageView whatsappIcon = findViewById(R.id.ic_whatsapp);
        ImageView emailIcon = findViewById(R.id.ic_mail);

        btnSejaParceiro.setOnClickListener(v -> {
            String phoneNumber = "+5554991202991";
            String message = "Olá! Quero ser parceiro da Reis Miau.";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message));
            startActivity(intent);
        });

        btnCopiarPix.setOnClickListener(v -> {
            String chavePix = "00020126330014br.gov.bcb.pix0111008414990635204000053039865802BR5924SUELEN MARTINS GRIEBELER6015FLORES DA CUNHA62120508ReisMiau6304EC74";

            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Chave Pix", chavePix);
            clipboard.setPrimaryClip(clip);

            Toast.makeText(this, "Chave Pix copiada com sucesso!", Toast.LENGTH_SHORT).show();
        });

        instagramIcon.setOnClickListener(v -> {
            String instagramUrl = "https://www.instagram.com/reismiau/";

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl));
            startActivity(intent);
        });

        whatsappIcon.setOnClickListener(v -> {
            String phoneNumber = "+5554991202991";
            String message = "Olá! É da Reis Miau?";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + message));
            startActivity(intent);
        });

        emailIcon.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:reismiaus@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Quero ajudar a Reis Miau!");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Olá! Gostaria de saber como posso ajudar.");

            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            } else {
                Toast.makeText(this, "Você precisa ter um aplicativo de email instalado!", Toast.LENGTH_LONG).show();
            }
        });


        BottomNavBarHelper.setup(this);
    }
}
