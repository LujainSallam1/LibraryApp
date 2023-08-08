package nl.first8.library.service;

import nl.first8.library.domain.Book;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (!book.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        return book.get();
    }

    public Book addBook(Book book) {
        return this.bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Optional<Book> findBook = bookRepository.findById(id);

        if (!findBook.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        Book updatedBook = findBook.get();

        if (book.getIsbn() != null)
            updatedBook.setIsbn(book.getIsbn());
        if (book.getTitle() != null)
            updatedBook.setTitle(book.getTitle());
        if (book.getAuthors() != null)
            updatedBook.setAuthors(book.getAuthors());
        if (book.getSummary() != null)
            updatedBook.setSummary(book.getSummary());
        if (book.getPublishDate() != null)
            updatedBook.setPublishDate(book.getPublishDate());
        return bookRepository.save(updatedBook);
    }

    public void deleteBookByIsbn(String isbn) {
        Book findBook = bookRepository.findByIsbn(isbn);
        if (findBook == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find book!");
        }

        bookRepository.deleteByIsbn(isbn);
    }

    public Book setBookStatusBorrowed(Long id) {
        Optional<Book> updatedBook = bookRepository.findById(id);

        if (!updatedBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        } else if (updatedBook.get().isBorrowed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already borrowed!");
        }

        Book book = updatedBook.get();
        book.setBorrowed(true);
        return bookRepository.save(book);
    }

    public Book setBookStatusHandin(Long id) {
        Optional<Book> updatedBook = bookRepository.findById(id);

        if (!updatedBook.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        } else if (!updatedBook.get().isBorrowed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book is already handed in!");
        }

        Book book = updatedBook.get();
        book.setBorrowed(false);
        return bookRepository.save(book);
    }
}
