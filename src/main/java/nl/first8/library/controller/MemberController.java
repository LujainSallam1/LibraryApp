package nl.first8.library.controller;

import nl.first8.library.domain.Book;
import nl.first8.library.domain.Member;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;

import static nl.first8.library.repository.MemberRepository.*;

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
    @PutMapping("/books/{id}")
    public ResponseEntity<Member> update(@PathVariable(value = "id") Long id, @RequestBody Member member) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        Member memberdb;

        if (memberOptional.isPresent()){
            memberdb = memberOptional.get();

            if (Objects.nonNull(member.getNaam()))              memberdb.setNaam(member.getNaam());
            if (Objects.nonNull(member.getAdres()))             memberdb.setAdres(member.getAdres());
            if (Objects.nonNull(member.getWoonplaats()))        memberdb.setWoonplaats(member.getWoonplaats());
            if (Objects.nonNull(member.getId()))                memberdb.setId(member.getId());
            if (Objects.nonNull(member.getBorrowedbooks()))     memberdb.setBorrowedbooks(member.getBorrowedbooks());

        }
        else {
            return ResponseEntity.notFound().build();
        }

        Member updatedMember = memberRepository.save(memberdb);
        return ResponseEntity.ok(updatedMember);
    }
//    @PutMapping("/members/{id}")
//    public ResponseEntity<Member> update(@PathVariable(value = "id") Long id, @RequestBody Member member) {
//        Optional<Member> optionalMember = memberRepository.findById(id);
//        if (optionalMember.isPresent()) {
//            Member orginalbook = optionalMember.get();
//            orginalbook.setNaam(member.getNaam());
//            orginalbook.setAdres(member.getAdres());
//            orginalbook.setWoonplaats(member.getWoonplaats());
//
//            Member updatedMemberEntity = memberRepository.save(member);
//            return ResponseEntity.ok(updatedMemberEntity);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

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

    @PutMapping("/members/{member_id}/borrow/{book_id}")
    public ResponseEntity<String> borrowBookMember(@PathVariable(value = "member_id") Long memberId, @PathVariable(value = "book_id") Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if ( !optionalBook.isPresent() ) {
            return new ResponseEntity<>("Book with ID " + bookId + " not found", HttpStatus.NOT_FOUND);
        }
        else if ( !optionalMember.isPresent() ) {
            return new ResponseEntity<>("Member with ID " + memberId + " not found", HttpStatus.NOT_FOUND);
        }
        else { // Execution flow
            Book book = optionalBook.get();
            Member member = optionalMember.get();

            if ( book.isBorrowed() ) {
                return new ResponseEntity<>("Book \"" + book.getTitle() + "\" with book ID " + bookId + " has already been borrowed.", HttpStatus.BAD_REQUEST);
            }
            else if ( member.getBorrowedbooks().size() >= member.getMaxLeenbaarProducten() ){
                return new ResponseEntity<>("Member " + member.getId() + " had already reached the maximum amount of borrowed books.", HttpStatus.BAD_REQUEST);
            }
            else { // Execution flow
                book.setBorrowed(true);
                book.setOutcheckDate(LocalDate.now());
                bookRepository.save(book);

                member.getBorrowedbooks().add(book);
                memberRepository.save(member);

                return ResponseEntity.ok("Member " + member.getId() + " borrowed book \"" + book.getTitle() + "\" successfully.");
            }
        }


//
//        if (optionalBook.isPresent() && optionalMember.isPresent()) {
//            Book book = optionalBook.get();
//            Member member = optionalMember.get();
//            {
//                if (!book.isBorrowed()) {
//                    if(member.getBorrowedbooks().size() < member.getMaxLeenbaarProducten()){
//
//                    book.setBorrowed(true);
//                    book.setOutcheckDate(LocalDate.now());
//                    bookRepository.save(book);
//                    member.getBorrowedbooks().add(book);
//                    memberRepository.save(member);
//                    }else {
//                        System.out.println("You cant borrow more books");
//                    }
//                } else {
//                    System.out.println("Book is already borrowed");
//
//                }
//            }
//
//        } else{
//            System.out.println("no book with this id");
//        }

    }

    @PutMapping("/{memberid}/return/{bookid}")
    public ResponseEntity<String> returnBookMember(@PathVariable Long memberid, @PathVariable Long bookid) {
        Optional<Book> optionalBook = bookRepository.findById(bookid);
        Optional<Member> optionalMember = memberRepository.findById(memberid);

        if (optionalBook.isPresent() && optionalMember.isPresent()) {
            Book book = optionalBook.get();
            Member member = optionalMember.get();

            if (book.isBorrowed()) {
                book.setBorrowed(false);
                book.setIncheckDate(LocalDate.now());
                bookRepository.save(book);
                member.getBorrowedbooks().remove(book);
                memberRepository.save(member);
                return ResponseEntity.ok("Book returned successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not borrowed");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book or member not found");
        }
    }
}






