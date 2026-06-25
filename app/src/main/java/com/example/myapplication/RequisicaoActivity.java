package com.example.myapplication;



import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almoxtech.R;
import com.example.myapplication.adapter.RequisicaoAdapter;
import com.example.myapplication.model.AppDatabase;
import com.example.myapplication.model.Requisicao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RequisicaoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisicao);

        db = AppDatabase.getInstance(this);

        recyclerView = findViewById(R.id.recyclerRequisicoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fabNovaRequisicao);
        fab.setOnClickListener(v ->
                startActivity(new Intent(this, NovaRequisicaoActivity.class))
        );

        carregarRequisicoes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarRequisicoes();
    }

    private void carregarRequisicoes() {
        List<Requisicao> lista = db.requisicaoDAO().listarTodas();
        RequisicaoAdapter adapter = new RequisicaoAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}
