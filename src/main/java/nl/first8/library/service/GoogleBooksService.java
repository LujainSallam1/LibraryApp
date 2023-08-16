package nl.first8.library.service;

import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.GoogleBookApiResponse;

public class GoogleBooksService {

    private GoogleBookAdapter adapter;

    public Book searchBook(String isbn) {
        GoogleBookApiResponse response = null;
        Book book = adapter.adapt(response);
        return book;
    }
}
