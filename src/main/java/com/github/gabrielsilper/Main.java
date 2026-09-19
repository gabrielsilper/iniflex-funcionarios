package com.github.gabrielsilper;

import com.github.gabrielsilper.funcionario.controller.FuncionarioController;
import com.github.gabrielsilper.funcionario.repository.FuncionarioRepository;
import com.github.gabrielsilper.funcionario.repository.FuncionarioRepositoryImpl;
import com.github.gabrielsilper.funcionario.service.FuncionarioService;

public class Main {
    public static void main(String[] args) {
        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        FuncionarioRepository funcionarioRepository = new FuncionarioRepositoryImpl();
        FuncionarioService funcionarioService = new FuncionarioService(funcionarioRepository);
        FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);

        // 3.2 – Remover o funcionário “João” da lista.
        funcionarioController.removerFuncionarioPorNome("João");

        // 3.3 - Imprimir todos os funcionários com todas suas informações, sendo que:
        //• informação de data deve ser exibido no formato dd/mm/aaaa;
        //• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
        funcionarioController.imprimirFuncionarios();

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        funcionarioController.aumentarSalarioFuncionarios(0);

        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        // 3.6 – Imprimir os funcionários, agrupados por função.
        funcionarioController.imprimirFuncionarioPorFuncao();

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        funcionarioController.imprimirFuncionariosPorMesesAniversario(10, 12);

        // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        funcionarioController.imprimirFuncionarioMaisVelho();

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética.
        funcionarioController.imprimirFuncionariosOrdenadosPorNome();

        // 3.11 – Imprimir o total dos salários dos funcionários.
        funcionarioController.imprimirTotalSalarios();

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        funcionarioController.imprimirFuncionariosSalariosMinimos();
    }
}