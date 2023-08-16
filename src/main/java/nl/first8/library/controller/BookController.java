package nl.first8.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.first8.library.controller.exceptions.GoogleBookNotFoundException;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.GoogleBookApiResponse;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping("/books")
    public List<Book> getAll(@RequestParam(required = false) String isbn) {
        if (isbn != null) {
            return bookRepository.findByIsbn(isbn);
        } else {
            return bookRepository.findAll();
        }
    }


    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return ResponseEntity.ok(book);
        } else
            return ResponseEntity.notFound().build();

    }

    @PostMapping("/books")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book savedbook = bookRepository.save(book);
        return ResponseEntity.ok(savedbook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book body) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book bookDB;

        if (bookOptional.isPresent()) {
            bookDB = bookOptional.get();

            if (Objects.nonNull(body.getIsbn())) bookDB.setIsbn(body.getIsbn());
            if (Objects.nonNull(body.getTitle())) bookDB.setTitle(body.getTitle());
            if (Objects.nonNull(body.getAuthors())) bookDB.setAuthors(body.getAuthors());
            if (Objects.nonNull(body.getPublishDate())) bookDB.setPublishDate(body.getPublishDate());
            if (Objects.nonNull(body.getSummary())) bookDB.setSummary(body.getSummary());

        } else {
            return ResponseEntity.notFound().build();
        }

        Book updatedBook = bookRepository.save(bookDB);
        return ResponseEntity.ok(updatedBook);
    }


    @PutMapping("/books/{id}/borrow")
    public boolean borrow(@PathVariable(value = "id") Long id) {
        /* Omar denkt dat het zo moet maybe
          public Optional<Book> borrow(@PathVariable(value = "id") Long id) {
            Optional<Book> optionalBook = BookAdminService.findById(id)              ( en dan later Optional<BookDTO> optionalBookDTO = BookAdminService.findById(id) )
            return optionalBook;

            dus in BookAdminService.findById(id) staat:

            Optional<Book> optionalBook = bookRepository.findById(id);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                if (!book.isBorrowed())
                    book.setReturnDate(LocalDate.now());
                book.setBorrowed(true);
                bookRepository.save(book);
                return book;
            }
            return book;
        */


        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (!book.isBorrowed())
                book.setReturnDate(LocalDate.now());
            book.setBorrowed(true);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @PutMapping("/books/{id}/handin")
    public boolean handin(@PathVariable(value = "id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.isBorrowed())
                book.setBorrowed(false);
            book.setBorrowDate(LocalDate.now());
            bookRepository.save(book);
        }
        return true;
    }
}







