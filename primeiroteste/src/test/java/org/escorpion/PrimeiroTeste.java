package org.escorpion;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class PrimeiroTeste {

    Calculadora calculadora;

    @Before
    public void setUp(){
        calculadora = new Calculadora();
    }

    @Test
    public void deveFazerASomaDeDoisNumeros() {
        // cenario
        calculadora.setNumeros(10, 0);
        calculadora.setNumeros(5, 1);
        // execucao
        double resultado = calculadora.somar();
        // verificações
        Assertions.assertThat(resultado).isEqualTo(15);
    }

    @Test
    public void deveFazerASomaDeNNumeros() {
        // cenario
        calculadora.setNumeros(10, 0);
        calculadora.setNumeros(5, 1);
        // execucao
        calculadora.somar();
        calculadora.setNumeros(5, 1);
        calculadora.somar();
        calculadora.setNumeros(45, 1);
        calculadora.somar();

        // verificações
        Assertions.assertThat(calculadora.getNumero(0)).isEqualTo(65);
    }

    @Test
    public void deveFazerASomaDeNumerosNegativos() {
        // cenario
        calculadora.setNumeros(-10, 0);
        calculadora.setNumeros(5, 1);
        // execucao
        double resultado = calculadora.somar();

        // verificações
        Assertions.assertThat(resultado).isEqualTo(-5);
    }

    @Test
    public void deveFazerADivisaoDosNumeros() {
        // cenario
        calculadora.setNumeros(10, 0);
        calculadora.setNumeros(5, 1);
        // execucao
        double resultado = calculadora.dividir();
        // verificações
        Assertions.assertThat(resultado).isEqualTo(2);
    }
    
    @Test
    public void deveFazerADivisaoDeNumerosNegativos() {
        // cenario
        calculadora.setNumeros(20, 0);
        calculadora.setNumeros(-5, 1);
        // execucao
        double resultado = calculadora.dividir();
        // verificações
        Assertions.assertThat(resultado).isEqualTo(-4);
    }
    
    @Test(expected = RuntimeException.class)
    public void naoDeveRealizarADivisaoPorZero() {
        // cenario
        calculadora.setNumeros(10, 0);
        calculadora.setNumeros(0, 1);
        // execucao
        calculadora.dividir();
    }

    @Test
    public void deveFazerASubtracaoDeDoisNumeros() {
        // cenario
        calculadora.setNumeros(70, 0);
        calculadora.setNumeros(4, 1);
        // execucao
        double resultado = calculadora.subtrair();
        // verificações
        Assertions.assertThat(resultado).isEqualTo(66);
    }

    @Test
    public void deveFazerASubtracaoDeNNumeros() {
        // cenario
        calculadora.setNumeros(70, 0);
        calculadora.setNumeros(4, 1);
        // execucao
        double resultado = calculadora.subtrair();
        calculadora.setNumeros(10, 1);
        resultado = calculadora.subtrair();
        // verificações
        Assertions.assertThat(resultado).isEqualTo(56);
    }

    @Test
    public void deveFazerASubtracaoDeNumerosNegativos() {
        // cenario
        calculadora.setNumeros(70, 0);
        calculadora.setNumeros(-4, 1);
        // execucao
        double resultado = calculadora.subtrair();
        // verificações
        Assertions.assertThat(resultado).isEqualTo(74);
    }
}
