package nl.first8.library.service;

import nl.first8.library.controller.exceptions.BookNotFoundException;
import nl.first8.library.controller.exceptions.MemberNotFoundException;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.entity.Member;
import nl.first8.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScannerService {
    private BorrowReturnService borrowReturnService;
    private GoogleBooksService googleBooksService;
    private BookAdminService bookAdminService;
    private MemberAdminService memberAdminService;

    public Book handlePayload(Map<String, String> payload) {
        String isbn = payload.get("barcode_info");
        // At this point, the user can only do actions on behalf of itself, so member=user
        Long memberId = Long.parseLong(payload.get("user_id"));

        try {
            // Get book(s) and member
            List<Book> foundBooks = bookAdminService.getAllBooks(isbn);
            Optional<Member> optionalMember = memberAdminService.getMember(memberId);

            // Handle failures to retrieve book/member
            if (foundBooks == null || foundBooks.isEmpty()) {
                return googleBooksService.handleNonExistingBook(isbn);
            } else if (!optionalMember.isPresent()) {
                throw new MemberNotFoundException(memberId);
            } else {
                // Execution Flow
                Member member = optionalMember.get();

                for(Book memberBook : member.getBorrowedbooks()){
                    if(memberBook.getIsbn().equals(isbn)){
                        // Member already borrows book with same ISBN; try to return book for member
                        Long bookId = memberBook.getId();
                        Book book = borrowReturnService.returnBookMember(memberId, bookId);
                        return book;
                    }
                }

                // Member doesn't yet borrow a book with same ISBN; try to borrow book for member
                // First find non-borrowed book with ISBN
                Book availableBook = null;
                for (Book bookWithISBN: bookAdminService.getAllBooks(isbn)){
                    if (!bookWithISBN.isBorrowed()){
                        availableBook = bookWithISBN;
                        break;
                    }
                }

                Long bookId = availableBook.getId();
                Book book = borrowReturnService.returnBookMember(memberId, bookId);
                return book; //null if no books were found

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
