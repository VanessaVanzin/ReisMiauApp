package com.example.reismiauapp.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
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

import com.example.reismiauapp.R;
import com.example.reismiauapp.helpers.BottomNavBarHelper;

public class CadastroAdminActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;

    private EditText edtStatus, edtGenero, edtIdade, edtTamanho;

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
        setupButtons();
        setupActivityLaunchers();

        BottomNavBarHelper.setup(this);
    }

    private void initViews() {
        edtStatus = findViewById(R.id.edtStatus);
        edtGenero = findViewById(R.id.edtGenero);
        edtIdade = findViewById(R.id.edtIdade);
        edtTamanho = findViewById(R.id.edtTamanho);

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
            Intent intent = new Intent(CadastroAdminActivity.this, ListaGatosActivity.class);
            startActivity(intent);
            finish();
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

