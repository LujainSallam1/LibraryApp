package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.domain.Member;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/members")
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    

    @PostMapping("/members")
    public ResponseEntity<Member> add(@RequestBody Member member) {
        Member savedmember = memberRepository.save(member);
        return ResponseEntity.ok(savedmember);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<Member> update(@PathVariable(value = "id") Long id, @RequestBody Member member) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member orginalbook = optionalMember.get();
            orginalbook.setNaam(member.getNaam());
            orginalbook.setAdres(member.getAdres());
            orginalbook.setWoonplaats(member.getWoonplaats());

            Member updatedMemberEntity = memberRepository.save(member);
            return ResponseEntity.ok(updatedMemberEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/members/{id}/disable")
    public boolean disable(@PathVariable(value = "id") Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (!member.isdisable())
                member.setDisable(true);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    @PutMapping("/members/{id}/enable")
    public boolean enable(@PathVariable(value = "id") Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.isdisable())
                member.setDisable(false);
            memberRepository.save(member);
        }
        return true;
    }
    @PutMapping("/members/{memberid}/borrowedbook/{bookid}")
    public void borrowBookmember(@PathVariable(value = "memberid") Long memberid, @PathVariable(value = "bookid") Long bookid) {
        Optional<Book> optionalbook = bookRepository.findById(bookid);
        Optional<Member> optionalmember = memberRepository.findById(memberid);
        if (optionalbook.isPresent()) {
            Book book = optionalbook.get();
            Member member = optionalmember.get();
            {
                if (!book.isBorrowed()) {
                    if(member.getBorrowedbooks().size() < member.getMaxLeenbaarProducten()){

                    book.setBorrowed(true);
                    bookRepository.save(book);
                    member.getBorrowedbooks().add(book);
                    memberRepository.save(member);
                    }else {
                        System.out.println("You cant borrow more books");
                    }
                } else {
                    System.out.println("Book is already borrowed");

                }
            }

        } else{
            System.out.println("no book with this id");
        }

    }

}





