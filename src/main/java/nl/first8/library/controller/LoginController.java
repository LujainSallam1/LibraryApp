package nl.first8.library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginController {
    @PostMapping("/logout")
    public String logout() {
        // Redirect the user to the Keycloak logout URL
        return "redirect:http://localhost:8080/realms/libraryapp/protocol/openid-connect/logout";
    }
}


