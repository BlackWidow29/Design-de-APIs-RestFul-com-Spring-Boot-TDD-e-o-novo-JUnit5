package br.com.escorpion.libraryapi.api.service;

import br.com.escorpion.libraryapi.api.model.entity.Book;
import br.com.escorpion.libraryapi.api.model.entity.Loan;
import br.com.escorpion.libraryapi.api.repository.LoanRepository;
import br.com.escorpion.libraryapi.api.service.impl.LoanServiceImpl;
import br.com.escorpion.libraryapi.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {

    LoanService loanService;
    @MockBean
    LoanRepository repository;

    @BeforeEach
    public void setUp(){
        loanService = new LoanServiceImpl(repository);
    }

    private Book createBook() {
        return Book.builder().id(1L).isbn("123").build();
    }

    private Loan createLoan(Book book) {
        Loan savingLoan = Loan.builder()
                .book(book)
                .customer("Fulano")
                .loanDate(LocalDate.now())
                .build();
        return savingLoan;
    }

    @Test
    @DisplayName("Deve salvar um emprestimo")
    public void saveLoanTest(){
        Book book = createBook();

        Loan savingLoan = createLoan(book);

        Loan savedLoan = Loan.builder()
                .id(1L)
                .book(book)
                .customer("Fulano")
                .loanDate(LocalDate.now())
                .build();

        when(repository.save(savingLoan)).thenReturn(savedLoan);

        Loan loan = loanService.save(savingLoan);

        assertThat(loan.getId()).isEqualTo(savedLoan.getId());
        assertThat(loan.getLoanDate()).isEqualTo(savedLoan.getLoanDate());
        assertThat(loan.getBook()).isEqualTo(savedLoan.getBook());
        assertThat(loan.getCustomer()).isEqualTo(savedLoan.getCustomer());
    }
    @Test
    @DisplayName("Deve lançar erro de negócio ao salvar um empréstimo com livro já emprestado")
    public void loanedBookSaveTest(){

        Book book = createBook();

        Loan savingLoan = createLoan(book);

        when(repository.existsByBookAndNotReturned(book)).thenReturn(true);

        Throwable exception = catchThrowable(() -> loanService.save(savingLoan));

        assertThat(exception)
                .hasMessage("Book already loaned")
                .isInstanceOf(BusinessException.class);

        verify(repository, never()).save(savingLoan);

    }

    @Test
    @DisplayName("Deve obter as informações do empréstimo pelo id")
    public void getLoanDetailsTest(){
        Long id = 1L;

        Book book = createBook();
        Loan loan = createLoan(book);
        loan.setId(id);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(loan));

        Optional<Loan> result = loanService.getById(id);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getCustomer()).isEqualTo(loan.getCustomer());
        assertThat(result.get().getLoanDate()).isEqualTo(loan.getLoanDate());
        assertThat(result.get().getBook()).isEqualTo(loan.getBook());

        verify(repository).findById(id);

    }

    @Test
    @DisplayName("Deve atualizar um empréstimo")
    public void updateLoanTest(){
        Book book = createBook();
        Loan loan = createLoan(book);
        loan.setId(1L);
        loan.setReturned(true);

        when(repository.save(loan)).thenReturn(loan);

        Loan updatedLoan = loanService.update(loan);

        assertThat(updatedLoan.isReturned()).isTrue();

        verify(repository).save(loan);
    }
}
