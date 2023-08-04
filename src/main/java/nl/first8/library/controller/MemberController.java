package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.domain.Members;
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

    @PutMapping("/member/{id}/book/{id_book}")
    public ResponseEntity<Members> lenen(@PathVariable(value = "id") Long id, @PathVariable(value = "id_book") Long id_book) {
        //book;
        Optional<Members> memberoud = memberRepository.findById(id);
        if (!memberoud.isPresent()) {
            return null;
        }
        Members updateMember = memberoud.get();

        Optional<Book> book = bookRepository.findById(id_book);
        Book infoBook = book.get();

        updateMember.getBook().add(infoBook);
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

}
