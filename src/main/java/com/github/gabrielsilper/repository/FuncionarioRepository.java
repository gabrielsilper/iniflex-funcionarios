package com.github.gabrielsilper.repository;

import com.github.gabrielsilper.model.Funcionario;

import java.util.List;

public interface FuncionarioRepository {
    List<Funcionario> listarFuncionarios();

    void removerFuncionarioPorNome(String nome);
    void aumentarSalarioFuncionarios(int percentual);
}
