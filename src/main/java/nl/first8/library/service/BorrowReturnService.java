package nl.first8.library.service;

import nl.first8.library.domain.entity.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowReturnService {
    private  BookRepository bookRepository;

    public boolean borrow(@PathVariable(value = "id") Long id) {

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
