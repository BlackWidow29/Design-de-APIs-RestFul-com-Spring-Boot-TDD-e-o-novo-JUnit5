package br.com.escorpion.libraryapi.api.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDTO {

    private String isbn;
    private String customer;

}
