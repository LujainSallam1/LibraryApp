package nl.first8.library.controller;

import nl.first8.library.domain.Stripbook
        ;
import nl.first8.library.domain.Stripbook;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.StriobookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")

public class StripbookController {
    @Autowired
    private StriobookRepository striobookRepository;

    @GetMapping("/stripbooks")
    public List<Stripbook> getAll() {
        return striobookRepository.findAll();
    }

    @GetMapping("/stripbooks / {id}")
    public ResponseEntity<Stripbook>getById(@PathVariable Long id) {
        Optional<Stripbook> optionalBook = striobookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Stripbook book = optionalBook.get();
            return ResponseEntity.ok(book);
        } else
            return ResponseEntity.notFound().build();

    }

    @PostMapping("/stripbooks ")
            public ResponseEntity<Stripbook>add(@RequestBody Stripbook book) {
        Stripbook savedbook = striobookRepository.save(book);
        return ResponseEntity.ok(savedbook);
    }


    @PutMapping("/stripbooks / {id}")
            public ResponseEntity<Stripbook>update(@PathVariable(value = "id") Long id, @RequestBody Stripbook book) {
        Optional<Stripbook> optionalBook = striobookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Stripbook orginalbook = optionalBook.get();
            orginalbook.setTitle(book.getTitle());
            orginalbook.setTitle(book.getTitle());
            orginalbook.setAuthor(book.getAuthor());
            orginalbook.setPublishDate(book.getPublishDate());
            orginalbook.setGenre(book.getGenre());


            Stripbook updatedBookEntity = striobookRepository.save(book);
            return ResponseEntity.ok(updatedBookEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/stripbooks / {id}")
            public ResponseEntity<Stripbook>delet(@PathVariable long id) {
        Optional<Stripbook> optionalBook = striobookRepository.findById((id));
        if (optionalBook.isPresent()) {
            striobookRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping("/stripbooks / {id} / borrow")
            public boolean borrow(@PathVariable(value = "id") Long id) {
        Optional<Stripbook> optionalBook = striobookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Stripbook book = optionalBook.get();

            if (!book.isBorrowed()) {
                book.setBorrowed(true);
                striobookRepository.save(book);
                return true;

            } else {
                if (book.isBorrowed()) {
                    book.setBorrowed(false);
                    striobookRepository.save(book);
                    return true;
                }
            }
        }
        return false;
    }
}
