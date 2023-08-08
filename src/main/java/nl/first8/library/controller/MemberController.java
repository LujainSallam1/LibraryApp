package nl.first8.library.controller;

import nl.first8.library.domain.BluRay;
import nl.first8.library.domain.Book;
import nl.first8.library.domain.ComicBook;
import nl.first8.library.domain.Members;
import nl.first8.library.repository.BluRayRepository;
import nl.first8.library.repository.ComicBookRepository;
import nl.first8.library.repository.MemberRepository;
import nl.first8.library.repository.BookRepository;
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

import javax.persistence.ManyToOne;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class MemberController {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BluRayRepository blurayRepository;

    @Autowired
    private ComicBookRepository comicBookRepository;

    @GetMapping("/member")
    public ResponseEntity<List<Members>> getAll() {
        List<Members> allMembers = memberRepository.findAll();
        return ResponseEntity.ok(allMembers);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<Members> getById( @PathVariable(value = "id") Long id) {
        Members member = memberRepository.findById(id).get();
        return ResponseEntity.ok(member);
    }

    @PostMapping("/member")
    public ResponseEntity<Members> add(@RequestBody Members member) {
        Members createMember = memberRepository.save(member);
        return ResponseEntity.ok(createMember);
    }

    @PutMapping("/member/{id}")
    public ResponseEntity<Members> update(@PathVariable(value = "id") Long id, @RequestBody Members member) {
        Optional<Members> memberoud = memberRepository.findById(id);
        if (!memberoud.isPresent()) {
            return null;
        }
        Members updateMember = memberoud.get();

        if (member.getName() != null) {
            updateMember.setName(member.getName());
        }

        if (member.getZipCode() != null) {
            updateMember.setZipCode(member.getZipCode());
        }

        if (member.getStreet() != null) {
            updateMember.setStreet(member.getStreet());
        }

        if (member.getHouseNumber() != null) {
            updateMember.setHouseNumber(member.getHouseNumber());
        }

        if (member.getCity() != null) {
            updateMember.setCity(member.getCity());
        }

        updateMember.setProducts(member.getProducts());

        updateMember.setActive(member.getActive());

        return ResponseEntity.ok(memberRepository.save(updateMember));
    }

    @PutMapping("/member/{id}/active")
    public ResponseEntity<Members> active(@PathVariable(value = "id") Long id, @RequestBody Members member) {
        Optional<Members> memberoud = memberRepository.findById(id);
        if (!memberoud.isPresent()) {
            return null;
        }
        Members updateMember = memberoud.get();

        if (member.getActive() == true) {
            updateMember.setActive(false);
        } else if (member.getActive() == false) {
            updateMember.setActive(false);
        }

        return ResponseEntity.ok(memberRepository.save(updateMember));
    }

    @PutMapping("/member/{id}/book/{id_book}")
    public ResponseEntity<String> lenenbook(@PathVariable(value = "id") Long id, @PathVariable(value = "id_book")
    Long id_book) {
        Optional<Members> memberoud = memberRepository.findById(id);
        Optional<Book> book = bookRepository.findById(id_book);

        if ((!memberoud.isPresent() && !book.isPresent()) || memberoud.get().getActive() == false) {
            return ResponseEntity.ok("Lid of book bestaat niet of lid heeft geen toegang!");
        }
        Members updateMember = memberoud.get();
        Book infoBook = book.get();

        int now = updateMember.getProducts();

        if (updateMember.getBook().contains(infoBook)) {
            updateMember.getBook().remove(infoBook);
            updateMember.setProducts(updateMember.getProducts() + 1);
            memberRepository.save(updateMember);
            infoBook.setBorrowed(false);
            bookRepository.save(infoBook);
            return ResponseEntity.ok("gelukt om een item terug te brengen");
        } else {
            updateMember.getBook().add(infoBook);
            if (now == 0){
                return ResponseEntity.ok("Zijn al te veel items geleend");
            } else {
                updateMember.setProducts(updateMember.getProducts() - 1);
                memberRepository.save(updateMember);
                infoBook.setBorrowed(true);
                bookRepository.save(infoBook);
                return ResponseEntity.ok("gelukt om een item te huren");
            }
        }
    }

    @PutMapping("/member/{id}/bluray/{id_bluray}")
    public ResponseEntity<String> lenenbluray(@PathVariable(value = "id") Long id, @PathVariable(value = "id_bluray")
    Long id_bluray) {
        Optional<Members> memberoud = memberRepository.findById(id);
        Optional<BluRay> bluRay = blurayRepository.findById(id_bluray);
        if ((!memberoud.isPresent() && !bluRay.isPresent()) || memberoud.get().getActive() == false) {
            return ResponseEntity.ok("Lid of book bestaat niet of lid heeft geen toegang!");
        }
        Members updateMember = memberoud.get();
        BluRay infoBluray = bluRay.get();

        int now = updateMember.getProducts();

        if (updateMember.getBluray().contains(infoBluray)) {
            updateMember.getBluray().remove(infoBluray);
            updateMember.setProducts(updateMember.getProducts() + 1);
            memberRepository.save(updateMember);
            infoBluray.setBorrowed(false);
            blurayRepository.save(infoBluray);
            return ResponseEntity.ok("gelukt om een item terug te brengen");
        } else {
            updateMember.getBluray().add(infoBluray);
            if (now == 0){
                return ResponseEntity.ok("Zijn al te veel items geleend");
            } else {
                updateMember.setProducts(updateMember.getProducts() - 1);
                memberRepository.save(updateMember);
                infoBluray.setBorrowed(true);
                blurayRepository.save(infoBluray);
                return ResponseEntity.ok("gelukt om een item te huren");
            }
        }
    }

    @PutMapping("/member/{id}/borrow_comicbook/{id_comicbook}")
    public ResponseEntity<String> lenencomicbook (@PathVariable(value = "id") Long id, @PathVariable(value = "id_comicbook")
    Long id_comicbook) {
        Optional<Members> memberoud = memberRepository.findById(id);
        Optional<ComicBook> comicBook = comicBookRepository.findById(id_comicbook);
        if ((!memberoud.isPresent() && !comicBook.isPresent()) || memberoud.get().getActive() == false || comicBook.get().isBorrowed()) {
            return ResponseEntity.ok("Lid of book bestaat niet of lid heeft geen toegang of boek is al uitgeleend!");
        }

        Members updateMember = memberoud.get();
        ComicBook infoComicBook = comicBook.get();
        int now = updateMember.getProducts();
        updateMember.getComicBook().add(infoComicBook);

        if (now == 0){
            return ResponseEntity.ok("Zijn al te veel items geleend");
        } else {
            updateMember.setProducts(updateMember.getProducts() - 1);
            memberRepository.save(updateMember);
            infoComicBook.setBorrowed(true);
            comicBookRepository.save(infoComicBook);
            return ResponseEntity.ok("gelukt om een item te huren");
        }
    }

    @PutMapping("/member/{id}/return_comicbook/{id_comicbook}")
    public ResponseEntity<String> returncomicbook (@PathVariable(value = "id") Long id, @PathVariable(value = "id_comicbook")
    Long id_comicbook) {
        Optional<Members> memberoud = memberRepository.findById(id);
        Optional<ComicBook> comicBook = comicBookRepository.findById(id_comicbook);
        if ((!memberoud.isPresent() && !comicBook.isPresent()) || memberoud.get().getActive() == false || !comicBook.get().isBorrowed()) {
            return ResponseEntity.ok("Lid of book bestaat niet of lid heeft geen toegang of boek is al uitgeleend!");
        }
        Members updateMember = memberoud.get();
        ComicBook infoComicBook = comicBook.get();

        updateMember.getComicBook().remove(infoComicBook);
        updateMember.setProducts(updateMember.getProducts() + 1);
        memberRepository.save(updateMember);
        infoComicBook.setBorrowed(false);
        comicBookRepository.save(infoComicBook);
        return ResponseEntity.ok("gelukt om een item terug te brengen");
        }
    }
