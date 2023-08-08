package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.service.BookService;
import org.apache.coyote.Response;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(service.getAllBooks());
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById( @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        return ResponseEntity.ok(service.addBook(book));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(id, book));
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<String> delete(@PathVariable( value = "isbn") String isbn) {
        service.deleteBookByIsbn(isbn);
        return ResponseEntity.ok("Book has been deleted!");
    }

    @PutMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.setBookStatusBorrowed(id));
    }

    @PutMapping("/books/{id}/handin")
    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
       return ResponseEntity.ok(service.setBookStatusHandin(id));
    }
}
