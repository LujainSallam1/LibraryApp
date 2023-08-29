package nl.first8.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@EnableSwagger2
@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args ) {
        SpringApplication.run(LibraryApplication.class, args);
    }


}