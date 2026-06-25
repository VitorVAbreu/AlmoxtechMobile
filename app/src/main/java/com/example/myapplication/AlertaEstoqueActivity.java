package com.example.myapplication;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almoxtech.R;
import com.example.myapplication.adapter.ProdutoAdapter;
import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Produto;

import java.util.List;

public class AlertaEstoqueActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta_estoque);

        db = AppDatabase.getInstance(this);

        recyclerView = findViewById(R.id.recyclerAlerta);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Produto> lista = db.produtoDAO().listarEstoqueBaixo();
        ProdutoAdapter adapter = new ProdutoAdapter(this, lista, new ProdutoAdapter.OnProdutoClickListener() {
            @Override
            public void onEditar(Produto produto) { }
            @Override
            public void onExcluir(Produto produto) { }
        });
        recyclerView.setAdapter(adapter);
    }
}
