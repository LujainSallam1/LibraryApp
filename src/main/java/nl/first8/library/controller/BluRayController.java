package nl.first8.library.controller;

import nl.first8.library.domain.BluRay;
import nl.first8.library.repository.BluRayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class BluRayController {
    @Autowired
    private BluRayRepository bluRayRepository;

    @GetMapping("/blurays")
    @ResponseBody
    public List<BluRay> getAll() {
        return bluRayRepository.findAll();
    }

    @GetMapping("/blurays/{id}")
    public ResponseEntity<BluRay> getById(@PathVariable(value = "id") Long id) {
        BluRay bluray = bluRayRepository.findById(id).get(); //TODO implement
        return ResponseEntity.ok(bluray);
    }

    @PostMapping("/blurays")
    public BluRay add(@RequestBody BluRay bluRay) {
        BluRay blurayAdd = bluRayRepository.save(bluRay);
        return ResponseEntity.ok(blurayAdd).getBody();
    }

    @PutMapping("/blurays/{id}")
    public BluRay update(@PathVariable(value = "id") Long id, @RequestBody BluRay bluray) {
        Optional<BluRay> findBluray = bluRayRepository.findById(id);
        if (!findBluray.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bluray not found");
        }

        BluRay updatedBluray = findBluray.get();

        if (bluray.getTitle() != null){
            updatedBluray.setTitle(bluray.getTitle());
        }
        if (bluray.getDuration() != null) {
            updatedBluray.setDuration(bluray.getDuration());
        }
        if (bluray.getGenre() != null) {
            updatedBluray.setGenre(bluray.getGenre());
        }
        if (bluray.getPG() != null){
            updatedBluray.setPG(bluray.getPG());
        }
        if (bluray.getPublisher() != null){
            updatedBluray.setPublisher(bluray.getPublisher());
        }
        if (bluray.getReleaseDate() != null){
            updatedBluray.setReleaseDate(bluray.getReleaseDate());
        }

        return bluRayRepository.save(updatedBluray);
    }

    @DeleteMapping("/blurays/{id}")
    public Map<String, Boolean> delete(@PathVariable( value = "id") Long id) {
        //TODO
        Map<String, Boolean> map ;
        bluRayRepository.deleteBluRayById(id);
        return null;
    }

    @PutMapping("/bluray/{id}/borrow")
    public ResponseEntity<BluRay> borrow(@PathVariable(value = "id") Long id) {
        //TODO

        Optional<BluRay> findBluray = bluRayRepository.findById(id);
        if (!findBluray.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bluray not found");
        }

        BluRay updatedBluray = findBluray.get();
        if (updatedBluray.isBorrowed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bluray has already been borrowed out");
        }
        updatedBluray.setBorrowed(true);

        return ResponseEntity.ok(bluRayRepository.save(updatedBluray));
    }

    @PutMapping("/blurays/{id}/handin")
    public ResponseEntity<BluRay> handin(@PathVariable(value = "id") Long id) {
        //TODO
        Optional<BluRay> findBluray = bluRayRepository.findById(id);
        if (!findBluray.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bluray not found");
        }

        BluRay updatedBluray = findBluray.get();
        if (!updatedBluray.isBorrowed()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bluray has already been handed in");
        }
        updatedBluray.setBorrowed(false);

        return ResponseEntity.ok(bluRayRepository.save(updatedBluray));
    }

}


