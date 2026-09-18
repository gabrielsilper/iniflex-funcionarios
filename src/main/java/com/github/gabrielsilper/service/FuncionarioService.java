package com.github.gabrielsilper.service;

import com.github.gabrielsilper.dto.NomeIdadeFuncionarioDTO;
import com.github.gabrielsilper.model.Funcionario;
import com.github.gabrielsilper.repository.FuncionarioRepository;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
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

    public void aumentarSalarioFuncionarios(int percentual) {
        if (percentual < 0) {
            System.out.println("Percentual inválido. Por favor, insira um valor maior que 0.");
        }

        funcionarioRepository.aumentarSalarioFuncionarios(percentual);
    }

    public List<Funcionario> listarFuncionariosPorMesesAniversaio(int... meses) {
        return this.funcionarioRepository.listarFuncionariosPorMesAniversario(meses);
    }

    public NomeIdadeFuncionarioDTO getNomeIdadeFuncionarioMaisVelho() {
        Funcionario funcionarioMaisVelho = this.funcionarioRepository.getFuncionarioMaisVelho();
        if (funcionarioMaisVelho == null) {
            return null;
        }

        if (funcionarioMaisVelho.getDataNascimento() == null) {
            return new NomeIdadeFuncionarioDTO(funcionarioMaisVelho.getNome(), -1);
        }

        int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();

        return new NomeIdadeFuncionarioDTO(funcionarioMaisVelho.getNome(), idade);
    }

    public List<Funcionario> listarFuncionariosOrdenadosPorNome() {
        return this.funcionarioRepository.listarFuncionariosOrdenadosPorNome();
    }

    public void imprimirFuncionariosPorMesesAniversario(int... meses) {
        System.out.println("Funcionários que fazem aniversário no mês " + Arrays.toString(meses) + ":");
        this.listarFuncionariosPorMesesAniversaio(meses).forEach(this::imprimirFuncionario);
        System.out.println("\n");
    }

    public void imprimirFuncionarios() {
        System.out.println("Lista de funcionários:");
        this.listarFuncionarios().forEach(this::imprimirFuncionario);
        System.out.println("\n");
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

    public void imprimirFuncionarioMaisVelho() {
        NomeIdadeFuncionarioDTO nomeIdadeFuncionarioMaisVelho = this.getNomeIdadeFuncionarioMaisVelho();
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
        this.listarFuncionariosOrdenadosPorNome().forEach(this::imprimirFuncionario);
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
