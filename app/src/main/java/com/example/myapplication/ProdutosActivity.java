package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ProdutoAdapter;
import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Produto;
import com.example.myapplication.util.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProdutosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        db      = AppDatabase.getInstance(this);
        session = new SessionManager(this);

        recyclerView = findViewById(R.id.recyclerProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabNovoProduto);

        // Só admin pode cadastrar produto
        if (session.isAdmin()) {
            fab.setOnClickListener(v ->
                    startActivity(new Intent(this, CadastroProdutoActivity.class))
            );
        } else {
            fab.hide();
        }

        carregarProdutos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProdutos();
    }

    private void carregarProdutos() {
        List<Produto> lista = db.produtoDAO().listarAtivos();
        ProdutoAdapter adapter = new ProdutoAdapter(this, lista, new ProdutoAdapter.OnProdutoClickListener() {
            @Override
            public void onEditar(Produto produto) {
                if (!session.isAdmin()) {
                    Toast.makeText(ProdutosActivity.this, "Apenas admins podem editar", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(ProdutosActivity.this, CadastroProdutoActivity.class);
                intent.putExtra("produto_id", produto.id);
                startActivity(intent);
            }

            @Override
            public void onExcluir(Produto produto) {
                if (!session.isAdmin()) {
                    Toast.makeText(ProdutosActivity.this, "Apenas admins podem excluir", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(ProdutosActivity.this)
                        .setTitle("Excluir produto")
                        .setMessage("Deseja excluir " + produto.nome + "?")
                        .setPositiveButton("Excluir", (dialog, which) -> {
                            db.produtoDAO().desativar(produto.id);
                            Toast.makeText(ProdutosActivity.this, "Produto excluído", Toast.LENGTH_SHORT).show();
                            carregarProdutos();
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
