package nl.first8.library.controller;

import nl.first8.library.domain.Leden;
import nl.first8.library.repository.LedenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class LedenController {
    @Autowired
    private LedenRepository ledenRepository;

    @GetMapping("/leden")
    public List<Leden> getAll() {return ledenRepository.findAll();
    }

    @GetMapping("/leden/{voornaam}/{achternaam}")
    public List<Leden> getByFullName( @RequestParam(value = "voornaam") String voornaam,
                                      @RequestParam(value = "achternaam") String agternaam) {
        return ledenRepository.findAllByVoornaamAndAgternaam(voornaam, agternaam);
    }

    @GetMapping("/leden/{id}")
    public ResponseEntity<Leden> getById( @PathVariable(value = "id") Long id) {
        Optional<Leden> lid = ledenRepository.findById(id);
        return lid.map(ResponseEntity::ok).orElseThrow(() -> new RuntimeException("Record with id not found"));
    }

    @PostMapping("/leden")
    public Leden add(@RequestBody Leden lid) {
        if (ledenRepository.existsById(lid.getId())) {throw new RuntimeException(
                "Cannot add to repository. Record with primary key id already exists");}
        ledenRepository.save(lid);
        return null;
    }

    @PutMapping("/leden/{id}")
    public ResponseEntity<Leden> update(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        Leden updatedLid = ledenRepository.findById(id).orElseThrow(() -> new RuntimeException("Record with id not found"));
        updatedLid.setVoornaam(lid.getVoornaam());
        updatedLid.setAgternaam(lid.getAgternaam());
        updatedLid.setHuisNummer(lid.getHuisNummer());
        updatedLid.setStraatNaam(lid.getStraatNaam());
        updatedLid.setPlaats(lid.getPlaats());
        updatedLid.setPostcode(lid.getPostcode());
        updatedLid.setDisabled(lid.isDisabled());
        updatedLid.setProductenGeleend(lid.getProductenGeleend());
        return ResponseEntity.ok(updatedLid);
    }

    @GetMapping("/leden/voornaam/{voornaam}")
    public List<Leden> getAllByVoornaam ( @PathVariable(value = "voornaam") String voornaam) {
        return ledenRepository.findAllByVoornaam(voornaam);
    }

    @GetMapping("/leden/agternaam/{agternaam}")
    public List<Leden> getAllByAgternaam ( @PathVariable(value = "agternaam") String agternaam) {
        return ledenRepository.findAllByAgternaam(agternaam);
    }

    @PutMapping("/leden/{id}/disable")
    public ResponseEntity<Leden> disable(@PathVariable(value = "id") Long id) {
        Leden updatedLid = ledenRepository.getById(id);
        updatedLid.setDisabled(true);
        return ResponseEntity.ok(updatedLid);
    }

    @PutMapping("/leden/{id}/productenGeleend")
    public ResponseEntity<Leden> update(@PathVariable(value = "id") Long id, Integer productenGeleend) {
        Leden updatedLid = ledenRepository.getById(id);
        updatedLid.setProductenGeleend(productenGeleend);
        return ResponseEntity.ok(updatedLid);
    }
}
