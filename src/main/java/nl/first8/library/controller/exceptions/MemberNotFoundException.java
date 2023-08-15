package nl.first8.library.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(Long id){
        super("Member with ID " + id + " not found");
    }
}
