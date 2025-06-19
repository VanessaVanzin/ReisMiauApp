package com.example.reismiauapp.helpers;

import android.content.Context;

public class UsuarioHelper {
    private static final String PREFS_NAME = "UsuarioPrefs";
    private static final String KEY_TIPO = "tipo_usuario";

    public static void salvarTipoUsuario(Context context, int tipo) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(KEY_TIPO, tipo)
                .apply();
    }

    public static int obterTipoUsuario(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(KEY_TIPO, -1); // -1 = não definido
    }

    public static void limparPrefs(Context context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
    }
}
