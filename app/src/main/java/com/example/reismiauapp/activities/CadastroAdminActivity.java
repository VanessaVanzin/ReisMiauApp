package com.example.reismiauapp.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reismiauapp.models.GatoModel;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

import com.example.reismiauapp.R;
import com.example.reismiauapp.helpers.BottomNavBarHelper;

public class CadastroAdminActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private String petIdEdicao;

    private EditText edtStatus, edtGenero, edtIdade, edtTamanho, edtNome, edtDescricao, edtRaca;

    private Button btnSalvar;
    private Button btnTirarFoto;
    private Button btnDaGaleria;
    private ImageButton btnVoltar;

    private final String[] statusOptions = {"Disponível", "Adotado"};
    private final String[] generoOptions = {"M", "F"};
    private final String[] tamanhoOptions = {"Mini", "P", "M", "G", "XG"};
    private final String[] idadeOptions = {"Filhote", "Adulto", "Sênior"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cadastroAdmin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupPickers();

        petIdEdicao = getIntent().getStringExtra("petId");
        if (petIdEdicao != null) {
            // Edição: preencher campos
            edtNome.setText(getIntent().getStringExtra("nome"));
            edtDescricao.setText(getIntent().getStringExtra("descricao"));
            edtGenero.setText(getIntent().getStringExtra("genero"));
            edtIdade.setText(getIntent().getStringExtra("idade"));
            edtRaca.setText(getIntent().getStringExtra("raca"));
            edtTamanho.setText(getIntent().getStringExtra("tamanho"));

            int statusInt = getIntent().getIntExtra("status", 0);
            edtStatus.setText(statusInt == 0 ? "Disponível" : "Adotado");
        }

        setupButtons();
        setupActivityLaunchers();

        BottomNavBarHelper.setup(this);
    }

    private void initViews() {
        edtStatus = findViewById(R.id.edtStatus);
        edtGenero = findViewById(R.id.edtGenero);
        edtIdade = findViewById(R.id.edtIdade);
        edtTamanho = findViewById(R.id.edtTamanho);
        edtNome = findViewById(R.id.edtNome);
        edtDescricao = findViewById(R.id.edtDescricao);
        edtRaca = findViewById(R.id.edtRaca);

        btnSalvar = findViewById(R.id.btnSalvar);
        btnTirarFoto = findViewById(R.id.btnTirarFoto);
        btnDaGaleria = findViewById(R.id.btnDaGaleria);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void setupPickers() {
        edtStatus.setOnClickListener(v -> showPickerDialog("Selecione o status", statusOptions, edtStatus));
        edtGenero.setOnClickListener(v -> showPickerDialog("Selecione o gênero", generoOptions, edtGenero));
        edtIdade.setOnClickListener(v -> showPickerDialog("Selecione a idade", idadeOptions, edtIdade));
        edtTamanho.setOnClickListener(v -> showPickerDialog("Selecione o tamanho", tamanhoOptions, edtTamanho));
    }

    private void showPickerDialog(String title, String[] options, EditText targetEditText) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setItems(options, (dialog, which) -> {
                    targetEditText.setText(options[which]);
                })
                .show();
    }

    private void setupButtons() {
        btnSalvar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString().trim();
            String idade = edtIdade.getText().toString().trim();
            String genero = edtGenero.getText().toString().trim();
            String raca = edtRaca.getText().toString().trim();
            String tamanho = edtTamanho.getText().toString().trim();
            String statusStr = edtStatus.getText().toString().trim();
            String descricao = edtDescricao.getText().toString().trim();

            if (nome.isEmpty() || idade.isEmpty() || genero.isEmpty() || raca.isEmpty() || tamanho.isEmpty() || statusStr.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int status = statusStr.equalsIgnoreCase("Disponível") ? 0 : 1;

            // todo: fazer a parte das fotos

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            if (petIdEdicao != null) {
                // Atualiza
                Map<String, Object> dadosAtualizados = new HashMap<>();
                dadosAtualizados.put("name", nome);
                dadosAtualizados.put("description", descricao);
                dadosAtualizados.put("gender", genero);
                dadosAtualizados.put("age", idade);
                dadosAtualizados.put("race", raca);
                dadosAtualizados.put("size", tamanho);
                dadosAtualizados.put("status", status);
                // se quiser salvar fotos depois: dadosAtualizados.put("photos", ...);

                db.collection("pets").document(petIdEdicao)
                        .update(dadosAtualizados)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Pet atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, ListaGatosActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Erro ao atualizar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                // Cadastro novo
                GatoModel gato = new GatoModel(nome, descricao, genero, idade, raca, tamanho, status);
                db.collection("pets")
                        .add(gato)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Pet cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, ListaGatosActivity.class));
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Erro ao cadastrar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        btnTirarFoto.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            } else {
                openCamera();
            }
        });

        btnDaGaleria.setOnClickListener(v -> {
            Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickPhotoIntent.setType("image/*");
            galleryLauncher.launch(pickPhotoIntent);
        });

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void setupActivityLaunchers() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getExtras() != null) {
                            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                            // Coloque a imagem na tela aqui
                        }
                    }
                }
        );

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Uri selectedImageUri = data.getData();
                            // Coloque a imagem na tela aqui
                        }
                    }
                }
        );
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Permissão da câmera negada", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

