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
    @DisplayName("Deve salvar um livro")
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
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
    public void shouldNotSaveBookWithDuplicateISBN() {
        //cenario
        Book validBook = createValidBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString()))
                .thenReturn(true);

        //execucao
        String mensagemErro = "Isbn já cadastrado";

        Throwable exception = Assertions.catchThrowable(() -> bookService.save(validBook));
        Assertions.assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage(mensagemErro);
        Mockito.verify(bookRepository, Mockito.never()).save(validBook);
    }

    private Book createValidBook() {
        return Book.builder()
                .author("Autor")
                .title("Meu Livro")
                .isbn("1213212")
                .build();
    }
}