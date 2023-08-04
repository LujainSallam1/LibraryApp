package nl.first8.library.controller;


import nl.first8.library.domain.Book;
import nl.first8.library.domain.Lid;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.LidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/leden")
    @ResponseBody
    public List<Lid> getAll(){
        return lidRepository.findAll();
    }

    @GetMapping("/leden/{id}")
    public ResponseEntity<Lid> getById(@PathVariable(value = "id") Long id) {
        Lid lid = lidRepository.findById(id).get();
        return ResponseEntity.ok(lid);
    }

    @PostMapping("/leden")
    public Lid add(@RequestBody Lid lid) {
        Lid lidAdd = lidRepository.save(lid);
        return ResponseEntity.ok(lidAdd).getBody();
    }

    @PutMapping("/leden/{id}")
    public Lid update(@PathVariable(value = "id") Long id, @RequestBody Lid lid) {
        Optional<Lid> findLid = lidRepository.findById(id);
        if (!findLid.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "member not found");
        }

        Lid updatedLid = findLid.get();

        if (lid.getNaam() != null){
            updatedLid.setNaam(lid.getNaam());
        }
        if (lid.getAdres() != null){
            updatedLid.setAdres(lid.getAdres());
        }
        if (lid.getWoonplaats() != null){
            updatedLid.setWoonplaats(lid.getWoonplaats());
        }

        return lidRepository.save(updatedLid);
    }

    @PutMapping("/leden/{id}/inactive")
    public ResponseEntity<Lid> inactive(@PathVariable(value = "id") Long id) {
        Optional<Lid> findLid = lidRepository.findById(id);
        if (!findLid.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Lid updatedLid = findLid.get();
        if (!updatedLid.isActive()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        updatedLid.setActive(false);
        return ResponseEntity.ok(lidRepository.save(updatedLid));
    }

    @PutMapping("/leden/{id}/active")
    public ResponseEntity<Lid> active(@PathVariable(value = "id") Long id) {
        Optional<Lid> findLid = lidRepository.findById(id);
        if (!findLid.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Lid updatedLid = findLid.get();
        if (updatedLid.isActive()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        updatedLid.setActive(true);
        return ResponseEntity.ok(lidRepository.save(updatedLid));
    }

    @PutMapping("/leden/{id}/addbook/{book_id}")
    public ResponseEntity<Lid> borrowBook(@PathVariable(value = "id") Long id, @PathVariable(value = "book_id") Long book_id){
        Optional<Lid> findLid = lidRepository.findById(id);
        Optional<Book> findBook = bookRepository.findById(book_id);
        if (!findLid.isPresent() || !findBook.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Lid lid = findLid.get();
        Book book = findBook.get();
        if (lid.getBorrowedBooks().size() >= lid.getMaxProducts()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        lid.addBook(book);

        return ResponseEntity.ok(lidRepository.save(lid));
    }

    @PutMapping("/leden/{id}/removebook/{book_id}")
    public ResponseEntity<Lid> returnBook(@PathVariable(value = "id") Long id, @PathVariable(value = "book_id") Long book_id){
        Optional<Lid> findLid = lidRepository.findById(id);
        Optional<Book> findBook = bookRepository.findById(book_id);
        if (!findLid.isPresent() || !findBook.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Lid lid = findLid.get();
        Book book = findBook.get();

        lid.removeBook(book);

        return ResponseEntity.ok(lidRepository.save(lid));
    }

}
