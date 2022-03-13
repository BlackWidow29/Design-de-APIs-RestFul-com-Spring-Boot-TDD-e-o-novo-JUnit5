package br.com.escorpion.libraryapi.api.service;

import br.com.escorpion.libraryapi.api.model.entity.Book;
import br.com.escorpion.libraryapi.api.repository.BookRepository;
import br.com.escorpion.libraryapi.api.service.impl.BookServiceImpl;
import br.com.escorpion.libraryapi.exception.BusinessException;
import com.sun.source.tree.LambdaExpressionTree;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class BookServiceTest {

    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        this.bookService = new BookServiceImpl(bookRepository);

    }

    @Test
    @DisplayName("Deve salvar um livro.")
    public void saveBookTest() {

        var book = createValidBook();

        var savedBookMock = Book.builder()
                .id(1L)
                .author("Autor")
                .title("Meu Livro")
                .isbn("1213212")
                .build();

        Mockito.when(bookRepository.save(book)).thenReturn(savedBookMock);

        var savedBook = bookService.save(book);

        assertNotNull(savedBook.getId());
        assertEquals("1213212", savedBook.getIsbn());
        assertEquals("Autor", savedBook.getAuthor());
        assertEquals("Meu Livro", savedBook.getTitle());
    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado.")
    public void shouldNotSaveBookWithDuplicateISBN() {
        //cenario
        Book validBook = createValidBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString()))
                .thenReturn(true);

        //execucao
        String mensagemErro = "Isbn já cadastrado";

        Throwable exception = Assertions.catchThrowable(() -> bookService.save(validBook));
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage(mensagemErro);
        Mockito.verify(bookRepository, Mockito.never()).save(validBook);
    }

    @Test
    @DisplayName("Deve obter um livro por id.")
    public void getByIdTest() {
        Long id = 1L;
        Book book = createValidBook();
        book.setId(id);
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Optional<Book> optionalBook = bookService.getById(id);

        assertThat(optionalBook.isPresent()).isTrue();
        assertThat(optionalBook.get().getId()).isEqualTo(id);
        assertThat(optionalBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(optionalBook.get().getIsbn()).isEqualTo(book.getIsbn());
        assertThat(optionalBook.get().getTitle()).isEqualTo(book.getTitle());
    }
    @Test
    @DisplayName("Deve retornar vazio ao obter um livro por id quando ele não existe na base.")
    public void bookNotFoundByIdTest() {
        Long id = 1L;
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Book> optionalBook = bookService.getById(id);

        assertThat(optionalBook.isPresent()).isFalse();
    }


    private Book createValidBook() {
        return Book.builder()
                .author("Autor")
                .title("Meu Livro")
                .isbn("1213212")
                .build();
    }
}