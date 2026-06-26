package com.example.myapplication;



import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Produto;
import com.example.myapplication.model.Requisicao;
import com.example.myapplication.util.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NovaRequisicaoActivity extends AppCompatActivity {

    private Spinner spinnerProduto, spinnerTipo;
    private TextInputEditText etQuantidade, etObservacao;
    private AppDatabase db;
    private SessionManager session;
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_requisicao);

        db      = AppDatabase.getInstance(this);
        session = new SessionManager(this);

        spinnerProduto = findViewById(R.id.spinnerProduto);
        spinnerTipo    = findViewById(R.id.spinnerTipo);
        etQuantidade   = findViewById(R.id.etQuantidade);
        etObservacao   = findViewById(R.id.etObservacao);

        carregarProdutos();

        // Tipo: SAIDA para todos, ENTRADA só para admin
        List<String> tipos = new ArrayList<>();
        tipos.add("SAIDA");
        if (session.isAdmin()) tipos.add("ENTRADA");

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, tipos);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterTipo);

        Button btnConfirmar = findViewById(R.id.btnConfirmar);
        Button btnCancelar  = findViewById(R.id.btnCancelar);

        btnConfirmar.setOnClickListener(v -> registrarRequisicao());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void carregarProdutos() {
        listaProdutos = db.produtoDAO().listarAtivos();
        List<String> nomes = new ArrayList<>();
        for (Produto p : listaProdutos) {
            nomes.add(p.nome + " (Qtd: " + p.quantidade + " " + p.unidade + ")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nomes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduto.setAdapter(adapter);
    }

    private void registrarRequisicao() {
        if (listaProdutos.isEmpty()) {
            Toast.makeText(this, "Nenhum produto cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }

        String qtdStr = etQuantidade.getText().toString().trim();
        if (qtdStr.isEmpty()) {
            Toast.makeText(this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(qtdStr);
        if (quantidade <= 0) {
            Toast.makeText(this, "Quantidade deve ser maior que zero", Toast.LENGTH_SHORT).show();
            return;
        }

        Produto produto = listaProdutos.get(spinnerProduto.getSelectedItemPosition());
        String tipo     = spinnerTipo.getSelectedItem().toString();
        String obs      = etObservacao.getText().toString().trim();

        // RN001 — não pode sair mais do que tem
        if ("SAIDA".equals(tipo) && quantidade > produto.quantidade) {
            Toast.makeText(this,
                    "Estoque insuficiente! Disponível: " + produto.quantidade + " " + produto.unidade,
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Atualiza o estoque
        if ("SAIDA".equals(tipo)) {
            produto.quantidade -= quantidade;
        } else {
            produto.quantidade += quantidade;
        }
        db.produtoDAO().atualizar(produto);

        // Registra a requisição
        String data = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date());

        Requisicao req = new Requisicao(
                session.getUsuarioId(),
                produto.id,
                produto.nome,
                session.getUsuarioNome(),
                quantidade,
                tipo,
                data,
                obs
        );
        db.requisicaoDAO().inserir(req);

        // Alerta de estoque mínimo
        if (produto.quantidade <= produto.quantidadeMinima) {
            Toast.makeText(this,
                    "⚠ ATENÇÃO: Estoque de " + produto.nome + " está baixo! (" + produto.quantidade + " restantes)",
                    Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "Requisição registrada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
