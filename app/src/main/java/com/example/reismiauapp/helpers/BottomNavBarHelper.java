package com.example.reismiauapp.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.reismiauapp.activities.CadastroAdminActivity;
import com.example.reismiauapp.activities.DoacaoActivity;
import com.example.reismiauapp.activities.HomeActivity;
import com.example.reismiauapp.activities.ListaGatosActivity;
import com.example.reismiauapp.R;
import com.example.reismiauapp.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class BottomNavBarHelper {
    public static void setup(final Activity activity) {
        ImageView navHome = activity.findViewById(R.id.nav_home);
        ImageView navLista = activity.findViewById(R.id.nav_lista);
        ImageView navDoacao = activity.findViewById(R.id.nav_doacao);
        ImageView navCadastroAdmin = activity.findViewById(R.id.nav_cadastroAdmin);
        ImageView navLogout = activity.findViewById(R.id.nav_logout);

        int tipoUsuario = UsuarioHelper.obterTipoUsuario(activity);
        if (navCadastroAdmin != null && tipoUsuario != 0) {
            navCadastroAdmin.setVisibility(View.GONE);
        }

        if (navHome != null) {
            navHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, HomeActivity.class));
                }
            });
        }

        if (navLista != null) {
            navLista.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, ListaGatosActivity.class));
                }
            });
        }

        if (navDoacao != null) {
            navDoacao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, DoacaoActivity.class));
                }
            });
        }

        if (navCadastroAdmin != null) {
            navCadastroAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, CadastroAdminActivity.class));
                }
            });
        }

        if (navLogout != null) {
            navLogout.setOnClickListener(v -> {
                new AlertDialog.Builder(activity)
                        .setTitle("Confirmar saída")
                        .setMessage("Tem certeza que deseja sair?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            FirebaseAuth.getInstance().signOut(); // Desloga do Firebase
                            UsuarioHelper.limparPrefs(activity); // Limpa tipo do SharedPreferences
                            Intent intent = new Intent(activity, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Evita voltar com o botão de voltar
                            activity.startActivity(intent);
                            activity.finish();
                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            });
        }
    }
}