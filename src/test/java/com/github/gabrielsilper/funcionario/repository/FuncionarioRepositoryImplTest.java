package com.github.gabrielsilper.funcionario.repository;

import com.github.gabrielsilper.funcionario.model.Funcionario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FuncionarioRepositoryImplTest {
    @Test
    public void deveListarTodosFuncionariosDaListaPadrao() {
        // Arrange
        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl();

        // Act
        List<Funcionario> funcionariosListados = repository.listarFuncionarios();

        // Assert
        assertThat(funcionariosListados).hasSize(10);
        assertThat(funcionariosListados.getFirst().getNome()).isEqualTo("Maria");
        assertThat(funcionariosListados.getLast().getNome()).isEqualTo("Helena");
    }


    @Test
    public void deveListarTodosFuncionariosDeUmaLista() {
        // Arrange
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        // Act
        List<Funcionario> funcionariosListados = repository.listarFuncionarios();

        // Assert
        assertThat(funcionariosListados).hasSize(2);
        assertThat(funcionariosListados.getFirst()).isEqualTo(maria);
        assertThat(funcionariosListados.getLast()).isEqualTo(joao);
    }

    @Test
    public void deveRemoverUmFuncionarioExistenteDeUmaLista() {
        // Arrange
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        // Act
        repository.removerFuncionarioPorNome("joão");

        // Assert
        List<Funcionario> funcionariosListados = repository.listarFuncionarios();

        assertThat(funcionariosListados).hasSize(1);
        assertThat(funcionarios).doesNotContain(joao);
    }

    @Test
    public void naoDeveRemoverNenhumFuncionarioComNomeInexistenteNaLista() {
        // Arrange
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        // Act
        repository.removerFuncionarioPorNome("Pedro");

        // Assert
        List<Funcionario> funcionariosListados = repository.listarFuncionarios();

        assertThat(funcionariosListados).hasSize(2);
        assertThat(funcionarios).contains(maria);
        assertThat(funcionarios).contains(joao);
    }

    @Test
    public void deveAumentarSalarioDeAcordoComPercentual() {
        BigDecimal salarioInicialMaria = BigDecimal.valueOf(2000.00);
        BigDecimal salarioInicialJoao = BigDecimal.valueOf(1000.00);
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), salarioInicialMaria, "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), salarioInicialJoao, "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        repository.atualizarSalarioFuncionarios(10);

        assertThat(maria.getSalario()).isNotEqualTo(salarioInicialMaria);
        assertThat(maria.getSalario()).isEqualByComparingTo(BigDecimal.valueOf(2200.00));
        assertThat(joao.getSalario()).isNotEqualTo(salarioInicialJoao);
        assertThat(joao.getSalario()).isEqualByComparingTo(BigDecimal.valueOf(1100.00));
    }

    @Test
    public void deveDiminuirSalarioDeAcordoComPercentualNegativo() {
        BigDecimal salarioInicialMaria = BigDecimal.valueOf(2000.00);
        BigDecimal salarioInicialJoao = BigDecimal.valueOf(1000.00);
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), salarioInicialMaria, "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), salarioInicialJoao, "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        repository.atualizarSalarioFuncionarios(-10);

        assertThat(maria.getSalario()).isNotEqualTo(salarioInicialMaria);
        assertThat(maria.getSalario()).isEqualByComparingTo(BigDecimal.valueOf(1800.00));
        assertThat(joao.getSalario()).isNotEqualTo(salarioInicialJoao);
        assertThat(joao.getSalario()).isEqualByComparingTo(BigDecimal.valueOf(900.00));
    }

    @Test
    public void deveListarOsFuncionariosPorMesAniversario() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        List<Funcionario> funcionariosAniversariantes = repository.listarFuncionariosPorMesAniversario(10);

        assertThat(funcionariosAniversariantes).hasSize(1);
        assertThat(funcionariosAniversariantes).contains(maria);
    }

    @Test
    public void deveListarOsFuncionariosPorMesAniversarioComValoresNulosNaLista() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(null);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        List<Funcionario> funcionariosAniversariantes = repository.listarFuncionariosPorMesAniversario(10);

        assertThat(funcionariosAniversariantes).hasSize(1);
        assertThat(funcionariosAniversariantes).contains(maria);
    }

    @Test
    public void deveListarOsFuncionariosPorMesAniversarioComFuncionariosSemData() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", null, BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        List<Funcionario> funcionariosAniversariantes = repository.listarFuncionariosPorMesAniversario(10);

        assertThat(funcionariosAniversariantes).hasSize(1);
        assertThat(funcionariosAniversariantes).contains(maria);
    }

    @Test
    public void deveRetornarNullQuandoNaoHaFuncionariosNaLista() {
        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(new ArrayList<>());

        assertThat(repository.getFuncionarioMaisVelho()).isNull();
    }

    @Test
    public void deveRetornarFuncionarioMaisVelhoComDatasValidas() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getFuncionarioMaisVelho()).isEqualTo(joao);
    }

    @Test
    public void deveRetornarFuncionarioMaisVelhoComOMaisVelhoSendoPrimeiroDaLista() {
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(joao);
        funcionarios.add(maria);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getFuncionarioMaisVelho()).isEqualTo(joao);
    }

    @Test
    public void deveIgnorarFuncionariosNulosAoBuscarFuncionarioMaisVelho() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(null);
        funcionarios.add(null);
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getFuncionarioMaisVelho()).isEqualTo(joao);
    }

    @Test
    public void devePreferirFuncionarioComDataNascimentoQuandoAtualMaisVelhoNaoTemData() {
        Funcionario semData = new Funcionario("Sem Data", null, BigDecimal.valueOf(1500.00), "Operador");
        Funcionario semData2 = new Funcionario("Sem Data 2", null, BigDecimal.valueOf(1500.00), "Operador");
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(semData);
        funcionarios.add(semData2);
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getFuncionarioMaisVelho()).isEqualTo(joao);
    }

    @Test
    public void deveRetornarPrimeiroFuncionarioQuandoTodosNaoTemDataNascimento() {
        Funcionario semData1 = new Funcionario("Sem Data 1", null, BigDecimal.valueOf(1500.00), "Operador");
        Funcionario semData2 = new Funcionario("Sem Data 2", null, BigDecimal.valueOf(1700.00), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(semData1);
        funcionarios.add(semData2);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getFuncionarioMaisVelho()).isEqualTo(semData1);
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHaFuncionariosOrdenadosPorNome() {
        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(new ArrayList<>());

        assertThat(repository.listarFuncionariosOrdenadosPorNome()).isEmpty();
    }

    @Test
    public void deveOrdenarFuncionariosPorNomeEmOrdemAlfabetica() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");
        Funcionario alice = new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);
        funcionarios.add(alice);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.listarFuncionariosOrdenadosPorNome())
                .containsExactly(alice, joao, maria);
    }

    @Test
    public void deveColocarFuncionariosComNomeNuloNoFinalAoOrdenarPorNome() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario semNome = new Funcionario(null, LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");
        Funcionario alice = new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(semNome);
        funcionarios.add(alice);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.listarFuncionariosOrdenadosPorNome())
                .containsExactly(alice, maria, semNome);
    }

    @Test
    public void deveManterFuncionarioNuloNoFinalAoOrdenarPorNome() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(null);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.listarFuncionariosOrdenadosPorNome())
                .containsExactly(joao, maria, null);
    }

    @Test
    public void deveRetornarZeroQuandoListaDeFuncionariosEstiverVazia() {
        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(new ArrayList<>());

        assertThat(repository.getTotalSalarios()).isZero();
    }

    @Test
    public void deveSomarTodosOsSalariosDeFuncionariosValidos() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");
        Funcionario alice = new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);
        funcionarios.add(alice);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getTotalSalarios())
                .isEqualByComparingTo(BigDecimal.valueOf(6528.50));
    }

    @Test
    public void deveIgnorarFuncionariosNulosNaSomaTotal() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(null);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getTotalSalarios())
                .isEqualByComparingTo(BigDecimal.valueOf(4293.82));
    }

    @Test
    public void deveIgnorarSalariosNulosNaSomaTotal() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), null, "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getTotalSalarios())
                .isEqualByComparingTo(BigDecimal.valueOf(2009.44));
    }

    @Test
    public void deveSomarValoresNegativosEZeroSemErro() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(-500.00), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(0), "Operador");
        Funcionario alice = new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(250.00), "Recepcionista");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);
        funcionarios.add(alice);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.getTotalSalarios())
                .isEqualByComparingTo(BigDecimal.valueOf(-250.00));
    }

    @Test
    public void deveRetornarMapaVazioQuandoNaoHaFuncionarios() {
        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(new ArrayList<>());

        assertThat(repository.listarFuncionariosPorFuncao()).isEmpty();
    }

    @Test
    public void deveAgruparFuncionariosPorFuncao() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");
        Funcionario alice = new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);
        funcionarios.add(alice);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.listarFuncionariosPorFuncao())
                .containsKeys("Operador", "Recepcionista")
                .hasSize(2);
        assertThat(repository.listarFuncionariosPorFuncao().get("Operador"))
                .containsExactly(maria, joao);
        assertThat(repository.listarFuncionariosPorFuncao().get("Recepcionista"))
                .containsExactly(alice);
    }

    @Test
    public void deveUsarChaveSemCargoQuandoFuncionarioTemFuncaoNula() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), null);
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.listarFuncionariosPorFuncao()).containsKey("Sem cargo");
        assertThat(repository.listarFuncionariosPorFuncao().get("Sem cargo")).containsExactly(maria);
    }

    @Test
    public void deveIgnorarFuncionarioNuloAoAgruparPorFuncao() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(null);

        FuncionarioRepositoryImpl repository = new FuncionarioRepositoryImpl(funcionarios);

        assertThat(repository.listarFuncionariosPorFuncao()).containsKey("Operador").hasSize(1);
        assertThat(repository.listarFuncionariosPorFuncao().get("Operador")).containsExactly(maria);
    }
}