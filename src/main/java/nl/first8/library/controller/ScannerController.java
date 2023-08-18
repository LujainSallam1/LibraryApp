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
import nl.first8.library.service.GoogleBooksService;
import nl.first8.library.service.MemberAdminService;
import nl.first8.library.service.ScannerService;
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
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_XML_VALUE)
public class ScannerController {

    private ScannerService scannerService;

    @PostMapping("/scanner/handlePayload")
    public ResponseEntity<Book> handlePayload(@RequestBody Map<String, String> payload) {
        Book book = scannerService.handlePayload(payload);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(book);
        }
    }
}
