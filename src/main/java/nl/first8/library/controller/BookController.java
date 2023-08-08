package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/books/{bookid}")
    public ResponseEntity<Book> getById(@PathVariable(value = "bookid") Long bookid) {
        Book book = bookRepository.findById(bookid).get();
        return ResponseEntity.ok(book);
    }

    @PostMapping("/books")
    public Book add(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    @PutMapping("/books/{bookid}")
    public ResponseEntity<Book> update(@PathVariable(value = "bookid") Long bookid, @RequestBody Book book) {
        book.setId(bookid);
        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/books/{isbn}")
    public Map<String, Boolean> delete(@PathVariable(value = "isbn") String isbn) {
        bookRepository.deleteByIsbn(isbn);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @PutMapping("/books/{bookid}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "bookid") Long bookid) {
        Optional<Book> optionalBook = bookRepository.findById(bookid);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.isBorrowed()) {
                return ResponseEntity.notFound().build(); // POSSIBLE BETTER ERROR HANDLING, SINCE IT IS ACTUALLY FOUND, JUST NOT AVAILABLE
            }
            book.setBorrowed(true);
            Book updatedBook = bookRepository.save(book);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

        @PutMapping("/books/{bookid}/handin")
        public ResponseEntity<Book> handin (@PathVariable(value = "bookid") Long bookid){
            Optional<Book> optionalBook = bookRepository.findById(bookid);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                if (book.isBorrowed()) {
                    book.setBorrowed(false);
                    Book updatedBook = bookRepository.save(book);
                    return ResponseEntity.ok(updatedBook);
                }
                else{
                    return ResponseEntity.notFound().build(); // POSSIBLE BETTER ERROR HANDLING, SINCE IT IS ACTUALLY FOUND, JUST NOT BORROWED YET
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }
