package com.example.reismiauapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reismiauapp.R;
import com.example.reismiauapp.models.UsuarioModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import android.text.InputType;
import android.widget.ImageView;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha, edtConfirmarSenha;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cadastro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmarSenha = findViewById(R.id.edtConfirmarSenha);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(v -> finish());
        btnCadastrar.setOnClickListener(v -> cadastrarUsuario());

        ImageView imgVerSenha = findViewById(R.id.imgVerSenha);
        ImageView imgVerConfirmarSenha = findViewById(R.id.imgVerConfirmarSenha);

        // Alternar visibilidade da senha
        imgVerSenha.setOnClickListener(v -> {
            if (edtSenha.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                edtSenha.setSelection(edtSenha.getText().length());
                imgVerSenha.setImageResource(R.drawable.baseline_disabled_visible_24);
            } else {
                edtSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                edtSenha.setSelection(edtSenha.getText().length());
                imgVerSenha.setImageResource(R.drawable.ic_visibility);
            }
        });

        // Alternar visibilidade da confirmação de senha
        imgVerConfirmarSenha.setOnClickListener(v -> {
            if (edtConfirmarSenha.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                edtConfirmarSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                edtConfirmarSenha.setSelection(edtConfirmarSenha.getText().length());
                imgVerConfirmarSenha.setImageResource(R.drawable.baseline_disabled_visible_24);
            } else {
                edtConfirmarSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                edtConfirmarSenha.setSelection(edtConfirmarSenha.getText().length());
                imgVerConfirmarSenha.setImageResource(R.drawable.ic_visibility);
            }
        });
    }

    private void cadastrarUsuario() {
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        String confirmarSenha = edtConfirmarSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha) || TextUtils.isEmpty(confirmarSenha)) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            salvarUsuarioNoFirestore(user.getUid(), email);
                        }
                    } else {
                        Exception e = task.getException();
                        if (e != null) {
                            String mensagemUsuario;

                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                // Email mal formatado ou credencial inválida
                                mensagemUsuario = "Email ou senha inválidos.";
                            } else if (e instanceof FirebaseAuthUserCollisionException) {
                                // Email já cadastrado
                                mensagemUsuario = "Este email já está cadastrado.";
                            } else if (e instanceof FirebaseAuthException) {
                                String codigoErro = ((FirebaseAuthException) e).getErrorCode();

                                switch (codigoErro) {
                                    case "ERROR_WEAK_PASSWORD":
                                        mensagemUsuario = "Senha muito fraca. Use pelo menos 6 caracteres.";
                                        break;
                                    case "ERROR_INVALID_EMAIL":
                                        mensagemUsuario = "Email inválido. Por favor, verifique e tente novamente.";
                                        break;
                                    case "ERROR_EMAIL_ALREADY_IN_USE":
                                        mensagemUsuario = "Este email já está cadastrado.";
                                        break;
                                    default:
                                        mensagemUsuario = "Erro: " + e.getLocalizedMessage();
                                        break;
                                }
                            } else {
                                mensagemUsuario = "Erro: " + e.getLocalizedMessage();
                            }

                            Toast.makeText(this, mensagemUsuario, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void salvarUsuarioNoFirestore(String uid, String email) {
        UsuarioModel usuario = new UsuarioModel(email, 1);
        db.collection("users")
                .document(uid)
                .set(usuario)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao salvar dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
