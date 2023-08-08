package nl.first8.library.controller;

import nl.first8.library.domain.ComicBook;
import nl.first8.library.repository.ComicBookRepository;
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
public class ComicBookController {
    @Autowired
    private ComicBookRepository comicBookRepository;

    @GetMapping("/comicbook")
    public ResponseEntity<List<ComicBook>> getAll() {
        List<ComicBook> allComicBook = comicBookRepository.findAll();
        return ResponseEntity.ok(allComicBook);
    }

    @GetMapping("/comcibook/{id}")
    public ResponseEntity<ComicBook> getById( @PathVariable(value = "id") Long id) {
        ComicBook comicBook = comicBookRepository.findById(id).get();
        return ResponseEntity.ok(comicBook);
    }

    @PostMapping("/comicbook")
    public ResponseEntity<ComicBook> add(@RequestBody ComicBook comicBook) {
        ComicBook createComicBook = comicBookRepository.save(comicBook);
        return ResponseEntity.ok(createComicBook);
    }

    @PutMapping("/comicbook/{id}")
    public ResponseEntity<ComicBook> update(@PathVariable(value = "id") Long id, @RequestBody ComicBook comicBook) {
        Optional<ComicBook> comicBookoud = comicBookRepository.findById(id);
        if (!comicBookoud.isPresent()) {
            return null;
        }
        ComicBook updateComicBook = comicBookoud.get();

        if (comicBook.getTitle() != null) {
            updateComicBook.setTitle(comicBook.getTitle());
        }

        if (comicBook.getAuthors() != null) {
            updateComicBook.setAuthors(comicBook.getAuthors());
        }

        if (comicBook.getPublishDate() != null) {
            updateComicBook.setPublishDate(comicBook.getPublishDate());
        }

        updateComicBook.setBorrowed(comicBook.isBorrowed());

        if (comicBook.getSummary() != null) {
            updateComicBook.setSummary(comicBook.getSummary());
        }

        return ResponseEntity.ok( comicBookRepository.save(updateComicBook));
    }

    @DeleteMapping("/comicbook/{id}")
    public ResponseEntity<String> delete(@PathVariable( value = "id") Long id) {
        comicBookRepository.deleteById(id);
        return ResponseEntity.ok("Gelukt");
    }

    @PutMapping("/comcibook/{id}/borrow")
    public ResponseEntity<ComicBook> borrow(@PathVariable(value = "id") Long id) {
        Optional<ComicBook> comicBookoud = comicBookRepository.findById(id);
        if (!comicBookoud.isPresent()) {
            return null;
        }
       ComicBook updateComicBook = comicBookoud.get();

        if (updateComicBook.isBorrowed() == false) {
            updateComicBook.setBorrowed(true);
        } else if (updateComicBook.isBorrowed() == true) {
            updateComicBook.setBorrowed(false);
        }
        return ResponseEntity.ok( comicBookRepository.save(updateComicBook));
    }
}
