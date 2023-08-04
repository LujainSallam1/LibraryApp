package nl.first8.library.controller;

import nl.first8.library.domain.BlueRay;
import nl.first8.library.domain.Book;
import nl.first8.library.repository.BlueRayRepository;
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
public class BlueRayController {
    @Autowired
    private BlueRayRepository blueRayRepository;

    @GetMapping("/blueray")
    public List<BlueRay> getAll() {
        return blueRayRepository.findAll();
    }

    @GetMapping("/blueray/{id}")
    public ResponseEntity<BlueRay> getById( @PathVariable(value = "id") Long id) {
        Optional<BlueRay> blueray = blueRayRepository.findById(id);

        if (!blueray.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BlueRay not found!");
        return ResponseEntity.ok(blueray.get());
    }

    @PostMapping("/blueray")
    public BlueRay add(@RequestBody BlueRay blueray) {
        blueRayRepository.save(blueray);
        return blueray;
    }

    @PutMapping("/blueray/{id}")
    public ResponseEntity<BlueRay> update(@PathVariable(value = "id") Long id, @RequestBody BlueRay blueray) {

        Optional<BlueRay> findBlueRay = blueRayRepository.findById(id);

        if (!findBlueRay.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found!");
        BlueRay updatedBlueRay = findBlueRay.get();

        if (blueray.getTitle() != null)
            updatedBlueRay.setTitle(blueray.getTitle());
        if (blueray.getAuthors() != null)
            updatedBlueRay.setAuthors(blueray.getAuthors());
        if (blueray.getSummary() != null)
            updatedBlueRay.setSummary(blueray.getSummary());
        if (blueray.getPublishDate() != null)
            updatedBlueRay.setPublishDate(blueray.getPublishDate());
        blueRayRepository.save(updatedBlueRay);

        return ResponseEntity.ok(updatedBlueRay);
    }

    @DeleteMapping("/blueray/{id}")
    public ResponseEntity<String> delete(@PathVariable( value = "id") Long id) {

        blueRayRepository.deleteById(id);
        return ResponseEntity.ok("BlueRay has been removed!");
    }

}
