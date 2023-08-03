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

import java.util.*;

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
    public ResponseEntity<Book> getById( @PathVariable(value = "id") Long id)  {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()){
            return ResponseEntity.ok(book.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/books")
    public Book add(@RequestBody Book book) {

        Book savedBook = bookRepository.save(book);

        return savedBook;
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book body) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book bookDB;

        if (bookOptional.isPresent()){
            bookDB = bookOptional.get();

            if (Objects.nonNull(body.getIsbn()))          bookDB.setIsbn(body.getIsbn());
            if (Objects.nonNull(body.getTitle()))         bookDB.setTitle(body.getTitle());
            if (Objects.nonNull(body.getAuthors()))       bookDB.setAuthors(body.getAuthors());
            if (Objects.nonNull(body.getPublishDate()))   bookDB.setPublishDate(body.getPublishDate());
            /* no need to check borrowed bc has default*/ bookDB.setBorrowed(body.isBorrowed());
        }
        else {
            return ResponseEntity.notFound().build();
        }

        Book updatedBook = bookRepository.save(bookDB);
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
