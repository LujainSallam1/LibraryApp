package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE })
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getAll(@RequestParam(required=false) String isbn) {
        if(isbn != null){
            return bookRepository.findByIsbn(isbn);
        }
        else {
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
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book orginalbook = optionalBook.get();
            orginalbook.setIsbn(book.getIsbn());
            orginalbook.setTitle(book.getTitle());
            orginalbook.setAuthors(book.getAuthors());
            orginalbook.setPublishDate(book.getPublishDate());
            orginalbook.setSummary(book.getSummary());

            Book updatedBookEntity = bookRepository.save(book);
            return ResponseEntity.ok(updatedBookEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @DeleteMapping("/books/{isbn}")
//    public ResponseEntity<Book> delet(@PathVariable String isbn) {
//        Optional<Book> optionalBook = bookRepository.findByIsbn((isbn));
//        if (optionalBook.isPresent()) {
//            bookRepository.deleteByIsbn(isbn);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PutMapping("/books/{id}/borrow")
    public boolean borrow(@PathVariable(value = "id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if (!book.isBorrowed()) {
                book.setBorrowed(true);
                bookRepository.save(book);
                return true;

            } else {
                if (book.isBorrowed()) {
                    book.setBorrowed(false);
                    bookRepository.save(book);
                    return true;
                }
            }
        }
        return false;
    }
}




