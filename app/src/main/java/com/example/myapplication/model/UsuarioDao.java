package com.example.myapplication.model;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    long inserir(Usuario usuario);

    @Update
    void atualizar(Usuario usuario);

    @Query("SELECT * FROM tb_usuario WHERE email = :email AND senha = :senha AND ativo = 1 LIMIT 1")
    Usuario buscarPorEmailSenha(String email, String senha);

    @Query("SELECT * FROM tb_usuario WHERE email = :email LIMIT 1")
    Usuario buscarPorEmail(String email);

    @Query("SELECT * FROM tb_usuario WHERE id = :id LIMIT 1")
    Usuario buscarPorId(int id);

    @Query("SELECT * FROM tb_usuario WHERE ativo = 1 ORDER BY nome ASC")
    List<Usuario> listarAtivos();

    @Query("SELECT COUNT(*) FROM tb_usuario")
    int contarUsuarios();

    @Query("UPDATE tb_usuario SET ativo = 0 WHERE id = :id")
    void desativar(int id);
}