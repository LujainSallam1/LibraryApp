package nl.first8.library.controller;

import nl.first8.library.domain.Bluerays;
import nl.first8.library.domain.Book;
import nl.first8.library.domain.Leden;
import nl.first8.library.domain.Stripbook;
import nl.first8.library.repository.BlueraysRepository;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.LedenRepository;
import nl.first8.library.repository.StriobookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LedenControlller {

    @Autowired
    private LedenRepository ledenRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BlueraysRepository blueraysRepository;
    @Autowired
    private StriobookRepository striobookRepository;

    @GetMapping("/leden")
    public List<Leden> getAll() {
        return ledenRepository.findAll();
    }

    @GetMapping("/leden/{id}")
    public ResponseEntity<Leden> getById(@PathVariable Long id) {
        Optional<Leden> optionallid = ledenRepository.findById(id);
        if (optionallid.isPresent()) {
            Leden lid = optionallid.get();
            return ResponseEntity.ok(lid);
        } else
            return ResponseEntity.notFound().build();

    }

    @PostMapping("/leden")
    public ResponseEntity<Leden> add(@RequestBody Leden lid) {
        Leden savedlid = ledenRepository.save(lid);
        return ResponseEntity.ok(savedlid);
    }

    @PutMapping("/leden/{id}")
    public ResponseEntity<Leden> update(@PathVariable(value = "id") Long id, @RequestBody Leden lid) {
        Optional<Leden> optionalLid = ledenRepository.findById(id);
        if (optionalLid.isPresent()) {
            Leden orginalbook = optionalLid.get();
            orginalbook.setNaam(lid.getNaam());
            orginalbook.setAdres(lid.getAdres());
            orginalbook.setWoonplaats(lid.getWoonplaats());

            Leden updatedLidEntity = ledenRepository.save(lid);
            return ResponseEntity.ok(updatedLidEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/leden/{id}/disable")
    public boolean disable(@PathVariable(value = "id") Long id) {
        Optional<Leden> optionalLid = ledenRepository.findById(id);
        if (optionalLid.isPresent()) {
            Leden lid = optionalLid.get();
            if (!lid.isdisable())
                lid.setDisable(true);
            ledenRepository.save(lid);
            return true;
        }
        return false;
    }

    @PutMapping("/leden/{id}/enable")
    public boolean enable(@PathVariable(value = "id") Long id) {
        Optional<Leden> optionalLid = ledenRepository.findById(id);
        if (optionalLid.isPresent()) {
            Leden lid = optionalLid.get();
            if (lid.isdisable())
                lid.setDisable(false);
            ledenRepository.save(lid);
        }
        return true;
    }
    @PutMapping("/leden/{lidid}/borrowedbook/{bookid}")
    public void borrowBooklid(@PathVariable(value = "lidid") Long lidid, @PathVariable(value = "bookid") Long bookid) {
        Optional<Book> optionalbook = bookRepository.findById(bookid);
        Optional<Leden> optionallid = ledenRepository.findById(lidid);
        if (optionalbook.isPresent()) {
            Book book = optionalbook.get();
            Leden lid = optionallid.get();
            {
                if (!book.isBorrowed()) {
                    if(lid.getBorrowedbooks().size() < lid.getMaxLeenbaarProducten()){

                    book.setBorrowed(true);
                    bookRepository.save(book);
                    lid.getBorrowedbooks().add(book);
                    ledenRepository.save(lid);
                    }else {
                        System.out.println("You cant borrow more books");
                    }
                } else {
                    System.out.println("Book is already borrowed");

                }}

            } else{
            System.out.println("no book with this id");
            }

    }

    @PutMapping("/leden/{lidid}/borrowedbluerays/{blueraysid}")
    public void borrowbluerayslid(@PathVariable(value = "lidid") Long lidid, @PathVariable(value = "blueraysid") Long blueraysid) {
        Optional<Bluerays> optionalBluerays = blueraysRepository.findById(blueraysid);
        Optional<Leden> optionallid = ledenRepository.findById(lidid);
        if (optionalBluerays.isPresent()) {
            Bluerays bluerays = optionalBluerays.get();
            Leden lid = optionallid.get();
            {
                if (!bluerays.isBorrowed()) {
                    if (lid.getBorrowedbooks().size() < lid.getMaxLeenbaarProducten()) {

                        bluerays.setBorrowed(true);
                        blueraysRepository.save(bluerays);
                        lid.getBlueraysbooks().add(bluerays);

                        ledenRepository.save(lid);
                    } else {
                        System.out.println("You cant borrow more books");
                    }
                } else {
                    System.out.println("Book is already borrowed");

                }
            }

        } else {
            System.out.println("no book with this id");
        }

    }
    @PutMapping("/leden/{lidid}/borrowedstripbook/{stripbookid}")
    public void borrowstripbooklid(@PathVariable(value = "lidid") Long lidid, @PathVariable(value = "stripbookid") Long blueraysid) {
        Optional<Stripbook> optionalStripbook = striobookRepository.findById(blueraysid);
        Optional<Leden> optionallid = ledenRepository.findById(lidid);
        if (optionalStripbook.isPresent()) {
            Stripbook stripbook = optionalStripbook.get();
            Leden lid = optionallid.get();
            {
                if (!stripbook.isBorrowed()) {
                    if(lid.getBorrowedbooks().size() < lid.getMaxLeenbaarProducten()){

                        stripbook.setBorrowed(true);
                        striobookRepository.save(stripbook);
                        lid.ge().add(stripbook);

                        ledenRepository.save(lid);
                    }else {
                        System.out.println("You cant borrow more books");
                    }
                } else {
                    System.out.println("Book is already borrowed");

                }}

        } else{
            System.out.println("no book with this id");
        }

}}





