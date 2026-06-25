package com.example.myapplication.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.R;
import com.example.myapplication.model.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    public interface OnUsuarioClickListener {
        void onEditar(Usuario usuario);
        void onExcluir(Usuario usuario);
    }

    private final List<Usuario> lista;
    private final Context context;
    private final OnUsuarioClickListener listener;

    public UsuarioAdapter(Context context, List<Usuario> lista, OnUsuarioClickListener listener) {
        this.context  = context;
        this.lista    = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Usuario u = lista.get(position);
        holder.tvNome.setText(u.nome);
        holder.tvEmail.setText(u.email);
        holder.tvPerfil.setText(u.perfil);

        holder.tvEditar.setOnClickListener(v -> listener.onEditar(u));
        holder.tvExcluir.setOnClickListener(v -> listener.onExcluir(u));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvEmail, tvPerfil, tvEditar, tvExcluir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome   = itemView.findViewById(R.id.tvNome);
            tvEmail  = itemView.findViewById(R.id.tvEmail);
            tvPerfil = itemView.findViewById(R.id.tvPerfil);
            tvEditar = itemView.findViewById(R.id.tvEditar);
            tvExcluir = itemView.findViewById(R.id.tvExcluir);
        }
    }
}
