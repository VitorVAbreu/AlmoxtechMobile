package com.example.myapplication;



import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Usuario;
import com.example.myapplication.util.Senhas;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText etNome, etEmail, etSenha, etConfirmarSenha;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        db = AppDatabase.getInstance(this);

        etNome           = findViewById(R.id.etNome);
        etEmail          = findViewById(R.id.etEmail);
        etSenha          = findViewById(R.id.etSenha);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);

        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        Button btnVoltar    = findViewById(R.id.btnVoltar);

        btnCadastrar.setOnClickListener(v -> realizarCadastro());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void realizarCadastro() {
        String nome      = etNome.getText().toString().trim();
        String email     = etEmail.getText().toString().trim();
        String senha     = etSenha.getText().toString().trim();
        String confirmar = etConfirmarSenha.getText().toString().trim();


        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            return;
        }


        if (senha.length() < 6) {
            Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!senha.equals(confirmar)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }


        if (db.usuarioDAO().buscarPorEmail(email) != null) {
            Toast.makeText(this, "Este e-mail já está cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }


        Usuario novoUsuario = new Usuario(
                nome,
                email,
                Senhas.hashSenha(senha),
                "FUNCIONARIO"
        );

        long id = db.usuarioDAO().inserir(novoUsuario);

        if (id > 0) {
            Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao criar conta. Tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }
}
