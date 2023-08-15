package nl.first8.library.controller.exceptions;

import nl.first8.library.domain.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BookAlreadyBorrowedException extends RuntimeException{
    public BookAlreadyBorrowedException(Book book){
        super("Book " + book.getTitle() + " with ID " + book.getId() + " has already been borrowed.");
    }
}
