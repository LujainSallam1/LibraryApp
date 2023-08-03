package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById( @PathVariable(value = "id") Long id) {
        Book book = null; //TODO implement
        return ResponseEntity.ok(book);
    }

    @PostMapping("/books")
    public Book add(@RequestBody Book book) {
        return null;
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        //TODO
        Book updatedBook = null;
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/books/{isbn}")
    public Map<String, Boolean> delete(@PathVariable( value = "isbn") String isbn) {
        //TODO
        Map<String, Boolean> map = null;
        return map;
    }

    @PutMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        //TODO

        Book updatedBook = null;
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/books/{id}/handin")
    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
        //TODO

        Book updatedBook = null;
        return ResponseEntity.ok(updatedBook);
    }
}
