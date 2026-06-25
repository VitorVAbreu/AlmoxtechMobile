package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almoxtech.R;
import com.example.myapplication.adapter.UsuarioAdapter;
import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Usuario;
import com.example.myapplication.util.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class UsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        db      = AppDatabase.getInstance(this);
        session = new SessionManager(this);

        // Só admin acessa
        if (!session.isAdmin()) {
            Toast.makeText(this, "Acesso restrito a administradores", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recyclerUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabNovoUsuario);
        fab.setOnClickListener(v ->
                startActivity(new Intent(this, CadastroUsuarioActivity.class))
        );

        carregarUsuarios();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarUsuarios();
    }

    private void carregarUsuarios() {
        List<Usuario> lista = db.usuarioDAO().listarAtivos();
        UsuarioAdapter adapter = new UsuarioAdapter(this, lista, new UsuarioAdapter.OnUsuarioClickListener() {
            @Override
            public void onEditar(Usuario usuario) {
                Intent intent = new Intent(UsuariosActivity.this, CadastroUsuarioActivity.class);
                intent.putExtra("usuario_id", usuario.id);
                startActivity(intent);
            }

            @Override
            public void onExcluir(Usuario usuario) {
                new AlertDialog.Builder(UsuariosActivity.this)
                        .setTitle("Excluir usuário")
                        .setMessage("Deseja excluir " + usuario.nome + "?")
                        .setPositiveButton("Excluir", (dialog, which) -> {
                            db.usuarioDAO().desativar(usuario.id);
                            Toast.makeText(UsuariosActivity.this, "Usuário excluído", Toast.LENGTH_SHORT).show();
                            carregarUsuarios();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}