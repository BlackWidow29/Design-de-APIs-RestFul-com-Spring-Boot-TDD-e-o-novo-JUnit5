package br.com.escorpion.libraryapi.api.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table
public class Loan {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String isbn;

    @Column
    private String customer;

    @JoinColumn
    @ManyToOne
    private Book book;

    @Column
    private LocalDate loanDate;

    @Column
    private boolean returned;
}
