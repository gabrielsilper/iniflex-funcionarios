package com.github.gabrielsilper.repository;

import com.github.gabrielsilper.model.Funcionario;

import java.util.List;
import java.util.Map;

public interface FuncionarioRepository {
    List<Funcionario> listarFuncionarios();
    Map<String, List<Funcionario>> listarFuncionariosPorFuncao();
    void removerFuncionarioPorNome(String nome);
    void aumentarSalarioFuncionarios(int percentual);
    List<Funcionario> listarFuncionariosPorMesAniversario(int... meses);
}
