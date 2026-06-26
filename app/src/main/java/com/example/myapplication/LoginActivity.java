package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Usuario;
import com.example.myapplication.util.Senhas;
import com.example.myapplication.util.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etSenha;
    private AppDatabase db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db      = AppDatabase.getInstance(this);
        session = new SessionManager(this);


        if (session.estaLogado()) {
            irParaMain();
            return;
        }


        criarAdminPadraoSeNecessario();

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);

        Button btnEntrar    = findViewById(R.id.btnEntrar);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        btnEntrar.setOnClickListener(v -> tentarLogin());
        btnCadastrar.setOnClickListener(v ->
                startActivity(new Intent(this, CadastroActivity.class))
        );
    }

    private void tentarLogin() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String senhaHash = Senhas.hashSenha(senha);
        Usuario usuario  = db.usuarioDAO().buscarPorEmailSenha(email, senhaHash);

        if (usuario != null) {
            session.salvarSessao(usuario.id, usuario.nome, usuario.perfil);
            Toast.makeText(this, "Bem-vindo, " + usuario.nome + "!", Toast.LENGTH_SHORT).show();
            irParaMain();
        } else {
            Toast.makeText(this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
        }
    }

    private void criarAdminPadraoSeNecessario() {
        if (db.usuarioDAO().contarUsuarios() == 0) {
            Usuario admin = new Usuario(
                    "Administrador",
                    "admin@almoxtech.com",
                    Senhas.hashSenha("admin123"),
                    "ADMIN"
            );
            db.usuarioDAO().inserir(admin);
        }
    }

    private void irParaMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
