package nl.first8.library.service;

import nl.first8.library.domain.Book;
import nl.first8.library.domain.Lid;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.LidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LidService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LidRepository lidRepository;

    public List<Lid> getAllUsers() {
        return this.lidRepository.findAll();
    }

    public Lid getUser(Long id) {
        Optional<Lid> lid = lidRepository.findById(id);

        if (!lid.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid not found!");
        return lid.get();
    }

    public Lid addUser(Lid lid) {
        try {
            return this.lidRepository.save(lid);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong!");
        }
    }

    public Lid updateUser(Long id, Lid lid) {
        Optional<Lid> findLid = lidRepository.findById(id);

        if (!findLid.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid not found!");
        Lid updatedLid = findLid.get();

        if (lid.getVoornaam() != null)
            updatedLid.setVoornaam(lid.getVoornaam());
        if (lid.getAchternaam() != null)
            updatedLid.setAchternaam(lid.getAchternaam());
        if (lid.getAdress() != null)
            updatedLid.setAdress(lid.getAdress());
        if (lid.getWoonplaats() != null)
            updatedLid.setWoonplaats(lid.getWoonplaats());
        if (lid.getMaxLeenbareProducten() != null)
            updatedLid.setMaxLeenbareProducten(lid.getMaxLeenbareProducten());
        return lidRepository.save(updatedLid);
    }

    public Lid activateUser(Long id) {
        Optional<Lid> findLid = lidRepository.findById(id);

        if (!findLid.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid not found!");

        Lid updatedLid = findLid.get();
        if (updatedLid.isActive())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lid already active");
        updatedLid.setActive(true);
        return lidRepository.save(updatedLid);
    }

    public Lid disableLid(Long id) {
        Optional<Lid> findLid = lidRepository.findById(id);

        if (!findLid.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid not found!");

        Lid updatedLid = findLid.get();
        if (!updatedLid.isActive())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lid already inactive");
        updatedLid.setActive(false);
        return lidRepository.save(updatedLid);
    }

    public Lid borrowBook(Long lidId, Long bookId) {
        Optional<Lid> findLid = lidRepository.findById(lidId);
        Optional<Book> findBook = bookRepository.findById(bookId);
        if (!findLid.isPresent() || !findBook.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid / Book not found!");

        Lid lid = findLid.get();
        Book book = findBook.get();

        if (lid.getBorrowedBooks().size() >= lid.getMaxLeenbareProducten())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have too many books!");
        if (book.isBorrowed())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already borrowed!");
        book.setBorrowed(true);
        lid.addBorrowedBook(book);

        bookRepository.save(book);
        return lidRepository.save(lid);
    }

    public Lid handinBook(Long lidId, Long bookId) {
        Optional<Lid> findLid = lidRepository.findById(lidId);
        Optional<Book> findBook = bookRepository.findById(bookId);
        if (!findLid.isPresent() || !findBook.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid / Book not found!");

        Lid lid = findLid.get();
        Book book = findBook.get();

        if (!lid.getBorrowedBooks().contains(book))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You don't have this book!");
        lid.removeBorrowedBook(book);
        book.setBorrowed(false);

        bookRepository.save(book);
        return lidRepository.save(lid);
    }

}
