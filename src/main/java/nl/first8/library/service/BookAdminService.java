package nl.first8.library.service;

import nl.first8.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BookAdminService {
    @Autowired
    private BookRepository bookRepository;

}
