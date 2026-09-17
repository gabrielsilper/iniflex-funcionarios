package com.github.gabrielsilper.service;

import com.github.gabrielsilper.model.Funcionario;
import com.github.gabrielsilper.repository.FuncionarioRepository;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;

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
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Locale brLocale = Locale.of("pt", "BR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(brLocale);

        this.listarFuncionarios().forEach( funcionario -> {
            System.out.printf("Funcionário: %s, Data de nascimento: %s, Salário: %s, Função: %s%n",
                    funcionario.getNome(),
                    funcionario.getDataNascimento().format(dtFormatter),
                    numberFormat.format(funcionario.getSalario()),
                    funcionario.getFuncao()
            );
        });
    }

    public void aumentarSalarioFuncionarios(int percentual) {
        if (percentual < 0){
            System.out.println("Percentual inválido. Por favor, insira um valor maior que 0.");
        }

        funcionarioRepository.aumentarSalarioFuncionarios(percentual);
    }
}
