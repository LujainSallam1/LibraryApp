package nl.first8.library.service;

import nl.first8.library.controller.exceptions.*;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.entity.Member;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowReturnService {
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;



    public ResponseEntity<Book> borrow(@PathVariable(value = "id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (!book.isBorrowed()) {
                book.setBorrowed(true);
                book.setBorrowDate(LocalDate.now());
                book.setReturnDate(null);
                bookRepository.save(book);
                System.out.println("The borrowing has been completed successfully");
                return ResponseEntity.ok(book);
            } else {
                System.out.println("Book is already borrowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            System.out.println("Book not found");
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<Book> handin(@PathVariable(value = "id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.isBorrowed()) {
                book.setBorrowed(false);
                book.setBorrowDate(LocalDate.now());
                bookRepository.save(book);
                System.out.println("Book returned successfully");
                return ResponseEntity.ok(book);
            } else {
                System.out.println("Book is not currently borrowed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            System.out.println("Book not found");
            return ResponseEntity.notFound().build();
        }
    }

    public Book returnBookMember(Long memberId, Long bookId) {
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

                return book;
            }
        }
    }
    public Book borrowBookMember(Long memberId, Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (!optionalBook.isPresent()) {
            throw new BookNotFoundException(bookId);
        } else if (!optionalMember.isPresent()) {
            throw new MemberNotFoundException(memberId);
        } else { // Execution flow
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

                return book;
            }
        }
    }

//
//    @PutMapping("/members/{member_id}/borrow/{book_id}")
//    public ResponseEntity<String> borrowBookMember(@PathVariable(value = "member_id") Long memberId, @PathVariable(value = "book_id") Long bookId) {
//        Optional<Book> optionalBook = bookRepository.findById(bookId);
//        Optional<Member> optionalMember = memberRepository.findById(memberId);
//
//        if (!optionalBook.isPresent()) throw new BookNotFoundException(bookId);
//        else if (!optionalMember.isPresent()) throw new MemberNotFoundException(memberId);
//        else { // Execution flow
//            Book book = optionalBook.get();
//            Member member = optionalMember.get();
//
//            if (book.isBorrowed()) {
//                throw new BookAlreadyBorrowedException(book);
//            } else if (member.getBorrowedbooks().size() >= member.getMaxBorrowableProducts()) {
//                throw new MemberMaxBorrowedException(memberId);
//            } else { // Execution flow
//                book.setBorrowed(true);
//                book.setBorrowDate(LocalDate.now());
//                book.setReturnDate(null);
//                bookRepository.save(book);
//
//                member.getBorrowedbooks().add(book);
//                memberRepository.save(member);
//
//                return ResponseEntity.ok("Member " + member.getId() + " borrowed book \"" + book.getTitle() + "\" with ID " + bookId + " successfully.");
//            }
//        }
//    }
//    public ResponseEntity<String> returnBookMember(@PathVariable(value = "member_id") Long memberId, @PathVariable(value = "book_id") Long bookId) {
//        Optional<Book> optionalBook = bookRepository.findById(bookId);
//        Optional<Member> optionalMember = memberRepository.findById(memberId);
//
//        if (!optionalBook.isPresent()) {
//            throw new BookNotFoundException(bookId);
//        } else if (!optionalMember.isPresent()) {
//            throw new MemberNotFoundException(memberId);
//        } else { // Execution flow
//            Book book = optionalBook.get();
//            Member member = optionalMember.get();
//
//            if (!book.isBorrowed()) {
//                throw new BookNotBorrowedException(book);
//            } else { // Execution flow
//                book.setBorrowed(false);
//                book.setReturnDate(LocalDate.now());
//                book.setBorrowDate(null);
//                bookRepository.save(book);
//
//                member.getBorrowedbooks().remove(book);
//                memberRepository.save(member);
//
//                return ResponseEntity.ok("Member " + member.getId() + " returned book \"" + book.getTitle() + "\" with ID " + bookId + " successfully.");
//            }
//        }
//    }


}
