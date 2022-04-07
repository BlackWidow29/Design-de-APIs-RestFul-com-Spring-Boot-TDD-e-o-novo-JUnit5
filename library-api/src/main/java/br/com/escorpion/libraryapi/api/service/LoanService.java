package br.com.escorpion.libraryapi.api.service;

import br.com.escorpion.libraryapi.api.model.entity.Loan;

public interface LoanService {
    Loan save(Loan loan);
}
