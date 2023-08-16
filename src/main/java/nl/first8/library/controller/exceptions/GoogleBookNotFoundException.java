package nl.first8.library.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class GoogleBookNotFoundException extends RuntimeException{
    public GoogleBookNotFoundException(String isbn){
        super("Book with ISBN " + isbn + " not found in GooglBooks");
    }
}
