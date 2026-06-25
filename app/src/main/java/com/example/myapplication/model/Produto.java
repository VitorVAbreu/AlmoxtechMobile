package com.example.myapplication.model;




import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_produto")
public class Produto {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nome;
    public String descricao;
    public String unidade;       // ex: "un", "kg", "m"
    public int quantidade;
    public int quantidadeMinima;
    public int ativo;            // 1 = ativo, 0 = inativo

    public Produto() {}

    public Produto(String nome, String descricao, String unidade,
                   int quantidade, int quantidadeMinima) {
        this.nome             = nome;
        this.descricao        = descricao;
        this.unidade          = unidade;
        this.quantidade       = quantidade;
        this.quantidadeMinima = quantidadeMinima;
        this.ativo            = 1;
    }
}