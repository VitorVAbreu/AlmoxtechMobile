package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.util.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager session = new SessionManager(this);

        TextView tvBemVindo = findViewById(R.id.tvBemVindo);
        TextView tvPerfil   = findViewById(R.id.tvPerfil);
        tvBemVindo.setText("Olá, " + session.getUsuarioNome() + "!");
        tvPerfil.setText("Perfil: " + session.getUsuarioPerfil());

        Button btnProdutos    = findViewById(R.id.btnProdutos);
        Button btnRequisicoes = findViewById(R.id.btnRequisicoes);
        Button btnUsuarios    = findViewById(R.id.btnUsuarios);
        Button btnRelatorios  = findViewById(R.id.btnRelatorios);
        Button btnSair        = findViewById(R.id.btnSair);

        btnProdutos.setOnClickListener(v ->
                startActivity(new Intent(this, ProdutosActivity.class)));

        btnRequisicoes.setOnClickListener(v ->
                startActivity(new Intent(this, RequisicaoActivity.class)));

        if (session.isAdmin()) {
            btnUsuarios.setVisibility(View.VISIBLE);
            btnUsuarios.setOnClickListener(v ->
                    startActivity(new Intent(this, UsuariosActivity.class)));

            btnRelatorios.setVisibility(View.VISIBLE);
            btnRelatorios.setOnClickListener(v ->
                    startActivity(new Intent(this, RelatoriosActivity.class)));
        } else {
            btnUsuarios.setVisibility(View.GONE);
            btnRelatorios.setVisibility(View.GONE);
        }

        btnSair.setOnClickListener(v -> {
            session.encerrarSessao();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
