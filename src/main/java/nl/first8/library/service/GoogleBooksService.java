package nl.first8.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.first8.library.controller.exceptions.GoogleBookNotFoundException;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.rest.GoogleBookApiResponse;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class GoogleBooksService {

    private GoogleBookAdapter adapter;

    public GoogleBooksService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Value("${google.books.api.key}")
    private String googleBooksApiKey;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ObjectMapper objectMapper;


    public Book handleNonExistingBook(String isbn) throws IOException {
        System.out.println("Book does not exist in our database");

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
        GoogleBookApiResponse response = objectMapper.readValue(url, GoogleBookApiResponse.class);

        if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
            GoogleBookApiResponse.GoogleBookItem item = response.getItems().get(0);
            GoogleBookApiResponse.GoogleBookVolumeInfo volumeInfo = item.getVolumeInfo();
            Book book = new Book();
            book.setTitle(volumeInfo.getTitle());
            if (volumeInfo.getAuthors() != null) {
                book.setAuthors(volumeInfo.getAuthors().toString());
            }
            if (volumeInfo.getPublishDate() != null) {
                book.setPublishDate(volumeInfo.getPublishDate());
            }
            book.setIsbn(isbn);
            bookRepository.save(book);
            System.out.println("Book saved successfully.");
            return book;

        } else {
            System.out.println("Book not found at google");
            throw new GoogleBookNotFoundException(isbn);
        }
    }
}
