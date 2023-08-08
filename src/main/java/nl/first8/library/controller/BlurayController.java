package nl.first8.library.controller;

import nl.first8.library.domain.Bluray;
import nl.first8.library.Constants;
import nl.first8.library.domain.Leden;
import nl.first8.library.repository.BlurayRepository;
import nl.first8.library.repository.LedenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BlurayController {
    @Autowired
    private BlurayRepository blurayRepository;

    @Autowired
    private LedenRepository lidRepository;

    @GetMapping("/blurays")
    public List<Bluray> getAll() {return blurayRepository.findAll();
    }

    @GetMapping("/blurays/{id}")
    public ResponseEntity<Bluray> getById( @PathVariable(value = "id") Long id) {
        Optional<Bluray> bluray = blurayRepository.findById(id);
        return bluray.map(ResponseEntity::ok).orElseThrow(()
                -> new RuntimeException("Record with value 'id' not found"));
    }

    @PostMapping("/blurays")
    public Bluray add(@RequestBody Bluray bluray) {
        if (blurayRepository.existsById(bluray.getId())) {throw new RuntimeException(
                "Cannot add to repository. Record with primary key value 'id' already exists");}
        blurayRepository.save(bluray);
        return null;
    }

    @PutMapping("/blurays/{id}")
    public ResponseEntity<Bluray> update(@PathVariable(value = "id") Long id, @RequestBody Bluray bluray) {
        Bluray updatedBluray = blurayRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Record with id not found"));
        updatedBluray.setDirector(bluray.getDirector());
        updatedBluray.setBorrowed(bluray.isBorrowed());
        updatedBluray.setTitle(bluray.getTitle());
        updatedBluray.setPublishDate(bluray.getPublishDate());
        boolean checkLidId = lidRepository.existsById(bluray.getBorrowerId());
        if (!checkLidId) {throw new RuntimeException("Update failed. Cannot find borrower with given id");}
        return ResponseEntity.ok(updatedBluray);
    }


    @PutMapping("/blurays/{id}/borrow")
    public ResponseEntity<Bluray> borrow(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        Bluray updatedBluray = blurayRepository.getById(id);
        if (updatedBluray.isBorrowed()) {
            throw new RuntimeException("Check out failed. Blu-ray is already checked out by someone else");}
        updatedBluray.setBorrowed(true);
        updatedBluray.setBorrowerId(lid.getId());
        Leden lidRequest = lidRepository.getById(lid.getId());
        if (lidRequest.getProductenGeleend() >= Constants.BORROWLIMIT) {
            throw new RuntimeException("Unable to process loan. Borrow limit has been reached");}
        lidRequest.setProductenGeleend(lid.getProductenGeleend() + 1);
        return ResponseEntity.ok(updatedBluray);
    }
    @PutMapping("/blurays/{id}/handin")
    public ResponseEntity<Bluray> handin(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        Bluray updatedBluray = blurayRepository.getById(id);
        updatedBluray.setBorrowed(false);
        updatedBluray.setBorrowerId(lid.getId());
        Leden lidRequest = lidRepository.getById(lid.getId());
        if (!Objects.equals(lidRequest.getId(), updatedBluray.getBorrowerId()))
        {throw new RuntimeException("Cannot hand in bly-ray. A different member is in possession of this item");}
        lidRequest.setProductenGeleend(lid.getProductenGeleend() - 1);
        return ResponseEntity.ok(updatedBluray);
    }

    @PutMapping("/blurays/{id}/summary")
    public ResponseEntity<Bluray> update(@PathVariable(value = "id") Long id, String summary) {
        Bluray updatedBook = blurayRepository.getById(id);
        updatedBook.setSummary(summary);
        return ResponseEntity.ok(updatedBook);
    }
}
