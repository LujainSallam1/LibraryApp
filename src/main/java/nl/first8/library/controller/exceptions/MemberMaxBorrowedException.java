package nl.first8.library.controller.exceptions;

import nl.first8.library.domain.Book;
import nl.first8.library.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MemberMaxBorrowedException extends RuntimeException{
    public MemberMaxBorrowedException(Long id){
        super("Member " + id + " had already reached the maximum amount of borrowed books.");
    }
}
