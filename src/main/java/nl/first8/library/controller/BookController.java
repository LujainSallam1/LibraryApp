package nl.first8.library.controller;

import nl.first8.library.Constants;
import nl.first8.library.domain.Book;
import nl.first8.library.domain.Leden;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.LedenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LedenRepository lidRepository;

    // 1. Pas `BookController.java` aan zodat de methode `getAll` een lijst terug geeft met alle boeken.
    @GetMapping("/books")
    public List<Book> getAll() {return bookRepository.findAll();
    }

    // 2. Pas `BookController.java` aan zodat de methode `getById` een enkel boek
    // kan ophalen op basis van het technische ID
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById( @PathVariable(value = "id") Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok).orElseThrow(() -> new RuntimeException("Record with id not found"));
    }

    // 3. Pas `BookController.java` aan zodat de methode `add` een boek kan toevoegen aan het inventaris.
    @PostMapping("/books")
    public Book add(@RequestBody Book book) {
        if (bookRepository.existsById(book.getId())) {throw new RuntimeException(
                "Cannot add to repository. Record with primary key id already exists");}
        bookRepository.save(book);
        return null;
    }

    // 4. Pas `BookController.java` aan zodat de methode `update` een boek kan updaten.
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book book, Leden lid) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Record with id not found"));
        updatedBook.setAuthors(book.getAuthors());
        updatedBook.setBorrowed(book.isBorrowed());
        updatedBook.setIsbn(book.getIsbn());
        updatedBook.setTitle(book.getTitle());
        updatedBook.setPublishDate(book.getPublishDate());
        updatedBook.setBorrowed(book.isBorrowed());
        boolean checkLidId = lidRepository.existsById(book.getBorrowerId());
        if (!checkLidId) {throw new RuntimeException("Update failed. Cannot find borrower with given id");}
        return ResponseEntity.ok(updatedBook);
    }

    // 5. Pas `BookController.java` aan zodat de methode `delete` een boek kan verwijderen.
    @GetMapping("/books/isbn/{isbn}")
    public List<Book> getAllByIsbn ( @PathVariable(value = "isbn") String isbn) {
        return bookRepository.findAllByIsbn(isbn);
    }
    @DeleteMapping("/books/{isbn}")
    public void delete(@PathVariable( value = "isbn") String isbn) {
        bookRepository.deleteAllByIsbn(isbn);
    }

    // 6. Pas `BookController.java` aan zodat de methodes `borrow` en `return` de `borrowed` status aanpassen.
    @PutMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        Book updatedBook = bookRepository.getById(id);
        if (updatedBook.isBorrowed()) {
            throw new RuntimeException("Check out failed. Book is already checked out by someone else");}
        updatedBook.setBorrowed(true);
        updatedBook.setBorrowerId(lid.getId());
        Leden lidRequest = lidRepository.getById(lid.getId());
        if (lidRequest.getProductenGeleend() >= Constants.BORROWLIMIT) {
            throw new RuntimeException("Unable to process loan. Borrow limit has been reached");}
        lidRequest.setProductenGeleend(lid.getProductenGeleend() + 1);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/books/{id}/handin")
    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        Book updatedBook = bookRepository.getById(id);
        updatedBook.setBorrowed(false);
        updatedBook.setBorrowerId(lid.getId());
        Leden lidRequest = lidRepository.getById(lid.getId());
        if (!Objects.equals(lidRequest.getId(), updatedBook.getBorrowerId()))
        {throw new RuntimeException("Cannot hand in book. A different member is in possession of this item");}
        lidRequest.setProductenGeleend(lid.getProductenGeleend() - 1);
        return ResponseEntity.ok(updatedBook);
    }

    // (7.) Pas `BookController.java` aan zodat de `update` methode ook een samenvatting kan toevoegen
    @PutMapping("/books/{id}/summary")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, String summary) {
        Book updatedBook = bookRepository.getById(id);
        updatedBook.setSummary(summary);
        return ResponseEntity.ok(updatedBook);
    }
}
