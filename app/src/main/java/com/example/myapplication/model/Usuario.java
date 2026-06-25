package com.example.myapplication.model;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "tb_usuario",
        indices = {@Index(value = {"email"}, unique = true)}
)
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nome;
    public String email;
    public String senha;
    public String perfil;  // "ADMIN" ou "FUNCIONARIO"
    public int ativo;      // 1 = ativo, 0 = inativo

    public Usuario() {}

    public Usuario(String nome, String email, String senha, String perfil) {
        this.nome   = nome;
        this.email  = email;
        this.senha  = senha;
        this.perfil = perfil;
        this.ativo  = 1;
    }
}
