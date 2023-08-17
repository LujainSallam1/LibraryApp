package nl.first8.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.first8.library.controller.exceptions.BookAlreadyBorrowedException;
import nl.first8.library.controller.exceptions.GoogleBookNotFoundException;
import nl.first8.library.controller.exceptions.MemberNotFoundException;
import nl.first8.library.domain.GoogleBookApiResponse;
import nl.first8.library.domain.entity.Book;
import nl.first8.library.domain.entity.Member;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.MemberRepository;
import org.apache.http.HttpConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ScannerController {

    @Autowired
    public ScannerController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Value("${google.books.api.key}")
    private String googleBooksApiKey;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ObjectMapper objectMapper;

    //TODO: rename endpoint, both here and in Python project
    @PostMapping("/searchbooks_and_add")
    public ResponseEntity<String> uploadBarcode(@RequestBody Map<String, String> payload) {
        String isbn = payload.get("barcode_info");
        //at this point, the "user" can only do actions on behalf of itself, so member=user
        Long memberId = Long.parseLong(payload.get("user_id"));

        try {
            List<Book> foundBooks = bookRepository.findByIsbn(isbn);

            Optional<Member> optionalMember = memberRepository.findById(memberId);

            if (foundBooks == null || foundBooks.isEmpty()) {
                return handleNonExistingBook(isbn);
            } else if (!optionalMember.isPresent()) {
                throw new MemberNotFoundException(memberId);
            } else { // Execution Flow
                Member member = optionalMember.get();

                for(Book memberBook : member.getBorrowedbooks()){
                    if(memberBook.getIsbn().equals(isbn)){
                        // Member already borrows book with same ISBN; try to return book for member by PUTting to relevant endpoint
                        Long bookId = memberBook.getId();
                        URL url = new URL("http://localhost:8080/api/v1/" + memberId + "/return/" + bookId);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("PUT");

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer content = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();

                        System.out.println("Response of PUT to return: " + content.toString());

                        con.disconnect();

                        return ResponseEntity.ok("Response of PUT to return: " + content.toString());
                    }
                }
                // Member doesn't yet borrow a book with same ISBN; try to borrow book for member by PUTting to relevant endpoint
                // First find non-borrowed book with ISBN
                Book availableBook = null;
                for (Book bookWithISBN: bookRepository.findByIsbn(isbn)){
                    if (!bookWithISBN.isBorrowed()){
                        availableBook = bookWithISBN;
                        break;
                    }
                }
                if (Objects.isNull(availableBook)){
                    return new ResponseEntity<>("All books with ISBN " + isbn + " have already been borrowed.", HttpStatus.BAD_REQUEST);
                }
                Long bookId = availableBook.getId();

                URL url = new URL("http://localhost:8080/api/v1/members/" + memberId + "/borrow/" + bookId);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("PUT");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                System.out.println("Response of PUT to borrow: " + content.toString());

                con.disconnect();

                return ResponseEntity.ok("Response of PUT to borrow: " + content.toString());
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<String> handleNonExistingBook(String isbn) throws IOException {
        System.out.println("Book does not exist in our database");

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
        GoogleBookApiResponse response = objectMapper.readValue(url, GoogleBookApiResponse.class);

        if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
            GoogleBookApiResponse.GoogleBookItem item = response.getItems().get(0);
            GoogleBookApiResponse.GoogleBookVolumeInfo volumeInfo = item.getVolumeInfo();
            Book book = new Book();
            book.setTitle(volumeInfo.getTitle());
            if (volumeInfo.getAuthors() != null) {
                book.setAuthors(volumeInfo.getAuthors().toString());
            }
            if (volumeInfo.getPublishDate() != null) {
                book.setPublishDate(volumeInfo.getPublishDate());
            }
            book.setIsbn(isbn);
            bookRepository.save(book);
            System.out.println("Book saved successfully.");
            return ResponseEntity.ok("Book saved successfully.");

        } else {
            System.out.println("Book not found at google");
            throw new GoogleBookNotFoundException(isbn);
        }


    }

    private ResponseEntity<String> handleExistingBook(Book existingBook) {
        if (existingBook.isBorrowed()) {
            System.out.println("Book is borrowed");
            existingBook.setReturnDate(LocalDate.now());
            existingBook.setBorrowDate(null);
            existingBook.setBorrowed(false);
            bookRepository.save(existingBook);
            System.out.println("Book returned successfully");
            return ResponseEntity.ok("Book returned successfully");
        } else {
            System.out.println("Book is not borrowed");
            existingBook.setReturnDate(null);
            existingBook.setBorrowDate(LocalDate.now());
            existingBook.setBorrowed(true);
            bookRepository.save(existingBook);
            System.out.println("Book borrowed successfully");
            return ResponseEntity.ok("Book borrowed successfully");
        }
    }

}
