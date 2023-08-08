package nl.first8.library.controller;

import nl.first8.library.domain.Lid;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.LidRepository;
import nl.first8.library.service.LidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LidController {
    @Autowired
    private LidService service;

    @GetMapping("/lid")
    public ResponseEntity<List<Lid>> getAll() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @GetMapping("/lid/{id}")
    public ResponseEntity<Lid> getById( @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.getUser(id));
    }

    @PostMapping("/lid")
    public ResponseEntity<Lid> add(@RequestBody Lid lid) {
        return ResponseEntity.ok(service.addUser(lid));
    }

    @PutMapping("/lid/{id}")
    public ResponseEntity<Lid> update(@PathVariable(value = "id") Long id, @RequestBody Lid lid) {
        return ResponseEntity.ok(this.service.updateUser(id, lid));
    }

    @PutMapping("/lid/activate/{id}")
    public ResponseEntity<Lid> setActive(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.activateUser(id));
    }

    @PutMapping("/lid/disable/{id}")
    public ResponseEntity<Lid> setInactive(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(service.disableLid(id));
    }

    //Borrow book through /lid
    @PutMapping("lid/{id}/borrowbook/{book_id}")
    public ResponseEntity<Lid> setBorrowedBook(@PathVariable(value = "id") Long lidId, @PathVariable(value = "book_id") long bookId) {
        return ResponseEntity.ok(service.borrowBook(lidId, bookId));
    }

    //Remove borrowed book through /lid
    @PutMapping("lid/{id}/handinbook/{book_id}")
    public ResponseEntity<Lid> removeBorrowedBook(@PathVariable(value = "id") Long id, @PathVariable(value = "book_id") long bookId) {
        return ResponseEntity.ok(service.handinBook(id, bookId));
    }

}
