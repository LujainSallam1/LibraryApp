package nl.first8.library.service;

import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.GoogleBookApiResponse;
import org.springframework.stereotype.Component;

@Component
public class GoogleBookAdapter {

    public Book adapt(GoogleBookApiResponse response) {
        return null;
    }

}
