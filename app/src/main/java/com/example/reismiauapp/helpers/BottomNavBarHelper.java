package com.example.reismiauapp.helpers;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.example.reismiauapp.CadastroAdminActivity;
import com.example.reismiauapp.DoacaoActivity;
import com.example.reismiauapp.HomeActivity;
import com.example.reismiauapp.ListaGatosActivity;
import com.example.reismiauapp.R;

public class BottomNavBarHelper {
    public static void setup(final Activity activity) {
        ImageView navHome = activity.findViewById(R.id.nav_home);
        ImageView navLista = activity.findViewById(R.id.nav_lista);
        ImageView navDoacao = activity.findViewById(R.id.nav_doacao);
        ImageView navCadastroAdmin = activity.findViewById(R.id.nav_cadastroAdmin);

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
    }
}