package com.github.gabrielsilper.funcionario.service;

import com.github.gabrielsilper.funcionario.model.Funcionario;
import com.github.gabrielsilper.funcionario.repository.FuncionarioRepository;
import com.github.gabrielsilper.funcionario.repository.FuncionarioRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class FuncionarioServiceTest {

    @Test
    public void deveChamarRepositorioEAplicarAumentoNoSalarioComPercentualPositivo() {
        Funcionario maria = new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2000.00), "Operador");
        Funcionario joao = new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(1000.00), "Operador");

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(maria);
        funcionarios.add(joao);

        FuncionarioRepositoryImpl repository = spy(new FuncionarioRepositoryImpl(funcionarios));
        FuncionarioService service = new FuncionarioService(repository);

        service.aumentarSalarioFuncionarios(10);

        verify(repository).atualizarSalarioFuncionarios(10);
        assertThat(maria.getSalario()).isEqualByComparingTo(BigDecimal.valueOf(2200.00));
        assertThat(joao.getSalario()).isEqualByComparingTo(BigDecimal.valueOf(1100.00));
    }

    @Test
    public void deveLancarErroNaoDeveChamarRepositorioQuandoPercentualForZero() {
        FuncionarioRepository repository = mock(FuncionarioRepository.class);
        FuncionarioService service = new FuncionarioService(repository);

        assertThatThrownBy(() -> service.aumentarSalarioFuncionarios(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Percentual inválido. Por favor, insira um valor maior que 0.");

        verify(repository, never()).atualizarSalarioFuncionarios(0);
    }

    @Test
    public void deveLancarErroNaoDeveChamarRepositorioQuandoPercentualForNegativo() {
        FuncionarioRepository repository = mock(FuncionarioRepository.class);
        FuncionarioService service = new FuncionarioService(repository);

        assertThatThrownBy(() -> service.aumentarSalarioFuncionarios(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Percentual inválido. Por favor, insira um valor maior que 0.");

        verify(repository, never()).atualizarSalarioFuncionarios(-10);
    }
}
