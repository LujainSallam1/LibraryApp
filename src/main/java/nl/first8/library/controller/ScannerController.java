package nl.first8.library.controller;

import nl.first8.library.domain.entity.Book;
import nl.first8.library.service.ScannerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
