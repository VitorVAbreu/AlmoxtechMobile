package com.example.myapplication.util;



import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME  = "almoxtech_session";
    private static final String KEY_ID     = "usuario_id";
    private static final String KEY_NOME   = "usuario_nome";
    private static final String KEY_PERFIL = "usuario_perfil";
    private static final String KEY_LOGADO = "logado";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void salvarSessao(int id, String nome, String perfil) {
        prefs.edit()
                .putInt(KEY_ID, id)
                .putString(KEY_NOME, nome)
                .putString(KEY_PERFIL, perfil)
                .putBoolean(KEY_LOGADO, true)
                .apply();
    }

    public boolean estaLogado() {
        return prefs.getBoolean(KEY_LOGADO, false);
    }

    public int getUsuarioId() {
        return prefs.getInt(KEY_ID, -1);
    }

    public String getUsuarioNome() {
        return prefs.getString(KEY_NOME, "");
    }

    public String getUsuarioPerfil() {
        return prefs.getString(KEY_PERFIL, "");
    }

    public boolean isAdmin() {
        return "ADMIN".equals(getUsuarioPerfil());
    }

    public void encerrarSessao() {
        prefs.edit().clear().apply();
    }
}
