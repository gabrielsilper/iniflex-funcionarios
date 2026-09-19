package com.github.gabrielsilper.funcionario.repository;

import com.github.gabrielsilper.funcionario.model.Funcionario;
import com.github.gabrielsilper.funcionario.model.Pessoa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class FuncionarioRepositoryImpl implements FuncionarioRepository {
    private final ArrayList<Funcionario> funcionarios;

    public FuncionarioRepositoryImpl() {
        this.funcionarios = new ArrayList<>(List.of(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));
    }

    public FuncionarioRepositoryImpl(ArrayList<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public List<Funcionario> listarFuncionarios() {
        return this.funcionarios;
    }

    @Override
    public Map<String, List<Funcionario>> listarFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : this.listarFuncionarios()) {
            if (funcionario == null) {
                continue;
            }

            String chaveFuncao = funcionario.getFuncao() != null ? funcionario.getFuncao() : "Sem cargo";

            if (funcionariosPorFuncao.containsKey(chaveFuncao)) {
                funcionariosPorFuncao.get(chaveFuncao).add(funcionario);
            } else {
                List<Funcionario> funcionarios = new ArrayList<>();
                funcionarios.add(funcionario);
                funcionariosPorFuncao.put(chaveFuncao, funcionarios);
            }
        }

        return funcionariosPorFuncao;
    }

    @Override
    public void removerFuncionarioPorNome(String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equalsIgnoreCase(nome));
    }

    @Override
    public void atualizarSalarioFuncionarios(int percentual) {
        // salario = salario * (1 + (percentual / 100))
        funcionarios.forEach(funcionario -> {
            BigDecimal percentualDecimal = BigDecimal.valueOf(1 + (percentual / 100.0));
            BigDecimal novoSalario = funcionario.getSalario().multiply(percentualDecimal);
            funcionario.setSalario(novoSalario);
        });
    }

    @Override
    public List<Funcionario> listarFuncionariosPorMesAniversario(int... meses) {
        return this.funcionarios.stream()
                .filter(Objects::nonNull)
                .filter(funcionario -> {
                    if (funcionario.getDataNascimento() == null) {
                        return false;
                    }

                    int mesAniversario = funcionario.getDataNascimento().getMonthValue();
                    for (int mes : meses) {
                        if (mes == mesAniversario) {
                            return true;
                        }
                    }
                    return false;
                }).toList();
    }

    @Override
    public Funcionario getFuncionarioMaisVelho() {
        Funcionario funcionarioMaisVelho = null;

        for (Funcionario funcionario : this.funcionarios) {
            if (funcionario == null) {
                continue;
            }

            if (funcionarioMaisVelho == null) {
                funcionarioMaisVelho = funcionario;
                continue;
            }

            if (funcionarioMaisVelho.getDataNascimento() == null && funcionario.getDataNascimento() != null) {
                funcionarioMaisVelho = funcionario;
                continue;
            }

            if (funcionario.getDataNascimento() != null
                    && funcionario.getDataNascimento().isBefore(funcionarioMaisVelho.getDataNascimento())
            ) {
                funcionarioMaisVelho = funcionario;
            }
        }

        return funcionarioMaisVelho;
    }

    @Override
    public List<Funcionario> listarFuncionariosOrdenadosPorNome() {
        return this.funcionarios.stream()
                .sorted(
                        Comparator.nullsLast(
                                Comparator.comparing(
                                        Pessoa::getNome,
                                        Comparator.nullsLast(Comparator.naturalOrder())
                                )
                        )
                )
                .toList();
    }

    @Override
    public BigDecimal getTotalSalarios() {
        return this.funcionarios.stream()
                .filter(Objects::nonNull)
                .map(Funcionario::getSalario)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
