package nl.first8.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.first8.library.controller.exceptions.GoogleBookNotFoundException;
import nl.first8.library.domain.GoogleBookApiResponse;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ScannerController {

    @Autowired
    public ScannerController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Value("${google.books.api.key}")
    private String googleBooksApiKey;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ObjectMapper objectMapper;

    //TODO: rename endpoint, both here and in Python project
    @PostMapping("/searchbooks_and_add")
    public ResponseEntity<String> uploadBarcode(@RequestBody Map<String, String> payload) {
        String isbn = payload.get("barcode_info");
        String userid = payload.get("user_id");

        try {
            List<Book> foundBooks = bookRepository.findByIsbn(isbn);

            System.out.println("Book not found in our database.");
            if (foundBooks == null || foundBooks.isEmpty()) {
                return handleNonExistingBook(isbn);
            } else {
                return handleExistingBook(foundBooks.get(0));
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<String> handleNonExistingBook(String isbn) throws IOException {
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
            return ResponseEntity.ok("Book saved successfully.");

        } else {
            System.out.println("Book not found at google");
            throw new GoogleBookNotFoundException(isbn);
        }


    }

    private ResponseEntity<String> handleExistingBook(Book existingBook) {
        System.out.println("Book exists");
        if (existingBook.isBorrowed()) {
            System.out.println("Book is borrowed");
            existingBook.setReturnDate(LocalDate.now());
            existingBook.setBorrowDate(null);
            existingBook.setBorrowed(false);
            bookRepository.save(existingBook);
            System.out.println("Book returned successfully");
            return ResponseEntity.ok("Book returned successfully");
        } else {
            System.out.println("Book is not borrowed");
            existingBook.setReturnDate(null);
            existingBook.setBorrowDate(LocalDate.now());
            existingBook.setBorrowed(true);
            bookRepository.save(existingBook);
            System.out.println("Book borrowed successfully");
            return ResponseEntity.ok("Book borrowed successfully");
        }
    }

}
