package com.github.gabrielsilper.funcionario.controller;

import com.github.gabrielsilper.common.util.FormatterUtils;
import com.github.gabrielsilper.funcionario.dto.NomeIdadeFuncionarioDTO;
import com.github.gabrielsilper.funcionario.model.Funcionario;
import com.github.gabrielsilper.funcionario.service.FuncionarioService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FuncionarioController {
    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public void imprimirFuncionariosPorMesesAniversario(int... meses) {
        System.out.println("Funcionários que fazem aniversário no mês " + Arrays.toString(meses) + ":");
        this.funcionarioService.listarFuncionariosPorMesesAniversaio(meses).forEach(this::imprimirFuncionario);
        System.out.println("\n");
    }

    public void imprimirFuncionarios() {
        System.out.println("Lista de funcionários:");
        this.funcionarioService.listarFuncionarios().forEach(this::imprimirFuncionario);
        System.out.println("\n");
    }

    public void imprimirFuncionarioPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = this.funcionarioService.listarFuncionariosPorFuncao();

        System.out.println("Lista de funcionários por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função - " + entry.getKey() + ":");
            entry.getValue().forEach(this::imprimirFuncionario);
            System.out.println("----------------------------");
        }
        System.out.println("\n");
    }

    public void imprimirFuncionarioMaisVelho() {
        NomeIdadeFuncionarioDTO nomeIdadeFuncionarioMaisVelho = this.funcionarioService.getNomeIdadeFuncionarioMaisVelho();
        if (nomeIdadeFuncionarioMaisVelho == null) {
            System.out.println("Não existe funcionário mais velho.");
        } else {
            System.out.println("Funcionário mais velho:");
            System.out.println("Nome: " + nomeIdadeFuncionarioMaisVelho.nome());
            System.out.println("Idade: " + nomeIdadeFuncionarioMaisVelho.idade());
        }
        System.out.println("\n");
    }

    public void imprimirFuncionariosOrdenadosPorNome() {
        System.out.println("Lista de funcionários ordenada por nome:");
        this.funcionarioService.listarFuncionariosOrdenadosPorNome().forEach(this::imprimirFuncionario);
        System.out.println("\n");
    }

    public void imprimirTotalSalarios() {
        BigDecimal totalSalarios = this.funcionarioService.getTotalSalarios();
        String totalSalariosStr = FormatterUtils.MOEDA_FORMATTER.format(totalSalarios);
        System.out.println("Total dos salários dos Funcionários: " + totalSalariosStr + "\n\n");
    }

    public void imprimirFuncionariosSalariosMinimos() {
        System.out.println("Funcionários e qtd. salários mínimos que ganha:");
        this.funcionarioService.listarFuncionariosComSalariosMinimos().forEach( nomeSalarios -> {
            System.out.printf("- Funcionário: %s, Salários Mínimos: %d%n",
                    nomeSalarios.nome(),
                    nomeSalarios.salariosMinimos());
        });
    }

    private void imprimirFuncionario(Funcionario funcionario) {
        System.out.printf("- Funcionário: %s, Data de nascimento: %s, Salário: %s, Função: %s%n",
                funcionario.getNome(),
                FormatterUtils.DATA_FORMATTER.format(funcionario.getDataNascimento()),
                FormatterUtils.MOEDA_FORMATTER.format(funcionario.getSalario()),
                funcionario.getFuncao()
        );
    }

    public void removerFuncionarioPorNome(String nome) {
        this.funcionarioService.removerFuncionarioPorNome(nome);
        System.out.println("Funcionário " + nome + " foi removido com sucesso.\n\n");
    }

    public void aumentarSalarioFuncionarios(int percentual) {
        this.funcionarioService.aumentarSalarioFuncionarios(percentual);
        System.out.println("Salários dos funcionários aumentados em " + percentual + "%.\n\n");
    }
}
