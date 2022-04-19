package br.com.escorpion.libraryapi.api.service.impl;

import br.com.escorpion.libraryapi.api.model.entity.Loan;
import br.com.escorpion.libraryapi.api.repository.LoanRepository;
import br.com.escorpion.libraryapi.api.service.LoanService;
import br.com.escorpion.libraryapi.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RequiredArgsConstructor
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository repository;

    @Override
    public Loan save(Loan loan) {
        if (repository.existsByBookAndNotReturned(loan.getBook())) {
            throw new BusinessException("Book already loaned");
        }
        return repository.save(loan);
    }

    @Override
    public Optional<Loan> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Loan update(Loan loan) {
        return null;
    }
}
