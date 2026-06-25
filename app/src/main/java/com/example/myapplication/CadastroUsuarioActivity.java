package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.almoxtech.R;
import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Usuario;
import com.example.myapplication.util.Senhas;
import com.example.myapplication.util.Senhas;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private TextInputEditText etNome, etEmail, etSenha;
    private Spinner spinnerPerfil;
    private AppDatabase db;
    private Usuario usuarioEditando = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        db = AppDatabase.getInstance(this);

        etNome         = findViewById(R.id.etNome);
        etEmail        = findViewById(R.id.etEmail);
        etSenha        = findViewById(R.id.etSenha);
        spinnerPerfil  = findViewById(R.id.spinnerPerfil);

        // Spinner de perfil
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"FUNCIONARIO", "ADMIN"}
        );
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPerfil.setAdapter(adapterSpinner);

        // Modo edição
        int usuarioId = getIntent().getIntExtra("usuario_id", -1);
        if (usuarioId != -1) {
            usuarioEditando = db.usuarioDAO().buscarPorId(usuarioId);
            preencherCampos();
        }

        Button btnSalvar = findViewById(R.id.btnSalvar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnSalvar.setOnClickListener(v -> salvar());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void preencherCampos() {
        etNome.setText(usuarioEditando.nome);
        etEmail.setText(usuarioEditando.email);
        // Seleciona o perfil no spinner
        if ("ADMIN".equals(usuarioEditando.perfil)) {
            spinnerPerfil.setSelection(1);
        } else {
            spinnerPerfil.setSelection(0);
        }
    }

    private void salvar() {
        String nome   = etNome.getText().toString().trim();
        String email  = etEmail.getText().toString().trim();
        String senha  = etSenha.getText().toString().trim();
        String perfil = spinnerPerfil.getSelectedItem().toString();

        if (nome.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Nome e e-mail são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (usuarioEditando == null) {
            // Cadastro novo
            if (senha.length() < 6) {
                Toast.makeText(this, "Senha deve ter ao menos 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }
            if (db.usuarioDAO().buscarPorEmail(email) != null) {
                Toast.makeText(this, "E-mail já cadastrado", Toast.LENGTH_SHORT).show();
                return;
            }
            Usuario novo = new Usuario(nome, email, Senhas.hashSenha(senha), perfil);
            db.usuarioDAO().inserir(novo);
            Toast.makeText(this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show();
        } else {
            // Edição
            usuarioEditando.nome   = nome;
            usuarioEditando.email  = email;
            usuarioEditando.perfil = perfil;
            if (!senha.isEmpty()) {
                if (senha.length() < 6) {
                    Toast.makeText(this, "Senha deve ter ao menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                usuarioEditando.senha = Senhas.hashSenha(senha);
            }
            db.usuarioDAO().atualizar(usuarioEditando);
            Toast.makeText(this, "Usuário atualizado!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}