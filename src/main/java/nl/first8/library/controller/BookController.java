package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
import org.apache.coyote.Response;
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

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> allBook = bookRepository.findAll();
        return ResponseEntity.ok(allBook);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById( @PathVariable(value = "id") Long id) {
        Book book = bookRepository.findById(id).get();
        return ResponseEntity.ok(book);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book createBook = bookRepository.save(book);
        return ResponseEntity.ok(createBook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        Optional<Book> bookoud = bookRepository.findById(id);
        if (!bookoud.isPresent()) {
            return null;
        }
        Book updateBook = bookoud.get();

        if (book.getIsbn() != null) {
            updateBook.setIsbn(book.getIsbn());
        }

        if (book.getTitle() != null) {
            updateBook.setTitle(book.getTitle());
        }

        if (book.getAuthors() != null) {
            updateBook.setAuthors(book.getAuthors());
        }

        if (book.getPublishDate() != null) {
            updateBook.setPublishDate(book.getPublishDate());
        }

        updateBook.setBorrowed(book.isBorrowed());

        if (book.getSummary() != null) {
            updateBook.setSummary(book.getSummary());
        }

        return ResponseEntity.ok(bookRepository.save(updateBook));
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<String> delete(@PathVariable( value = "isbn") String isbn) {
       bookRepository.deleteByIsbn(isbn);
       return ResponseEntity.ok("Gelukt");
    }

    @PutMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        Optional<Book> bookoud = bookRepository.findById(id);
        if (!bookoud.isPresent()) {
            return null;
        }
        Book updateBook = bookoud.get();

        if (updateBook.isBorrowed() == false) {
            updateBook.setBorrowed(true);
        } else if (updateBook.isBorrowed() == true) {
            updateBook.setBorrowed(false);
        }
        return ResponseEntity.ok(bookRepository.save(updateBook));
    }

    @PutMapping("/books/{id}/handin")
    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
        //TODO

        Book updatedBook = null;
        return ResponseEntity.ok(updatedBook);
    }
}
