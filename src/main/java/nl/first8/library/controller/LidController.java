package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.domain.Lid;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.LidRepository;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LidController {
    @Autowired
    private LidRepository lidRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/lid")
    public List<Lid> getAll() {
        return lidRepository.findAll();
    }

    @GetMapping("/lid/{id}")
    public ResponseEntity<Lid> getById( @PathVariable(value = "id") Long id) {
        Optional<Lid> lid = lidRepository.findById(id);

        if (!lid.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid not found!");
        return ResponseEntity.ok(lid.get());
    }

    @PostMapping("/lid")
    public Lid add(@RequestBody Lid lid) {
        lidRepository.save(lid);
        return lid;
    }

    @PutMapping("/lid/{id}")
    public ResponseEntity<Lid> update(@PathVariable(value = "id") Long id, @RequestBody Lid lid) {

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
        lidRepository.save(updatedLid);

        return ResponseEntity.ok(updatedLid);
    }

    @PutMapping("/lid/activate/{id}")
    public ResponseEntity<Lid> setActive(@PathVariable(value = "id") Long id) {
        Optional<Lid> findLid = lidRepository.findById(id);

        if (!findLid.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid not found!");

        Lid updatedLid = findLid.get();
        if (updatedLid.isActive())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lid already active");
        updatedLid.setActive(true);
        return ResponseEntity.ok(lidRepository.save(updatedLid));
    }

    @PutMapping("/lid/disable/{id}")
    public ResponseEntity<Lid> setInactive(@PathVariable(value = "id") Long id) {
        Optional<Lid> findLid = lidRepository.findById(id);

        if (!findLid.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lid not found!");

        Lid updatedLid = findLid.get();
        if (!updatedLid.isActive())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lid already inactive");
        updatedLid.setActive(false);
        return ResponseEntity.ok(lidRepository.save(updatedLid));
    }

    //Borrow book through /lid
    @PutMapping("lid/{id}/borrowbook/{book_id}")
    public ResponseEntity<Lid> setBorrowedBook(@PathVariable(value = "id") Long lidId, @PathVariable(value = "book_id") long bookId) {

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
        return ResponseEntity.ok(lidRepository.save(lid));
    }

    //Remove borrowed book through /lid
    @PutMapping("lid/{id}/handinbook/{book_id}")
    public ResponseEntity<Lid> removeBorrowedBook(@PathVariable(value = "id") Long id, @PathVariable(value = "book_id") long bookId) {

        Optional<Lid> findLid = lidRepository.findById(id);
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
        return ResponseEntity.ok(lidRepository.save(lid));
    }

}
