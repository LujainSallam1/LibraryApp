package nl.first8.library.controller.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long id){
        super("Book with ID " + id + " not found");
    }
}
