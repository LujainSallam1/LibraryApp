package nl.first8.library.controller;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.first8.library.domain.Book;
import nl.first8.library.domain.GoogleBookApiResponse;
import nl.first8.library.domain.Member;
import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Column;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_XML_VALUE})
public class BookController {
    @Autowired
    public BookController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    private RestTemplate restTemplate;
    @Value("${google.books.api.key}")
    private String googleBooksApiKey;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/books")
    public List<Book> getAll(@RequestParam(required = false) String isbn) {
        if (isbn != null) {
            return bookRepository.findByIsbn(isbn);
        } else {
            return bookRepository.findAll();
        }
    }

    @PostMapping("/books/search")
    public ResponseEntity<String> searchBooksByIsbn(@RequestParam String isbn) {
        // تكوين URL للبحث باستخدام رقم ISBN في Google Books API
        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&key=" + googleBooksApiKey;

        // إرسال طلب GET إلى Google Books API باستخدام RestTemplate
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        // إرجاع الاستجابة من Google Books API
        return response;
    }

    //    @PostMapping("/searchbooks_and_add")
//    public ResponseEntity<String> uploadBarcode(@RequestBody Map<String, String> payload ) {
//        String barcodeInfo = payload.get("barcode_info");
//        String userid= payload.get("user_id");
////public ResponseEntity<String> searchBooks(@RequestParam String isbn) {
//        try {
//            URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + barcodeInfo);
//            GoogleBookApiResponse response = objectMapper.readValue(url, GoogleBookApiResponse.class);
//
//            if (response != null && response.getItems() != null && !response.getItems().isEmpty()){
//                GoogleBookApiResponse.GoogleBookItem item = response.getItems().get(0);
//                GoogleBookApiResponse.GoogleBookVolumeInfo volumeInfo = item.getVolumeInfo();
//
//                Book book = new Book();
//                book.setTitle(volumeInfo.getTitle());
//                if (volumeInfo.getAuthors() != null) {
//                    book.setAuthors(volumeInfo.getAuthors().toString());
//                }
//                if (volumeInfo.getPublishDate() != null) {
//                    book.setPublishDate(volumeInfo.getPublishDate());
//                }
//                book.setIsbn(barcodeInfo);
//                bookRepository.save(book);
//                System.out.println("Book saved successfully.");
//                return ResponseEntity.ok("Book saved successfully.");
//
//            } else {
//                System.out.println("dkkkkkkkkkkkkkkkkkkkkkkk.");
//                return ResponseEntity.notFound().build();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    @PostMapping("/searchbooks_and_add")
    public ResponseEntity<String> uploadBarcode(@RequestBody Map<String, String> payload) {
        String barcodeInfo = payload.get("barcode_info");
        String userid = payload.get("user_id");

        try {
            List<Book> foundBooks = bookRepository.findByIsbn(barcodeInfo);

            System.out.println("Book not found in our database.");
            if (foundBooks == null || foundBooks.isEmpty()) {

                return handleNonExistingBook(barcodeInfo);
            } else {
                return handleExistingBook(foundBooks.get(0));
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<String> handleNonExistingBook(String barcodeInfo) throws IOException {
        System.out.println("Book does not exist in our database");

        URL url = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + barcodeInfo);
        GoogleBookApiResponse response = objectMapper.readValue(url, GoogleBookApiResponse.class);

        if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
            GoogleBookApiResponse.GoogleBookItem item = response.getItems().get(0);
            GoogleBookApiResponse.GoogleBookVolumeInfo volumeInfo = item.getVolumeInfo();
//                Book existingBook = bookRepository.findByIsbn(barcodeInfo);

            Book book = new Book();
            book.setTitle(volumeInfo.getTitle());
            if (volumeInfo.getAuthors() != null) {
                book.setAuthors(volumeInfo.getAuthors().toString());
            }
            if (volumeInfo.getPublishDate() != null) {
                book.setPublishDate(volumeInfo.getPublishDate());
            }
            book.setIsbn(barcodeInfo);
            bookRepository.save(book);
            System.out.println("Book saved successfully.");
            return ResponseEntity.ok("Book saved successfully.");

        } else {
            System.out.println("Book not found at google");
            return ResponseEntity.notFound().build();
        }


    }

    private ResponseEntity<String> handleExistingBook(Book existingBook) {
        System.out.println("Book exists");
        if (existingBook.isBorrowed()) {
            System.out.println("Book is borrowed");
            existingBook.setIncheckDate(LocalDate.now());
            existingBook.setOutcheckDate(null);
            existingBook.setBorrowed(false);
            bookRepository.save(existingBook);
            System.out.println("Book returned successfully");
            return ResponseEntity.ok("Book returned successfully");
        } else {
            System.out.println("Book is not borrowed");
            existingBook.setIncheckDate(null);
            existingBook.setOutcheckDate(LocalDate.now());
            existingBook.setBorrowed(true);
            bookRepository.save(existingBook);
            System.out.println("Book borrowed successfully");
            return ResponseEntity.ok("Book borrowed successfully");
        }
    }
//nj,fhkjfhm,jfff


    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return ResponseEntity.ok(book);
        } else
            return ResponseEntity.notFound().build();

    }

    @PostMapping("/books")
    public ResponseEntity<Book> add(@RequestBody Book book) {
        Book savedbook = bookRepository.save(book);
        return ResponseEntity.ok(savedbook);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable(value = "id") Long id, @RequestBody Book body) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book bookDB;

        if (bookOptional.isPresent()) {
            bookDB = bookOptional.get();

            if (Objects.nonNull(body.getIsbn())) bookDB.setIsbn(body.getIsbn());
            if (Objects.nonNull(body.getTitle())) bookDB.setTitle(body.getTitle());
            if (Objects.nonNull(body.getAuthors())) bookDB.setAuthors(body.getAuthors());
            if (Objects.nonNull(body.getPublishDate())) bookDB.setPublishDate(body.getPublishDate());
            if (Objects.nonNull(body.getSummary())) bookDB.setSummary(body.getSummary());

        } else {
            return ResponseEntity.notFound().build();
        }

        Book updatedBook = bookRepository.save(bookDB);
        return ResponseEntity.ok(updatedBook);
    }

    //    @PostMapping("/books/upload-barcode")
//    public ResponseEntity<String> uploadBarcode(@RequestBody Map<String, String> payload ) {
//        String barcodeInfo = payload.get("barcode_info");
//        String userid= payload.get("user_id");
//        return null;
//    }
//        if (barcodeInfo != null && userid != null ) {
//            List<Book> optionalBook = bookRepository.findByIsbn(barcodeInfo);
//
//            if (!optionalBook.isEmpty()) {
//                Book book = optionalBook.get(0);
//                if (book.isBorrowed()) {
//                    if (book.getOutcheckDate() == null) {
//                        book.setOutcheckDate(LocalDate.now());
//                        book.setBorrowed(false);
//                        bookRepository.save(book);
//                        System.out.println("Book returned successfully");
//                        return ResponseEntity.ok("Book returned successfully");
//
//                    } else {
//                         return ResponseEntity.notFound().build();
//                    }
//                } else {
//                    // الكتاب غير مستعار، يجب إجراء إعارة
//                    book.setBorrowed(true);
//                    book.setIncheckDate(LocalDate.now());
//                    bookRepository.save(book);
//                    System.out.println("Book borrowed successfully");
//                    return ResponseEntity.ok("Book borrowed successfully");
//                }
//            } else {
//                return ResponseEntity.notFound().build();
//
//            }
//        } else {
//            return ResponseEntity.badRequest().body("Barcode info or user ID is missing");
//        }
//    }
//    @DeleteMapping("/books/{isbn}")
//    public ResponseEntity<Book> delet(@PathVariable String isbn) {
//        Optional<Book> optionalBook = bookRepository.findByIsbn((isbn));
//        if (optionalBook.isPresent()) {
//            bookRepository.deleteByIsbn(isbn);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @PutMapping("/books/{id}/borrow")
    public boolean borrow(@PathVariable(value = "id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (!book.isBorrowed())
                book.setIncheckDate(LocalDate.now());
            book.setBorrowed(true);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @PutMapping("/books/{id}/handin")
    public boolean handin(@PathVariable(value = "id") Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.isBorrowed())
                book.setBorrowed(false);
            book.setOutcheckDate(LocalDate.now());
            bookRepository.save(book);
        }
        return true;
    }
}







