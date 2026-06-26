package com.example.myapplication;



import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Produto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroProdutoActivity extends AppCompatActivity {

    private TextInputEditText etNome, etDescricao, etUnidade, etQuantidade, etQuantidadeMinima;
    private AppDatabase db;
    private Produto produtoEditando = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);

        db = AppDatabase.getInstance(this);

        etNome             = findViewById(R.id.etNome);
        etDescricao        = findViewById(R.id.etDescricao);
        etUnidade          = findViewById(R.id.etUnidade);
        etQuantidade       = findViewById(R.id.etQuantidade);
        etQuantidadeMinima = findViewById(R.id.etQuantidadeMinima);

        int produtoId = getIntent().getIntExtra("produto_id", -1);
        if (produtoId != -1) {
            produtoEditando = db.produtoDAO().buscarPorId(produtoId);
            preencherCampos();
        }

        Button btnSalvar   = findViewById(R.id.btnSalvar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnSalvar.setOnClickListener(v -> salvar());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void preencherCampos() {
        etNome.setText(produtoEditando.nome);
        etDescricao.setText(produtoEditando.descricao);
        etUnidade.setText(produtoEditando.unidade);
        etQuantidade.setText(String.valueOf(produtoEditando.quantidade));
        etQuantidadeMinima.setText(String.valueOf(produtoEditando.quantidadeMinima));
    }

    private void salvar() {
        String nome      = etNome.getText().toString().trim();
        String descricao = etDescricao.getText().toString().trim();
        String unidade   = etUnidade.getText().toString().trim();
        String qtdStr    = etQuantidade.getText().toString().trim();
        String minStr    = etQuantidadeMinima.getText().toString().trim();

        if (nome.isEmpty() || unidade.isEmpty() || qtdStr.isEmpty() || minStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade       = Integer.parseInt(qtdStr);
        int quantidadeMinima = Integer.parseInt(minStr);

        if (produtoEditando == null) {
            Produto novo = new Produto(nome, descricao, unidade, quantidade, quantidadeMinima);
            db.produtoDAO().inserir(novo);
            Toast.makeText(this, "Produto cadastrado!", Toast.LENGTH_SHORT).show();
        } else {
            produtoEditando.nome             = nome;
            produtoEditando.descricao        = descricao;
            produtoEditando.unidade          = unidade;
            produtoEditando.quantidade       = quantidade;
            produtoEditando.quantidadeMinima = quantidadeMinima;
            db.produtoDAO().atualizar(produtoEditando);
            Toast.makeText(this, "Produto atualizado!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
