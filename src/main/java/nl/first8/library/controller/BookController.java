package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    @ResponseBody
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById(@PathVariable(value = "id") Long id) {
        Book book = bookRepository.findById(id).get(); //TODO implement
        return ResponseEntity.ok(book);

    }

    @PostMapping("/books")
    public Book add(@RequestBody Book book) {
        Book bookAdd = bookRepository.save(book);
        return ResponseEntity.ok(bookAdd).getBody();
    }

    @PutMapping("/books/{id}")
    public Book update(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        Optional<Book> findBook = bookRepository.findById(id);
        if (!findBook.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found");
        }

        Book updatedBook = findBook.get();

        if (book.getIsbn() != null){
            updatedBook.setIsbn(book.getIsbn());
        }
        if (book.getTitle() != null){
            updatedBook.setTitle(book.getTitle());
        }
        if (book.getAuthors() != null){
            updatedBook.setAuthors(book.getAuthors());
        }
        if (book.getPublishDate() != null){
            updatedBook.setPublishDate(book.getPublishDate());
        }
        if (book.getSummary() != null){
            updatedBook.setSummary(book.getSummary());
        }

        return bookRepository.save(updatedBook);
    }

    @DeleteMapping("/books/{isbn}")
    public Map<String, Boolean> delete(@PathVariable( value = "isbn") String isbn) {
        //TODO
        Map<String, Boolean> map ;
        bookRepository.deleteByIsbn(isbn);
        return null;
    }

    @PutMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        //TODO

        Optional<Book> findBook = bookRepository.findById(id);
        if (!findBook.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found");
        }

        Book updatedBook = findBook.get();
        if (updatedBook.isBorrowed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "book has already been borrowed out");
        }
        updatedBook.setBorrowed(true);

        return ResponseEntity.ok(bookRepository.save(updatedBook));
    }

    @PutMapping("/books/{id}/handin")
    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
        //TODO
        Optional<Book> findBook = bookRepository.findById(id);
        if (!findBook.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found");
        }

        Book updatedBook = findBook.get();
        if (!updatedBook.isBorrowed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "book has already been handed in");
        }
        updatedBook.setBorrowed(false);

        return ResponseEntity.ok(bookRepository.save(updatedBook));
    }
}
