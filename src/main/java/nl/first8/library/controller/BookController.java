package nl.first8.library.controller;

import nl.first8.library.domain.entity.Book;
import nl.first8.library.service.BookAdminService;
import nl.first8.library.service.BorrowReturnService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
public class BookController {
    @Autowired
    private BorrowReturnService borrowReturnService;
    @Autowired
    private BookAdminService bookAdminService;
    @PreAuthorize("hasRole('ROLE_USER')")
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
   @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/books")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book savedBook = bookAdminService.addBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book body) {
        Book book = bookAdminService.updateBook(id, body);
        return ResponseEntity.ok(book);
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







