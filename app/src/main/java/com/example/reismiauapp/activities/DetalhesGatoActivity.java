package com.example.reismiauapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reismiauapp.R;
import com.example.reismiauapp.helpers.BottomNavBarHelper;
import com.example.reismiauapp.helpers.UsuarioHelper;

public class DetalhesGatoActivity extends AppCompatActivity {

    private String nome;
    private String descricao;
    private String fotoUrl;
    private String genero;
    private String idade;
    private String raca;
    private String tamanho;
    private int status;

    private TextView catName, catDescription, catGender, catAge, catRace, catSize, catStatus;
    private ImageView catImage;
    private ImageButton btnEditar, btnVoltar, btnExcluir;
    private Button btnAdotar;

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

        receberDadosIntent();
        inicializarViews();
        preencherDadosNaTela();
        configurarListeners();

        BottomNavBarHelper.setup(this);

        int tipoUsuario = UsuarioHelper.obterTipoUsuario(this);
        if (tipoUsuario != 0) {
            btnEditar.setVisibility(View.GONE);
            btnExcluir.setVisibility(View.GONE);
        }
    }

    private void receberDadosIntent() {
        Intent intent = getIntent();
        nome = intent.getStringExtra("nome");
        descricao = intent.getStringExtra("descricao");
        fotoUrl = intent.getStringExtra("fotoUrl");
        genero = intent.getStringExtra("genero");
        idade = intent.getStringExtra("idade");
        raca = intent.getStringExtra("raca");
        tamanho = intent.getStringExtra("tamanho");
        status = intent.getIntExtra("status", 0);
    }

    private void inicializarViews() {
        catName = findViewById(R.id.catName);
        catDescription = findViewById(R.id.catDescription);
        catImage = findViewById(R.id.catImage);
        catGender = findViewById(R.id.catGender);
        catAge = findViewById(R.id.catAge);
        catRace = findViewById(R.id.catRace);
        catSize = findViewById(R.id.catSize);
        btnEditar = findViewById(R.id.btnEditar);
        btnAdotar = findViewById(R.id.btnAdotar);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnExcluir = findViewById(R.id.btnExcluir);
        catStatus = findViewById(R.id.catStatus);
    }

    private void preencherDadosNaTela() {
        catName.setText(nome != null ? nome : "");
        catDescription.setText(descricao != null ? descricao : "");
        catGender.setText(genero != null ? genero : "");
        catAge.setText(idade != null ? idade : "");
        catRace.setText(raca != null ? raca : "");
        catSize.setText(tamanho != null ? tamanho : "");
        catStatus.setText(status == 0 ? "Disponível" : "Adotado");

        if (fotoUrl != null && !fotoUrl.isEmpty()) {
            Glide.with(this)
                    .load(fotoUrl)
                    .placeholder(R.drawable.gatoteste)
                    .into(catImage);
        } else {
            catImage.setImageResource(R.drawable.gatoteste);
        }
    }

    private void configurarListeners() {
        btnEditar.setOnClickListener(v -> {
            Intent editIntent = new Intent(DetalhesGatoActivity.this, CadastroAdminActivity.class);
            startActivity(editIntent);
        });

        btnAdotar.setOnClickListener(v -> {
            String phoneNumber = "+5554992223853";
            String message = "Olá! Acessei o app e tenho interesse em adotar o seguinte pet: \n" + nome;

            Intent intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message)));
            startActivity(intent1);
        });

        btnVoltar.setOnClickListener(v -> finish());
    }
}
