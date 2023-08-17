package nl.first8.library.controller;

import nl.first8.library.controller.exceptions.*;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.entity.Member;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.MemberRepository;
import nl.first8.library.service.BorrowReturnService;
import nl.first8.library.service.MemberAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MemberController {
    @Autowired
    private MemberAdminService memberAdminService;
    @Autowired
    private BorrowReturnService borrowReturnService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/members")
    public List<Member> getAll() {
        return memberAdminService.getAll();
    }


    @PostMapping("/members")
    public ResponseEntity<Member> add(@RequestBody Member member) {
            Member savedmember= memberAdminService.addmember(member);
            return ResponseEntity.ok(savedmember);
        }

    @PutMapping("/members/{id}/disable")
    public ResponseEntity<Optional<Member>> disable(@PathVariable(value = "id") Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        boolean success = memberAdminService.disable(id);
        if (success) {
            return ResponseEntity.ok(optionalMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/members/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable(value = "id") Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        boolean success = memberAdminService.enable(id);
        if (success) {
            return ResponseEntity.ok("Member enabled successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/members/{member_id}/borrow/{book_id}")
    public ResponseEntity<String> borrowBookMember(@PathVariable(value = "member_id") Long memberId, @PathVariable(value = "book_id") Long bookId) {
        return borrowReturnService.borrowBookMember(memberId,bookId);
    }

    @PutMapping("/members/{member_id}/return/{book_id}")
    public ResponseEntity<String> returnBookMember(@PathVariable(value = "member_id") Long memberId, @PathVariable(value = "book_id") Long bookId) {
        return borrowReturnService.returnBookMember(memberId, bookId);
    }}
