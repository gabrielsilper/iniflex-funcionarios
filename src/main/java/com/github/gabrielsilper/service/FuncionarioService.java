package com.github.gabrielsilper.service;

import com.github.gabrielsilper.model.Funcionario;
import com.github.gabrielsilper.repository.FuncionarioRepository;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final Locale brLocale = Locale.of("pt", "BR");
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(brLocale);

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.listarFuncionarios();
    }

    public void removerFuncionarioPorNome(String nome) {
        funcionarioRepository.removerFuncionarioPorNome(nome);
    }

    public void imprimirFuncionarios() {
        System.out.println("Lista de funcionários:");
        this.listarFuncionarios().forEach(this::imprimirFuncionario);
        System.out.println("\n");
    }

    public void aumentarSalarioFuncionarios(int percentual) {
        if (percentual < 0) {
            System.out.println("Percentual inválido. Por favor, insira um valor maior que 0.");
        }

        funcionarioRepository.aumentarSalarioFuncionarios(percentual);
    }

    public void imprimirFuncionarioPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = this.funcionarioRepository.listarFuncionariosPorFuncao();

        System.out.println("Lista de funcionários por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função - " + entry.getKey() + ":");
            entry.getValue().forEach(this::imprimirFuncionario);
            System.out.println("----------------------------");
        }
        System.out.println("\n");
    }

    public void imprimirFuncionariosPorMesesAniversario(int... meses) {
        System.out.println("Funcionários que fazem aniversário no mês " + Arrays.toString(meses) + ":");
        this.funcionarioRepository.listarFuncionariosPorMesAniversario(meses).forEach(this::imprimirFuncionario);
        System.out.println("\n");
    }

    private void imprimirFuncionario(Funcionario funcionario) {
        System.out.printf("- Funcionário: %s, Data de nascimento: %s, Salário: %s, Função: %s%n",
                funcionario.getNome(),
                funcionario.getDataNascimento().format(this.dtFormatter),
                this.numberFormat.format(funcionario.getSalario()),
                funcionario.getFuncao()
        );
    }
}
