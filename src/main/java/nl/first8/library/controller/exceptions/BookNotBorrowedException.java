package nl.first8.library.controller.exceptions;

import nl.first8.library.domain.entity.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BookNotBorrowedException extends RuntimeException{
    public BookNotBorrowedException(Book book){
        super("Cannot return non-borrowed book " + book.getTitle() + " with ID " + book.getId() + ".");
    }
}