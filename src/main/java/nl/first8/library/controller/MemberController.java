package nl.first8.library.controller;

import nl.first8.library.controller.exceptions.*;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.entity.Member;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

        if (!optionalBook.isPresent()) throw new BookNotFoundException(bookId);
        else if (!optionalMember.isPresent()) throw new MemberNotFoundException(memberId);
        else { // Execution flow
            Book book = optionalBook.get();
            Member member = optionalMember.get();

            if (book.isBorrowed()) {
                throw new BookAlreadyBorrowedException(book);
            } else if (member.getBorrowedbooks().size() >= member.getMaxBorrowableProducts()) {
                throw new MemberMaxBorrowedException(memberId);
            } else { // Execution flow
                book.setBorrowed(true);
                book.setBorrowDate(LocalDate.now());
                book.setReturnDate(null);
                bookRepository.save(book);

                member.getBorrowedbooks().add(book);
                memberRepository.save(member);

                return ResponseEntity.ok("Member " + member.getId() + " borrowed book \"" + book.getTitle() + "\" with ID " + bookId + " successfully.");
            }
        }
    }

    @PutMapping("/{member_id}/return/{book_id}")
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






