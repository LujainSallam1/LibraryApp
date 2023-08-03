package nl.first8.library.controller;

import nl.first8.library.domain.Bluerays;
import nl.first8.library.repository.BlueraysRepository;
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

public class BlueraysController {

        @Autowired
        private BlueraysRepository blueraysRepository;


        @GetMapping("/bluerays")
        public List<Bluerays> getAll() {
            return blueraysRepository.findAll();
        }

        @GetMapping("/bluerays/{id}")
        public ResponseEntity<Bluerays> getById(@PathVariable Long id) {
            Optional<Bluerays> optionalBook = blueraysRepository.findById(id);
            if (optionalBook.isPresent()) {
                Bluerays book = optionalBook.get();
                return ResponseEntity.ok(book);
            } else
                return ResponseEntity.notFound().build();

        }

//    @GetMapping("/bluerays/{sbn}")
//    public ResponseEntity<Bluerays> getByIsbn(@PathVariable String isbn) {
//        Optional<Bluerays> optionalBook = blueraysRepostiry.findByIsbn(isbn);
//        if (optionalBook.isPresent()) {
//            Bluerays book = optionalBook.get();
//            return ResponseEntity.ok(book);
//        } else
//            return ResponseEntity.notFound().build();
//
//    }

        @PostMapping("/bluerays")
        public ResponseEntity<Bluerays> add(@RequestBody Bluerays book) {
            Bluerays savedbook = blueraysRepository.save(book);
            return ResponseEntity.ok(savedbook);
        }


        @PutMapping("/bluerays/{id}")
        public ResponseEntity<Bluerays> update(@PathVariable(value = "id") Long id, @RequestBody Bluerays book) {
            Optional<Bluerays> optionalBook = blueraysRepository.findById(id);
            if (optionalBook.isPresent()) {
                Bluerays orginalbook = optionalBook.get();
                orginalbook.setTitle(book.getTitle());
                orginalbook.setTitle(book.getTitle());
                orginalbook.setDirector(book.getDirector());
                orginalbook.setPublishDate(book.getPublishDate());
                orginalbook.setGenre(book.getGenre());


                Bluerays updatedBookEntity = blueraysRepository.save(book);
                return ResponseEntity.ok(updatedBookEntity);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/bluerays/{id}")
        public ResponseEntity<Bluerays> delet(@PathVariable long id) {
            Optional<Bluerays> optionalBook = blueraysRepository.findById((id));
            if (optionalBook.isPresent()) {
                blueraysRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }


        }

        @PutMapping("/bluerays/{id}/borrow")
        public boolean borrow(@PathVariable(value = "id") Long id) {
            Optional<Bluerays> optionalBook = blueraysRepository.findById(id);
            if (optionalBook.isPresent()) {
                Bluerays book = optionalBook.get();

                if (!book.isBorrowed()) {
                    book.setBorrowed(true);
                    blueraysRepository.save(book);
                    return true;

                } else {
                    if (book.isBorrowed()) {
                        book.setBorrowed(false);
                        blueraysRepository.save(book);
                        return true;
                    }
                }
            }
            return false;
        }
    }

