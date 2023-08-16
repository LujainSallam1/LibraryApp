package nl.first8.library.service;

import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import nl.first8.library.domain.entity.Book;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class BookAdminService {
    @Autowired
    private BookRepository bookRepository;
        public List<Book> getAllBooks(String isbn) {
            if (isbn != null) {
                return bookRepository.findByIsbn(isbn);
            } else {
                return bookRepository.findAll();
            }
        }

        public Optional<Book> getBookById(Long id) {
            return bookRepository.findById(id);
        }

        public Book addBook(Book book) {
            return bookRepository.save(book);
        }

        public ResponseEntity<Book> updateBook(Long id, Book updatedBook) {
            Optional<Book> bookOptional = bookRepository.findById(id);

            if (bookOptional.isPresent()) {
                Book bookDB = bookOptional.get();

                if (Objects.nonNull(updatedBook.getIsbn())) bookDB.setIsbn(updatedBook.getIsbn());
                if (Objects.nonNull(updatedBook.getTitle())) bookDB.setTitle(updatedBook.getTitle());
                if (Objects.nonNull(updatedBook.getAuthors())) bookDB.setAuthors(updatedBook.getAuthors());
                if (Objects.nonNull(updatedBook.getPublishDate())) bookDB.setPublishDate(updatedBook.getPublishDate());
                if (Objects.nonNull(updatedBook.getSummary())) bookDB.setSummary(updatedBook.getSummary());

                Book savedBook = bookRepository.save(bookDB);
                return ResponseEntity.ok(savedBook);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }


