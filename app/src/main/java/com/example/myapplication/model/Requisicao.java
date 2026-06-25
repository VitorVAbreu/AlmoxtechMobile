package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "tb_requisicao",
        foreignKeys = {
                @ForeignKey(entity = Usuario.class,
                        parentColumns = "id", childColumns = "usuarioId"),
                @ForeignKey(entity = Produto.class,
                        parentColumns = "id", childColumns = "produtoId")
        }
)
public class Requisicao {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int usuarioId;
    public int produtoId;
    public String nomeProduto;    // cópia para histórico
    public String nomeUsuario;    // cópia para histórico
    public int quantidade;
    public String tipo;           // "ENTRADA" ou "SAIDA"
    public String data;           // formato: "dd/MM/yyyy HH:mm"
    public String observacao;

    public Requisicao() {}

    public Requisicao(int usuarioId, int produtoId, String nomeProduto,
                      String nomeUsuario, int quantidade, String tipo,
                      String data, String observacao) {
        this.usuarioId   = usuarioId;
        this.produtoId   = produtoId;
        this.nomeProduto = nomeProduto;
        this.nomeUsuario = nomeUsuario;
        this.quantidade  = quantidade;
        this.tipo        = tipo;
        this.data        = data;
        this.observacao  = observacao;
    }
}