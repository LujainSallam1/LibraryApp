package nl.first8.library.service;

import nl.first8.library.controller.exceptions.*;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.entity.Member;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service


public class MemberAdminService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Member> getAll() {
    return memberRepository.findAll();
}
    public Member addmember(Member member) {
        return memberRepository.save(member);
    }

    public ResponseEntity<Member> update(@PathVariable(value = "id") Long id, @RequestBody Member member) {
        Optional<Member> memberOptional = memberRepository.findById(id);
        Member memberdb;

        if (memberOptional.isPresent()) {
            memberdb = memberOptional.get();

            if (Objects.nonNull(member.getName())) memberdb.setName(member.getName());
            if (Objects.nonNull(member.getAddress())) memberdb.setAddress(member.getAddress());
            if (Objects.nonNull(member.getCity())) memberdb.setCity(member.getCity());
            if (Objects.nonNull(member.getId())) memberdb.setId(member.getId());
            if (Objects.nonNull(member.getBorrowedbooks())) memberdb.setBorrowedbooks(member.getBorrowedbooks());

        } else {
            return ResponseEntity.notFound().build();
        }

        Member updatedMember = memberRepository.save(memberdb);
        return ResponseEntity.ok(updatedMember);
    }

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



    public ResponseEntity<String> returnBookMember(@PathVariable(value = "member_id") Long memberId, @PathVariable(value = "book_id") Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException(bookId);
        } else if (!optionalMember.isPresent()) {
            throw new MemberNotFoundException(memberId);
        } else { // Execution flow
            Book book = optionalBook.get();
            Member member = optionalMember.get();

            if (!book.isBorrowed()) {
                throw new BookNotBorrowedException(book);
            } else { // Execution flow
                book.setBorrowed(false);
                book.setReturnDate(LocalDate.now());
                book.setBorrowDate(null);
                bookRepository.save(book);

                member.getBorrowedbooks().remove(book);
                memberRepository.save(member);

                return ResponseEntity.ok("Member " + member.getId() + " returned book \"" + book.getTitle() + "\" with ID " + bookId + " successfully.");
            }
        }
    }
}



