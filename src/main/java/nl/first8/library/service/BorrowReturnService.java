package nl.first8.library.service;

import nl.first8.library.domain.entity.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowReturnService {
    @Autowired
    private  BookRepository bookRepository;


    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (!book.isBorrowed()) {
                book.setBorrowed(true);
                book.setBorrowDate(LocalDate.now());
                book.setReturnDate(null);
                bookRepository.save(book);
                System.out.println("The borrowing has been completed successfully");
                return ResponseEntity.ok(book);
            } else {
                System.out.println("Book is already borrowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            System.out.println("Book not found");
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.isBorrowed()) {
                book.setBorrowed(false);
                book.setBorrowDate(LocalDate.now());
                bookRepository.save(book);
                System.out.println("Book returned successfully");
                return ResponseEntity.ok(book);
            } else {
                System.out.println("Book is not currently borrowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            System.out.println("Book not found");
            return ResponseEntity.notFound().build();
        }
    }


}
