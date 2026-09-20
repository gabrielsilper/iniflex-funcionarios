package com.github.gabrielsilper.funcionario.service;

import com.github.gabrielsilper.funcionario.dto.NomeIdadeFuncionarioDTO;
import com.github.gabrielsilper.funcionario.model.Funcionario;
import com.github.gabrielsilper.funcionario.repository.FuncionarioRepository;
import com.github.gabrielsilper.funcionario.repository.FuncionarioRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class FuncionarioServiceTest {

    @Test
    public void deveListarTodosOsFuncionarioAoChamarRepository() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");

        FuncionarioRepositoryImpl repository = mock(FuncionarioRepositoryImpl.class);

        when(repository.listarFuncionarios()).thenReturn(List.of(maria));

        FuncionarioService service = new FuncionarioService(repository);
        List<Funcionario> funcionarios = service.listarFuncionarios();

        verify(repository).listarFuncionarios();
        assertThat(funcionarios).hasSize(1);
        assertThat(funcionarios).contains(maria);
    }

    @Test
    public void deveRetornarTrueAoReceberTrueDoRepository() {
        FuncionarioRepositoryImpl repository = mock(FuncionarioRepositoryImpl.class);

        when(repository.removerFuncionarioPorNome(anyString())).thenReturn(true);

        FuncionarioService service = new FuncionarioService(repository);
        boolean resultado = service.removerFuncionarioPorNome("Maria");

        verify(repository).removerFuncionarioPorNome("Maria");
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveRetornarFalseAoReceberFalseDoRepository() {
        FuncionarioRepositoryImpl repository = mock(FuncionarioRepositoryImpl.class);

        when(repository.removerFuncionarioPorNome(anyString())).thenReturn(false);

        FuncionarioService service = new FuncionarioService(repository);
        boolean resultado = service.removerFuncionarioPorNome("Maria");

        verify(repository).removerFuncionarioPorNome("Maria");
        assertThat(resultado).isFalse();
    }

    @Test
    public void deveChamarRepositoryParaAplicarAumentoNoSalario() {
        FuncionarioRepositoryImpl repository = mock(FuncionarioRepositoryImpl.class);
        FuncionarioService service = new FuncionarioService(repository);

        service.aumentarSalarioFuncionarios(10);

        verify(repository).atualizarSalarioFuncionarios(10);
    }

    @Test
    public void deveLancarErroNaoDeveChamarRepositoryQuandoPercentualForZero() {
        FuncionarioRepository repository = mock(FuncionarioRepository.class);
        FuncionarioService service = new FuncionarioService(repository);

        assertThatThrownBy(() -> service.aumentarSalarioFuncionarios(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Percentual inválido. Por favor, insira um valor maior que 0.");

        verify(repository, never()).atualizarSalarioFuncionarios(0);
    }

    @Test
    public void deveLancarErroNaoDeveChamarRepositoryQuandoPercentualForNegativo() {
        FuncionarioRepository repository = mock(FuncionarioRepository.class);
        FuncionarioService service = new FuncionarioService(repository);

        assertThatThrownBy(() -> service.aumentarSalarioFuncionarios(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Percentual inválido. Por favor, insira um valor maior que 0.");

        verify(repository, never()).atualizarSalarioFuncionarios(-10);
    }

    @Test
    public void deveListarOsAniversariantesAoConsultarRepository() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");

        FuncionarioRepositoryImpl repository = mock(FuncionarioRepositoryImpl.class);

        when(repository.listarFuncionariosPorMesAniversario(5, 10)).thenReturn(List.of(maria, joao));

        FuncionarioService service = new FuncionarioService(repository);
        List<Funcionario> funcionarios = service.listarFuncionariosPorMesesAniversario(5, 10);

        verify(repository).listarFuncionariosPorMesAniversario(5, 10);
        assertThat(funcionarios).hasSize(2);
        assertThat(funcionarios).contains(maria);
        assertThat(funcionarios).contains(joao);
    }


    @Test
    public void deveRetornarSomenteNomeIdadeDoFuncionarioMaisVelho() {
        ZoneId zoneId = ZoneId.of("America/Manaus");

        Clock clock = Clock.fixed(
                LocalDate.of(2026, 9, 20)
                        .atStartOfDay(zoneId)
                        .toInstant(),
                zoneId
        );

        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador");

        FuncionarioRepositoryImpl repository = mock(FuncionarioRepositoryImpl.class);

        when(repository.getFuncionarioMaisVelho()).thenReturn(maria);

        FuncionarioService service = new FuncionarioService(repository, clock);
        NomeIdadeFuncionarioDTO maisVelho = service.getNomeIdadeFuncionarioMaisVelho();

        verify(repository).getFuncionarioMaisVelho();
        assertThat(maisVelho).isNotNull();
        assertThat(maisVelho.nome()).isEqualTo("Maria");
        assertThat(maisVelho.idade()).isPositive();
        assertThat(maisVelho.idade()).isEqualTo(25);
    }
}
