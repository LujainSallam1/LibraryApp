package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
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
        return new ArrayList<>(bookRepository.findAll()); //TODO implement
    }
    

    @GetMapping("/books/{id}")
    public Book getByID(@PathVariable("id") String id){
        return bookRepository.findById(Long.valueOf(id)).get();//TODO implement
    }

    @PostMapping("/books")
    public Book add(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        Optional<Book> bookoud = bookRepository.findById(id);
        if (!bookoud.isPresent()){
            return null;
        }
        Book updateBook = bookoud.get();

        if (book.getIsbn() != null){
            updateBook.setIsbn(book.getIsbn());
        }
        if (book.getTitle() != null){
            updateBook.setTitle(book.getTitle());
        }
        if (book.getAuthors() != null){
            updateBook.setAuthors(book.getAuthors());
        }
        if (book.getPublishDate() != null){
            updateBook.setPublishDate(book.getPublishDate());
        }

        if (book.getSummary() != null) {
            updateBook.setSummary(book.getSummary());
        }

        return ResponseEntity.ok(bookRepository.save(updateBook));
    }

    @DeleteMapping("/books/{isbn}")
    public Map<String, Boolean> delete(@PathVariable( value = "isbn") String isbn) {
        Map<String, Boolean> map;
        bookRepository.deleteByIsbn(isbn);
        return null;
    }

    @PutMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        //TODO
        Optional<Book> findBook = bookRepository.findById(id);
        if (!findBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        Book updatedBook = findBook.get();
        if (updatedBook.isBorrowed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book had already borrowed");
        }
        updatedBook.setBorrowed(true);

        return ResponseEntity.ok(bookRepository.save(updatedBook));
    }

    @PutMapping("/books/{id}/handin")
    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
        //TODO

        Optional<Book> findBook = bookRepository.findById(id);
        if (!findBook.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        Book updatedBook = findBook.get();
        if (!updatedBook.isBorrowed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book had already handin");
        }
        updatedBook.setBorrowed(false);

        return ResponseEntity.ok(bookRepository.save(updatedBook));
    }
}
