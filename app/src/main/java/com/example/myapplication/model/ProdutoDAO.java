package com.example.myapplication.model;





import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProdutoDAO {

    @Insert
    long inserir(Produto produto);

    @Update
    void atualizar(Produto produto);

    @Query("SELECT * FROM tb_produto WHERE ativo = 1 ORDER BY nome ASC")
    List<Produto> listarAtivos();

    @Query("SELECT * FROM tb_produto WHERE id = :id LIMIT 1")
    Produto buscarPorId(int id);

    @Query("UPDATE tb_produto SET ativo = 0 WHERE id = :id")
    void desativar(int id);

    @Query("SELECT * FROM tb_produto WHERE quantidade <= quantidadeMinima AND ativo = 1")
    List<Produto> listarEstoqueBaixo();
}