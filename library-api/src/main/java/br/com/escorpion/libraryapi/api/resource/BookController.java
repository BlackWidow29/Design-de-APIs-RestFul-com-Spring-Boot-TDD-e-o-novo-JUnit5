package br.com.escorpion.libraryapi.api.resource;

import br.com.escorpion.libraryapi.exception.BusinessException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import br.com.escorpion.libraryapi.api.dto.BookDTO;
import br.com.escorpion.libraryapi.api.model.entity.Book;
import br.com.escorpion.libraryapi.api.service.BookService;

import javax.validation.Valid;
import java.util.List;

import br.com.escorpion.libraryapi.api.exception.ApiErros;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    private final ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto) {
        var entity = modelMapper.map(dto, Book.class);
        entity = bookService.save(entity);
        return modelMapper.map(entity, BookDTO.class);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleValidationExceptions(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();

        return new ApiErros(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleBusinessException(BusinessException exception){
        return new ApiErros(exception);
    }
}
