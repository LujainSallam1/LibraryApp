package nl.first8.library.controller;

import nl.first8.library.domain.Bluray;
import nl.first8.library.repository.BlurayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class BlurayController {
    @Autowired
    private BlurayRepository blurayRepository;

    @GetMapping("/blurays")
    public List<Bluray> getAll() {
        return this.blurayRepository.findAll();
    }

    @GetMapping("/blurays/{id}")
    public ResponseEntity<Bluray> getById( @PathVariable(value = "id") Long id)  {
        Optional<Bluray> bluray = blurayRepository.findById(id);

        if (bluray.isPresent()){
            return ResponseEntity.ok(bluray.get());
        }
        else {
            //TODO: better response
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/blurays")
    public Bluray add(@RequestBody Bluray bluray) {

        Bluray savedBluray = blurayRepository.save(bluray);

        return savedBluray;
    }

    @PutMapping("/blurays/{id}")
    public ResponseEntity<Bluray> update(@PathVariable(value = "id") Long id, @RequestBody Bluray body) {
        Optional<Bluray> blurayOptional = blurayRepository.findById(id);
        Bluray blurayDB;

        if (blurayOptional.isPresent()){
            blurayDB = blurayOptional.get();

            if (Objects.nonNull(body.getTitle()))         blurayDB.setTitle(body.getTitle());
            if (Objects.nonNull(body.getDirectors()))     blurayDB.setDirectors(body.getDirectors());
            if (Objects.nonNull(body.getPublishDate()))   blurayDB.setPublishDate(body.getPublishDate());
            if (Objects.nonNull(body.getSummary()))       blurayDB.setSummary(body.getSummary());

        }
        else {
            return ResponseEntity.notFound().build();
        }

        Bluray updatedBluray = blurayRepository.save(blurayDB);
        return ResponseEntity.ok(updatedBluray);
    }

    @DeleteMapping("/blurays/{title}")
    public Map<String, Boolean> delete(@PathVariable( value = "title") String title) {
        //TODO
        List<Bluray> bluraysWithTitle = blurayRepository.findByTitle(title);
        Map<String, Boolean> map = new HashMap<>();

        for(int i=0 ; i<bluraysWithTitle.size(); i++){
            Bluray bluray = bluraysWithTitle.get(i);
            Long id = bluray.getId();
            blurayRepository.deleteById(id);
            Boolean hasBeenDeleted = ! blurayRepository.findById(id).isPresent();

            map.put(id.toString(), hasBeenDeleted);
        }
        return map;
    }

    @PutMapping("/blurays/{id}/borrow")
    public ResponseEntity<Bluray> borrow(@PathVariable(value = "id") Long id) {
        Bluray bluray = blurayRepository.getById(id);
        if(bluray.isBorrowed()){
            //TODO: better response
            return ResponseEntity.notFound().build();
        }

        bluray.setBorrowed(true);

        Bluray updatedBluray = blurayRepository.save(bluray);
        return ResponseEntity.ok(updatedBluray);
    }

    @PutMapping("/blurays/{id}/handin")
    public ResponseEntity<Bluray> handin(@PathVariable(value = "id") Long id) {
        Bluray bluray = blurayRepository.getById(id);

        bluray.setBorrowed(false);

        Bluray updatedBook = blurayRepository.save(bluray);
        return ResponseEntity.ok(updatedBook);
    }
}
