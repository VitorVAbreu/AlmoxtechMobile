package com.example.myapplication.model;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RequisicaoDAO {

    @Insert
    long inserir(Requisicao requisicao);

    @Query("SELECT * FROM tb_requisicao ORDER BY id DESC")
    List<Requisicao> listarTodas();

    @Query("SELECT * FROM tb_requisicao WHERE usuarioId = :usuarioId ORDER BY id DESC")
    List<Requisicao> listarPorUsuario(int usuarioId);

    @Query("SELECT * FROM tb_requisicao WHERE produtoId = :produtoId ORDER BY id DESC")
    List<Requisicao> listarPorProduto(int produtoId);

    @Query("SELECT * FROM tb_requisicao WHERE tipo = 'SAIDA' ORDER BY id DESC")
    List<Requisicao> listarSaidas();

    @Query("SELECT * FROM tb_requisicao WHERE tipo = 'ENTRADA' ORDER BY id DESC")
    List<Requisicao> listarEntradas();
}
