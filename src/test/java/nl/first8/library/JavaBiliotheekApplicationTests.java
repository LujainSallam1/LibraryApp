package nl.first8.library;

import nl.first8.library.domain.Lid;
import nl.first8.library.repository.BluRayRepository;
import nl.first8.library.repository.BookRepository;
import nl.first8.library.repository.LidRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class JavaBiliotheekApplicationTests {

    @Autowired
    private LidRepository lidRepository;
    private BluRayRepository blurayRepository;
    private BookRepository bookRepository;

//    @Test
//    void contextLoads() {
//    }

    @Test
    void naamLidId1(){
        Lid lid = lidRepository.findById(1L).get();
        Assertions.assertThat(lid.getNaam().equals("Chezley"));
    }

    void maxProductsIs5(){
        Lid lid = lidRepository.getOne(1L);
        Assertions.assertThat(lid.getMaxProducts() == 5);
    }




}
