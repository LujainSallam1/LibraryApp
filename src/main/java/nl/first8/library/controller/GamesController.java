package nl.first8.library.controller;

import nl.first8.library.Constants;
import nl.first8.library.domain.Games;
import nl.first8.library.domain.Leden;
import nl.first8.library.repository.GamesRepository;
import nl.first8.library.repository.LedenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class GamesController {
    @Autowired
    private GamesRepository gamesRepository;

    @Autowired
    private LedenRepository lidRepository;

    @GetMapping("/games")
    public List<Games> getAll() {return gamesRepository.findAll();
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Games> getById( @PathVariable(value = "id") Long id) {
        Optional<Games> game = gamesRepository.findById(id);
        return game.map(ResponseEntity::ok).orElseThrow(()
                -> new RuntimeException("Record with value 'id' not found"));
    }

    @PostMapping("/games")
    public Games add(@RequestBody Games game) {
        if (gamesRepository.existsById(game.getId())) {throw new RuntimeException(
                "Cannot add to repository. Record with primary key value 'id' already exists");}
        gamesRepository.save(game);
        return null;
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Games> update(@PathVariable(value = "id") Long id, @RequestBody Games game) {
        Games updatedGame = gamesRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Record with id not found"));
        updatedGame.setDeveloper(game.getDeveloper());
        updatedGame.setBorrowed(game.isBorrowed());
        updatedGame.setTitle(game.getTitle());
        updatedGame.setPublishDate(game.getPublishDate());
        updatedGame.setPlatform(game.getPlatform());
        boolean checkLidId = lidRepository.existsById(game.getBorrowerId());
        if (!checkLidId) {throw new RuntimeException("Update failed. Cannot find borrower with given id");}
        return ResponseEntity.ok(updatedGame);
    }


    @PutMapping("/games/{id}/borrow")
    public ResponseEntity<Games> borrow(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        Games updatedGame = gamesRepository.getById(id);
        if (updatedGame.isBorrowed()) {
            throw new RuntimeException("Check out failed. Game is already checked out by someone else");}
        updatedGame.setBorrowed(true);
        updatedGame.setBorrowerId(lid.getId());
        Leden lidRequest = lidRepository.getById(lid.getId());
        if (lidRequest.getProductenGeleend() >= Constants.BORROWLIMIT) {
            throw new RuntimeException("Unable to process loan. Borrow limit has been reached");}
        lidRequest.setProductenGeleend(lid.getProductenGeleend() + 1);
        return ResponseEntity.ok(updatedGame);
    }
    @PutMapping("/games/{id}/handin")
    public ResponseEntity<Games> handin(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        Games updatedGame = gamesRepository.getById(id);
        updatedGame.setBorrowed(false);
        updatedGame.setBorrowerId(lid.getId());
        Leden lidRequest = lidRepository.getById(lid.getId());
        if (!Objects.equals(lidRequest.getId(), updatedGame.getBorrowerId()))
        {throw new RuntimeException("Cannot hand in game. A different member is in possession of this item");}
        lidRequest.setProductenGeleend(lid.getProductenGeleend() - 1);
        return ResponseEntity.ok(updatedGame);
    }

    @PutMapping("/games/{id}/summary")
    public ResponseEntity<Games> update(@PathVariable(value = "id") Long id, String summary) {
        Games updatedGame = gamesRepository.getById(id);
        updatedGame.setSummary(summary);
        return ResponseEntity.ok(updatedGame);
    }
}
