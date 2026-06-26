package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.model.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {

    public interface OnProdutoClickListener {
        void onEditar(Produto produto);
        void onExcluir(Produto produto);
    }

    private final List<Produto> lista;
    private final Context context;
    private final OnProdutoClickListener listener;

    public ProdutoAdapter(Context context, List<Produto> lista, OnProdutoClickListener listener) {
        this.context  = context;
        this.lista    = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto p = lista.get(position);
        holder.tvNome.setText(p.nome);
        holder.tvQuantidade.setText("Qtd: " + p.quantidade + " " + p.unidade);
        holder.tvMinimo.setText("Mínimo: " + p.quantidadeMinima);

        // Destaque vermelho se estoque baixo
        if (p.quantidade <= p.quantidadeMinima) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFEBEE"));
            holder.tvQuantidade.setTextColor(Color.parseColor("#C62828"));
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvQuantidade.setTextColor(Color.parseColor("#0D1B2A"));
        }

        holder.tvEditar.setOnClickListener(v -> listener.onEditar(p));
        holder.tvExcluir.setOnClickListener(v -> listener.onExcluir(p));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvQuantidade, tvMinimo, tvEditar, tvExcluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome       = itemView.findViewById(R.id.tvNome);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            tvMinimo     = itemView.findViewById(R.id.tvMinimo);
            tvEditar     = itemView.findViewById(R.id.tvEditar);
            tvExcluir    = itemView.findViewById(R.id.tvExcluir);
        }
    }
}
