package br.com.escorpion.libraryapi.api.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class LoanDTO {

    private String isbn;
    private String customer;

}
