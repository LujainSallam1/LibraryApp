package nl.first8.library.controller;

import nl.first8.library.domain.BluRay;
import nl.first8.library.repository.BluRayRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class BluRayController {
    @Autowired
    private BluRayRepository blurayRepository;

    @GetMapping("/bluray")
    public ResponseEntity<List<BluRay>> getAll() {
        List<BluRay> allBluRay = blurayRepository.findAll();
        return ResponseEntity.ok(allBluRay);
    }

    @GetMapping("/bluray/{id}")
    public ResponseEntity<BluRay> getById( @PathVariable(value = "id") Long id) {
        BluRay bluray = blurayRepository.findById(id).get();
        return ResponseEntity.ok(bluray);
    }

    @PostMapping("/bluray")
    public ResponseEntity<BluRay> add(@RequestBody BluRay bluray) {
        BluRay createBluray = blurayRepository.save(bluray);
        return ResponseEntity.ok(createBluray);
    }

    @PutMapping("/bluray/{id}")
    public ResponseEntity<BluRay> update(@PathVariable(value = "id") Long id, @RequestBody BluRay bluray) {
        Optional<BluRay> blurayoud = blurayRepository.findById(id);
        if (!blurayoud.isPresent()) {
            return null;
        }
        BluRay updateBluray = blurayoud.get();

        if (bluray.getTitle() != null) {
            updateBluray.setTitle(bluray.getTitle());
        }

        if (bluray.getActor() != null) {
            updateBluray.setActor(bluray.getActor());
        }

        if (bluray.getPublishDate() != null) {
            updateBluray.setPublishDate(bluray.getPublishDate());
        }

        updateBluray.setBorrowed(bluray.isBorrowed());

        if (bluray.getSummary() != null) {
            updateBluray.setSummary(bluray.getSummary());
        }

        return ResponseEntity.ok( blurayRepository.save(updateBluray));
    }

    @DeleteMapping("/bluray/{id}")
    public ResponseEntity<String> delete(@PathVariable( value = "id") Long id) {
        blurayRepository.deleteById(id);
        return ResponseEntity.ok("Gelukt");
    }

    @PutMapping("/bluray/{id}/borrow")
    public ResponseEntity<BluRay> borrow(@PathVariable(value = "id") Long id) {
        Optional<BluRay> blurayoud = blurayRepository.findById(id);
        if (!blurayoud.isPresent()) {
            return null;
        }
        BluRay updateBluray = blurayoud.get();

        if (updateBluray.isBorrowed() == false) {
            updateBluray.setBorrowed(true);
        } else if (updateBluray.isBorrowed() == true) {
            updateBluray.setBorrowed(false);
        }
        return ResponseEntity.ok( blurayRepository.save(updateBluray));
    }

    @PutMapping("/bluray/{id}/handin")
    public ResponseEntity<BluRay> handin(@PathVariable(value = "id") Long id) {
        //TODO
        BluRay updatedBluray = null;
        return ResponseEntity.ok(updatedBluray);
    }
}
