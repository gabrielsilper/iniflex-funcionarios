package com.github.gabrielsilper;

import com.github.gabrielsilper.repository.FuncionarioRepository;
import com.github.gabrielsilper.repository.FuncionarioRepositoryImpl;
import com.github.gabrielsilper.service.FuncionarioService;

public class Main {
    public static void main(String[] args) {
        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        FuncionarioRepository funcionarioRepository = new FuncionarioRepositoryImpl();
        FuncionarioService funcionarioService = new FuncionarioService(funcionarioRepository);

        // 3.2 – Remover o funcionário “João” da lista.
        funcionarioService.removerFuncionarioPorNome("João");

        // 3.3 - Imprimir todos os funcionários com todas suas informações, sendo que:
        //• informação de data deve ser exibido no formato dd/mm/aaaa;
        //• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
        funcionarioService.imprimirFuncionarios();

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        funcionarioService.aumentarSalarioFuncionarios(10);

        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        // 3.6 – Imprimir os funcionários, agrupados por função.
        funcionarioService.imprimirFuncionarioPorFuncao();
    }
}