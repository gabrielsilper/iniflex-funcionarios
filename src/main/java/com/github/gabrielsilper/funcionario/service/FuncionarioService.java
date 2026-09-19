package com.github.gabrielsilper.funcionario.service;

import com.github.gabrielsilper.funcionario.dto.NomeIdadeFuncionarioDTO;
import com.github.gabrielsilper.funcionario.dto.NomeSalariosFuncionarioDTO;
import com.github.gabrielsilper.funcionario.model.Funcionario;
import com.github.gabrielsilper.funcionario.repository.FuncionarioRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FuncionarioService {
    private static final BigDecimal SALARIO_MINIMO = BigDecimal.valueOf(1212.00);
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
        if (percentual <= 0) {
            System.out.println("Percentual inválido. Por favor, insira um valor maior que 0.");
            return;
        }

        funcionarioRepository.atualizarSalarioFuncionarios(percentual);
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

    public Map<String, List<Funcionario>> listarFuncionariosPorFuncao() {
        return this.funcionarioRepository.listarFuncionariosPorFuncao();
    }

    public List<Funcionario> listarFuncionariosOrdenadosPorNome() {
        return this.funcionarioRepository.listarFuncionariosOrdenadosPorNome();
    }

    public BigDecimal getTotalSalarios() {
        return this.funcionarioRepository.getTotalSalarios();
    }

    public List<NomeSalariosFuncionarioDTO> listarFuncionariosComSalariosMinimos() {
        return this.listarFuncionarios().stream().map(funcionario -> {
            if (funcionario.getSalario() == null) {
                return new NomeSalariosFuncionarioDTO(funcionario.getNome(), 0);
            }

            int salariosMinimos = funcionario.getSalario()
                    .divide(SALARIO_MINIMO, 0, RoundingMode.DOWN)
                    .intValue();

            return new NomeSalariosFuncionarioDTO(funcionario.getNome(), salariosMinimos);
        }).toList();
    }
}
