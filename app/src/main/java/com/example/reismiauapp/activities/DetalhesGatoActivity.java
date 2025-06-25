package com.example.reismiauapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.reismiauapp.R;
import com.example.reismiauapp.adapters.ImageSliderAdapter;
import com.example.reismiauapp.helpers.BottomNavBarHelper;
import com.example.reismiauapp.helpers.UsuarioHelper;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DetalhesGatoActivity extends AppCompatActivity {

    private String nome;
    private String descricao;
    //  private String fotoUrl;
    private String genero;
    private String idade;
    private String raca;
    private String tamanho;
    private String petId;
    private int status;

    private TextView catName, catDescription, catGender, catAge, catRace, catSize, catStatus;
    private ImageButton btnEditar, btnVoltar, btnExcluir;
    private Button btnAdotar;
    private ViewPager2 viewPagerFotos;

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
        petId = getIntent().getStringExtra("petId");
        nome = intent.getStringExtra("nome");
        descricao = intent.getStringExtra("descricao");
        genero = intent.getStringExtra("genero");
        idade = intent.getStringExtra("idade");
        raca = intent.getStringExtra("raca");
        tamanho = intent.getStringExtra("tamanho");
        status = intent.getIntExtra("status", 0);
    }

    private void inicializarViews() {
        catName = findViewById(R.id.catName);
        catDescription = findViewById(R.id.catDescription);
        catGender = findViewById(R.id.catGender);
        catAge = findViewById(R.id.catAge);
        catRace = findViewById(R.id.catRace);
        catSize = findViewById(R.id.catSize);
        btnEditar = findViewById(R.id.btnEditar);
        btnAdotar = findViewById(R.id.btnAdotar);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnExcluir = findViewById(R.id.btnExcluir);
        catStatus = findViewById(R.id.catStatus);
        viewPagerFotos = findViewById(R.id.viewPagerFotos);
    }

    private void preencherDadosNaTela() {
        catName.setText(nome != null ? nome : "");
        catDescription.setText(descricao != null ? descricao : "");
        catGender.setText(genero != null ? genero : "");
        catAge.setText(idade != null ? idade : "");
        catRace.setText(raca != null ? raca : "");
        catSize.setText(tamanho != null ? tamanho : "");
        catStatus.setText(status == 0 ? "Disponível" : "Adotado");

        ArrayList<String> fotos = getIntent().getStringArrayListExtra("fotos");

        if (fotos != null && !fotos.isEmpty()) {
            ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(this, fotos);
            viewPagerFotos.setAdapter(sliderAdapter);
        }
    }

    private void configurarListeners() {
        btnEditar.setOnClickListener(v -> {
            Intent editIntent = new Intent(DetalhesGatoActivity.this, CadastroAdminActivity.class);
            editIntent.putExtra("petId", petId);
            editIntent.putExtra("nome", nome);
            editIntent.putExtra("descricao", descricao);
            editIntent.putExtra("genero", genero);
            editIntent.putExtra("idade", idade);
            editIntent.putExtra("raca", raca);
            editIntent.putExtra("tamanho", tamanho);
            editIntent.putExtra("status", status);

            ArrayList<String> fotos = getIntent().getStringArrayListExtra("fotos");
            if (fotos != null) {
                editIntent.putStringArrayListExtra("fotos", fotos);
            }

            startActivity(editIntent);
        });


        btnExcluir.setOnClickListener(v -> {
                            if (petId != null && !petId.isEmpty()) {
                                new androidx.appcompat.app.AlertDialog.Builder(this)
                                        .setTitle("Confirmar exclusão")
                                        .setMessage("Tem certeza que deseja excluir este pet?")
                                        .setPositiveButton("Sim", (dialog, which) -> {
                                            FirebaseFirestore.getInstance()
                                                    .collection("pets")
                                                    .document(petId)
                                                    .delete()
                                                    .addOnSuccessListener(unused -> {
                                                        Toast.makeText(this, "Pet excluído com sucesso!", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(this, "Erro ao excluir: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    });
                                        })
                                        .setNegativeButton("Cancelar", null)
                        .show();
            } else {
                Toast.makeText(this, "Pet não encontrado.", Toast.LENGTH_SHORT).show();
            }
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
