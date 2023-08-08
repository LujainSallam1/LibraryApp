package nl.first8.library.controller;

import nl.first8.library.domain.BluRay;
import nl.first8.library.repository.BluRayRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BluRayController {
    @Autowired
    private BluRayRepository bluRayRepository;

    @GetMapping("/blurays")
    public List<BluRay> getAll() { return bluRayRepository.findAll(); }

    @GetMapping("/blurays/{blurayid}")
    public ResponseEntity<BluRay> getById(@PathVariable(value = "blurayid") Long blurayid) {
        BluRay bluray = bluRayRepository.findById(blurayid).get();
        return ResponseEntity.ok(bluray);
    }

    @PostMapping("/blurays")
    public BluRay add(@RequestBody BluRay bluray) {
        BluRay savedBluRay = bluRayRepository.save(bluray);
        return savedBluRay;
    }

    @PutMapping("/blurays/{blurayid}")
    public ResponseEntity<BluRay> update(@PathVariable(value = "blurayid") Long blurayid, @RequestBody BluRay bluray) {
        bluray.setId(blurayid);
        BluRay updatedBluRay = bluRayRepository.save(bluray);
        return ResponseEntity.ok(updatedBluRay);
    }

    @DeleteMapping("/blurays/{blurayid}")
    public Map<String, Boolean> delete(@PathVariable(value = "blurayid") Long blurayid) {
        bluRayRepository.deleteById(blurayid);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return response;
    }

    @PutMapping("/blurays/{blurayid}/borrow")
    public ResponseEntity<BluRay> borrow(@PathVariable(value = "blurayid") Long blurayid) {
        Optional<BluRay> optionalBluRay = bluRayRepository.findById(blurayid);
        if (optionalBluRay.isPresent()) {
              BluRay bluray = optionalBluRay.get();
            if (bluray.isBorrowed()) {
                return ResponseEntity.notFound().build(); // POSSIBLE BETTER ERROR HANDLING, SINCE IT IS ACTUALLY FOUND, JUST NOT AVAILABLE
            }
            bluray.setBorrowed(true);
            BluRay updatedBluRay = bluRayRepository.save(bluray);
            return ResponseEntity.ok(updatedBluRay);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/blurays/{blurayid}/handin")
    public ResponseEntity<BluRay> handin (@PathVariable(value = "blurayid") Long blurayid){
        Optional<BluRay> optionalBluRay = bluRayRepository.findById(blurayid);
        if (optionalBluRay.isPresent()) {
            BluRay bluray = optionalBluRay.get();
            if (bluray.isBorrowed()) {
                bluray.setBorrowed(false);
                BluRay updatedBluRay = bluRayRepository.save(bluray);
                return ResponseEntity.ok(updatedBluRay);
            }
            else{
                return ResponseEntity.notFound().build(); // POSSIBLE BETTER ERROR HANDLING, SINCE IT IS ACTUALLY FOUND, JUST NOT BORROWED YET
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
