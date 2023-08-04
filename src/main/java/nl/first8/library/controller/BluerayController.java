package nl.first8.library.controller;

import nl.first8.library.domain.Blueray;
import nl.first8.library.repository.BluerayRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BluerayController {
    @Autowired
    private BluerayRepository bluerayRepository;

    @GetMapping("/blueray")
    public List<Blueray> getAll() {
        return new ArrayList<>(bluerayRepository.findAll()); //TODO implement
    }


    @GetMapping("/blueray/{id}")
    public Blueray getByID(@PathVariable("id") String id){
        return bluerayRepository.findById(Long.valueOf(id)).get();//TODO implement
    }

    @PostMapping("/blueray")
    public Blueray add(@RequestBody Blueray blueray) {
        return bluerayRepository.save(blueray);
    }

    @PutMapping("/blueray/{id}")
    public ResponseEntity<Blueray> update(@PathVariable(value = "id") Long id, @RequestBody Blueray blueray) {
        Optional<Blueray> bluerayoud = bluerayRepository.findById(id);
        if (!bluerayoud.isPresent()){
            return null;
        }
        Blueray updateBlueray = bluerayoud.get();

        if (blueray.getIsbn() != null){
            updateBlueray.setIsbn(blueray.getIsbn());
        }
        if (blueray.getTitle() != null){
            updateBlueray.setTitle(blueray.getTitle());
        }
        if (blueray.getAuthors() != null){
            updateBlueray.setAuthors(blueray.getAuthors());
        }
        if (blueray.getPublishDate() != null){
            updateBlueray.setPublishDate(blueray.getPublishDate());
        }

        if (blueray.getSummary() != null) {
            updateBlueray.setSummary(blueray.getSummary());
        }

        return ResponseEntity.ok(bluerayRepository.save(updateBlueray));
    }

    @DeleteMapping("/blueray/{isbn}")
    public Map<String, Boolean> delete(@PathVariable( value = "isbn") String isbn) {
        Map<String, Boolean> map;
        bluerayRepository.deleteByIsbn(isbn);
        return null;
    }

    @PutMapping("/blueray/{id}/borrow")
    public ResponseEntity<Blueray> borrow(@PathVariable(value = "id") Long id) {
        //TODO
        Optional<Blueray> findBlueray = bluerayRepository.findById(id);
        if (!findBlueray.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blueray not found");
        }

        Blueray updatedBlueray = findBlueray.get();
        if (updatedBlueray.isBorrowed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Blueray had already borrowed");
        }
        updatedBlueray.setBorrowed(true);

        return ResponseEntity.ok(bluerayRepository.save(updatedBlueray));
    }

    @PutMapping("/blueray/{id}/handin")
    public ResponseEntity<Blueray> handin(@PathVariable(value = "id") Long id) {
        //TODO

        Optional<Blueray> findBlueray = bluerayRepository.findById(id);
        if (!findBlueray.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blueray not found");
        }

        Blueray updatedBlueray = findBlueray.get();
        if (!updatedBlueray.isBorrowed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Blueray had already handin");
        }
        updatedBlueray.setBorrowed(false);

        return ResponseEntity.ok(bluerayRepository.save(updatedBlueray));
    }
}
