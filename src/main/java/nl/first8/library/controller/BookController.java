package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById( @PathVariable(value = "id") Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (!book.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        return ResponseEntity.ok(book.get());
    }

    @PostMapping("/books")
    public Book add(@RequestBody Book book) {
        bookRepository.save(book);
        return book;
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book book) {

        Optional<Book> findBook = bookRepository.findById(id);

        if (!findBook.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        Book updatedBook = findBook.get();

        if (book.getIsbn() != null)
            updatedBook.setIsbn(book.getIsbn());
        if (book.getTitle() != null)
            updatedBook.setTitle(book.getTitle());
        if (book.getAuthors() != null)
            updatedBook.setAuthors(book.getAuthors());
        if (book.getSummary() != null)
            updatedBook.setSummary(book.getSummary());
        if (book.getPublishDate() != null)
            updatedBook.setPublishDate(book.getPublishDate());
        bookRepository.save(updatedBook);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/books/{isbn}")
    public ResponseEntity<String> delete(@PathVariable( value = "isbn") String isbn) {

        Book findBook = bookRepository.findByIsbn(isbn);
        if (findBook == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find book!");
        }

        bookRepository.deleteByIsbn(isbn);
        return ResponseEntity.ok("Book has been removed!");
    }

    @PutMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        Optional<Book> updatedBook = bookRepository.findById(id);

        if (!updatedBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        } else if (updatedBook.get().isBorrowed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already borrowed!");
        }

        Book book = updatedBook.get();
        book.setBorrowed(true);
        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/books/{id}/handin")
    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
        Optional<Book> updatedBook = bookRepository.findById(id);

        if (!updatedBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        } else if (!updatedBook.get().isBorrowed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book is already handed in!");
        }

        Book book = updatedBook.get();
        book.setBorrowed(false);
        bookRepository.save(book);

        return ResponseEntity.ok(book);
    }
}
