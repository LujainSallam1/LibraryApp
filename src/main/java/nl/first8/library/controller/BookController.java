package nl.first8.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.first8.library.controller.exceptions.GoogleBookNotFoundException;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.GoogleBookApiResponse;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.service.BookAdminService;
import nl.first8.library.service.BorrowReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowReturnService borrowReturnService;
    @Autowired
    private BookAdminService bookAdminService;

    @GetMapping("/books")
    public List<Book> getAll(@RequestParam(required = false) String isbn) {
       return bookAdminService.getAllBooks(isbn);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookAdminService.getBookById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/books")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book savedBook = bookAdminService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book body) {
        return bookAdminService.updateBook(id, body);
    }



    @PutMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        return borrowReturnService.borrow(id);
    }

    @PutMapping("/books/{id}/handin")
    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
      return borrowReturnService.handin(id);
}
}







