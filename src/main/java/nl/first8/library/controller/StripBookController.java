package nl.first8.library.controller;

import nl.first8.library.Constants;
import nl.first8.library.domain.Leden;
import nl.first8.library.domain.StripBook;
import nl.first8.library.repository.LedenRepository;
import nl.first8.library.repository.StripBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class StripBookController {
    @Autowired
    private StripBookRepository stripBookRepository;

    @Autowired
    private LedenRepository lidRepository;

    @GetMapping("/stripbooks")
    public List<StripBook> getAll() {return stripBookRepository.findAll();
    }

    @GetMapping("/stripbooks/{id}")
    public ResponseEntity<StripBook> getById( @PathVariable(value = "id") Long id) {
        Optional<StripBook> stripBook = stripBookRepository.findById(id);
        return stripBook.map(ResponseEntity::ok).orElseThrow(() -> new RuntimeException("Record with id not found"));
    }

    @PostMapping("/stripbooks")
    public StripBook add(@RequestBody StripBook stripBook) {
        if (stripBookRepository.existsById(stripBook.getId())) {throw new RuntimeException(
                "Cannot add to repository. Record with primary key id already exists");}
        stripBookRepository.save(stripBook);
        return null;
    }

    @PutMapping("/stripbooks/{id}")
    public ResponseEntity<StripBook> update(@PathVariable(value = "id") Long id, @RequestBody StripBook stripBook) {
        StripBook updatedStripBook = stripBookRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Record with id not found"));
        updatedStripBook.setAuthors(stripBook.getAuthors());
        updatedStripBook.setBorrowed(stripBook.isBorrowed());
        updatedStripBook.setIsbn(stripBook.getIsbn());
        updatedStripBook.setTitle(stripBook.getTitle());
        updatedStripBook.setPublishDate(stripBook.getPublishDate());
        updatedStripBook.setBorrowed(stripBook.isBorrowed());
        updatedStripBook.setBorrowerId(stripBook.getBorrowerId());
        boolean checkLidId = lidRepository.existsById(stripBook.getBorrowerId());
        if (!checkLidId) {throw new RuntimeException("Update failed. Cannot find user with given id");}
        return ResponseEntity.ok(updatedStripBook);
    }

    @GetMapping("/stripbooks/isbn/{isbn}")
    public List<StripBook> getAllByIsbn ( @PathVariable(value = "isbn") String isbn) {
        return stripBookRepository.findAllByIsbn(isbn);
    }

    @DeleteMapping("/stripbooks/{isbn}")
    public void delete(@PathVariable( value = "isbn") String isbn) {
        stripBookRepository.deleteAllByIsbn(isbn);
    }

    @PutMapping("/stripbooks/{id}/borrow")
    public ResponseEntity<StripBook> borrow(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        StripBook updatedStripBook = stripBookRepository.getById(id);
        if (updatedStripBook.isBorrowed()) {
            throw new RuntimeException("Check out failed. Book is already checked out by someone else");}
        updatedStripBook.setBorrowed(true);
        updatedStripBook.setBorrowerId(lid.getId());
        Leden lidRequest = lidRepository.getById(lid.getId());
        if (lidRequest.getProductenGeleend() >= Constants.BORROWLIMIT) {
            throw new RuntimeException("Unable to process loan. Borrow limit has been reached");}
        lidRequest.setProductenGeleend(lid.getProductenGeleend() + 1);
        return ResponseEntity.ok(updatedStripBook);
    }

    @PutMapping("/stripbooks/{id}/handin")
    public ResponseEntity<StripBook> handin(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        StripBook updatedStripBook = stripBookRepository.getById(id);
        updatedStripBook.setBorrowed(false);
        updatedStripBook.setBorrowerId(lid.getId());
        Leden lidRequest = lidRepository.getById(lid.getId());
        if (!Objects.equals(lidRequest.getId(), updatedStripBook.getBorrowerId()))
        {throw new RuntimeException("Cannot hand in book. A different member is in possession of this item");}
        lidRequest.setProductenGeleend(lid.getProductenGeleend() - 1);
        return ResponseEntity.ok(updatedStripBook);
    }

    @PutMapping("/stripbooks/{id}/summary")
    public ResponseEntity<StripBook> update(@PathVariable(value = "id") Long id, String summary) {
        StripBook updatedStripBook = stripBookRepository.getById(id);
        updatedStripBook.setSummary(summary);
        return ResponseEntity.ok(updatedStripBook);
    }
}
