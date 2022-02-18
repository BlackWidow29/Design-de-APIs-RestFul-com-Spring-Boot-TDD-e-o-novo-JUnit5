package org.escorpion;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class CadastroPessoaTest {

    CadastroPessoas cadastro;

    @Before
    public void setUp() {
        cadastro = new CadastroPessoas();
    }

    @Test
    public void deveCriarOCadastroDePessoas() {
        Assertions.assertThat(cadastro.getPessoas()).isEmpty();
    }

    @Test
    public void deveAdicionarUmaPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Isinha");
        cadastro.adicionar(pessoa);
        Assertions.assertThat(cadastro.getPessoas())
                .isNotEmpty()
                .hasSize(1)
                .contains(pessoa);
    }

    @Test(expected = PessoaSemNotException.class)
    public void naoDeveAdicionarPessoaComNomeVazio(){
        Pessoa pessoa = new Pessoa();
        cadastro.adicionar(pessoa);
    }

    @Test
    public void deveRemoverUmaPessoa(){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Isa");
        cadastro.adicionar(pessoa);

        cadastro.remover(pessoa);

        Assertions.assertThat(cadastro.getPessoas()).isEmpty();
    }

    @Test(expected = CadastroVazioException.class)
    public void deveLancarErroAoTentarRemoverPessoaInexistente(){
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Isa");

        cadastro.remover(pessoa);

        Assertions.assertThat(cadastro.getPessoas()).isEmpty();
    }
}
