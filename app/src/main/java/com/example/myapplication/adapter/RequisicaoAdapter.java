package com.example.myapplication.adapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Requisicao;

import java.util.List;

public class RequisicaoAdapter extends RecyclerView.Adapter<RequisicaoAdapter.ViewHolder> {

    private final List<Requisicao> lista;
    private final Context context;

    public RequisicaoAdapter(Context context, List<Requisicao> lista) {
        this.context = context;
        this.lista   = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_requisicao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Requisicao r = lista.get(position);
        holder.tvProduto.setText(r.nomeProduto);
        holder.tvUsuario.setText("Por: " + r.nomeUsuario);
        holder.tvQuantidade.setText("Qtd: " + r.quantidade);
        holder.tvData.setText(r.data);
        holder.tvTipo.setText(r.tipo);

        if ("SAIDA".equals(r.tipo)) {
            holder.tvTipo.setTextColor(Color.parseColor("#C62828"));
            holder.tvTipo.setBackgroundColor(Color.parseColor("#FFEBEE"));
        } else {
            holder.tvTipo.setTextColor(Color.parseColor("#1B5E20"));
            holder.tvTipo.setBackgroundColor(Color.parseColor("#E8F5E9"));
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvProduto, tvUsuario, tvQuantidade, tvData, tvTipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProduto    = itemView.findViewById(R.id.tvProduto);
            tvUsuario    = itemView.findViewById(R.id.tvUsuario);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            tvData       = itemView.findViewById(R.id.tvData);
            tvTipo       = itemView.findViewById(R.id.tvTipo);
        }
    }
}